package workhub.controle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workhub.entidades.Reserva;
import workhub.excecoes.FalhaPersistenciaException;
import workhub.excecoes.ReservaNaoEncontradaException;
import workhub.entidades.Cliente;
import workhub.entidades.Espaco;

public class RepositorioReservas {
    private Map<Integer, Reserva> reservas;

    public RepositorioReservas() {
        this.reservas = new HashMap<>();
    }

    public void adicionarReserva() {
        Reserva r = new Reserva();
        this.reservas.put(r.getId(), r);
    }

    public Reserva buscarReserva(int id) throws ReservaNaoEncontradaException{
        return this.reservas.get(id);
    }

    public Reserva buscarReservaCliente(Cliente cliente, Espaco espaco) {
        Collection<Reserva> valores = reservas.values();
        for (Reserva reserva : valores) {
            if(reserva.getCliente().equals(cliente) && reserva.getEspaco().equals(espaco)) {
                return reserva;
            }
        }
        return null;
    }

    public void removerReserva(int id) throws ReservaNaoEncontradaException{
        reservas.remove(id);
    }

    public boolean fazerReserva(Cliente cliente, Espaco espaco, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim){
        Collection<Reserva> valores = reservas.values();
        for (Reserva reserva : valores) {
            if(reserva.isDisponivel(espaco, dataReserva)) {
                if(!this.existeSobreposicao(espaco, dataReserva, horaInicio, horaFim)) {
                    reserva.fazerReserva(cliente, espaco, dataReserva, horaInicio, horaFim);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean cancelarReserva(int id, Cliente cliente) {
        Collection<Reserva> valores = reservas.values();
        for (Reserva reserva : valores) {
            if(reserva.getId() == id && reserva.getCliente().equals(cliente)) {
                reserva.cancelarReserva();
                return true;
            }
        }
        return false;
    }

    public StringBuilder listarReservasDoCliente(Cliente cliente) {
        StringBuilder b = new StringBuilder();
        Collection<Reserva> valores = reservas.values();
        for (Reserva reserva : valores) {
            if(reserva.getCliente() == null)
                continue;
            if(reserva.getCliente().equals(cliente)) {
                LocalDate d = LocalDate.now();
                if(d.isBefore(reserva.getData())){
                    b.append("\n")
                    .append(reserva.descricaoCompleta());
                }
            }
        }
        return b;
    }

    public String horaisTotais(Espaco espaco) {
        Collection<Reserva> valores = reservas.values();
        double horasTotais = 0;
        for (Reserva reserva : valores) {
            if(reserva.getEspaco() == null)
                horasTotais += 0;
            else if(reserva.getEspaco().equals(espaco))
                horasTotais += reserva.espacosReservas(espaco);
        }
        return " - Horas Totais Usadas: " + horasTotais;
    }

    public double receitaPorCliente(Cliente cliente) {
        Collection<Reserva> valores = reservas.values();
        double valor = 0;
        for (Reserva reserva : valores) {
            if(reserva.getCliente() == null) 
                continue;
            else if(reserva.getCliente().equals(cliente))
                valor += reserva.getValor();
        }
        return valor;
    }

    public double receitaPorEspaco(Espaco espaco) {
        Collection<Reserva> valores = reservas.values();
        double valor = 0;
        for (Reserva reserva : valores) {
            if(reserva.getEspaco() == null) continue;
            if (reserva.getEspaco().equals(espaco)) {
                valor += reserva.getValorPorEspaco();
            }
        }
        return valor;
    }

    public double reservasFeitasNoDia(LocalDate d, LocalTime t) {
        Collection<Reserva> valores = reservas.values();
        double valor = 0;
        for (Reserva reserva : valores) {
            if(reserva.getData() == null) continue;
            if(reserva.getData().isEqual(d) && reserva.getHoraFim().isBefore(t)) {
                valor += reserva.getValor();
            }
        }
        return valor;
    }

    public StringBuilder servicosAdicionais() {
        StringBuilder b = new StringBuilder();
        Reserva r = new Reserva();
        int qtdLO = 0, qtdRC = 0, qtdES = 0, qtdCP = 0;
        double valorLO = 0, valorRC = 0, valorES = 0, valorCP = 0;
        Collection<Reserva> valores = reservas.values();
        for (Reserva reserva : valores) {
            valorCP += reserva.todosOsServicos(r.getServicoAdicionalCP());
            valorES += reserva.todosOsServicos(r.getServicoAdicionalES());
            valorLO += reserva.todosOsServicos(r.getServicoAdicionalLO());
            valorRC += reserva.todosOsServicos(r.getServicoAdicionalRC());

            qtdCP += reserva.qtdServico(r.getServicoAdicionalCP());
            qtdES += reserva.qtdServico(r.getServicoAdicionalES());
            qtdLO += reserva.qtdServico(r.getServicoAdicionalLO());
            qtdRC += reserva.qtdServico(r.getServicoAdicionalRC());
        }
        b.append("Serviço Locker:\nQuantidade: " + qtdLO + " - Valor Total: " + valorLO)
        .append("\nServiço Café Premium:\nQuantidade: " + qtdCP + " - Valor Total: " + valorCP)
        .append("\nServiço Estacionamento:\nQuantidade: " + + qtdES + " - Valor Total: " + valorES)
        .append("\nServiço Recebimento de Correspondencia:\nQuantidade: " + qtdRC + " - Valor Total: " + valorRC);
        return b;
    }

    public int tamanhoRepositorioReservas() {
        return this.reservas.size();
    }

    public void salvarArquivo(String caminho) throws FalhaPersistenciaException {
        try (FileOutputStream fileOut = new FileOutputStream(caminho);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {
            oos.writeObject(reservas);
        } catch (IOException e) {
            throw new FalhaPersistenciaException("Falha ao salvar pedidos no arquivo");
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarArquivo(String caminho) throws FalhaPersistenciaException {
        try (FileInputStream fileIn = new FileInputStream(caminho);
            ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            reservas = (Map<Integer, Reserva>) ois.readObject();
            proximaReserva();
        } catch (IOException | ClassNotFoundException e) {
            throw new FalhaPersistenciaException("Falha ao carregar pedidos do arquivo");
        }
    }

    public void proximaReserva() {
        Reserva r = new Reserva();
        r.proxReser(this.tamanhoRepositorioReservas());
    }

    public boolean existeSobreposicao(Espaco espaco, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim) {
        Collection<Reserva> valores = reservas.values();
        for (Reserva reservaExistente : valores) {
            // 1. Verifica se é o mesmo espaço e a mesma data
            if (reservaExistente.getEspaco() == null && reservaExistente.getData() == null) {
                continue;
            }

            boolean mesmoEspaco = reservaExistente.getEspaco().getClass().equals(espaco.getClass());
            boolean mesmaData = reservaExistente.getData().equals(dataReserva);

            if (mesmoEspaco && mesmaData) {
                // 2. Verifica a sobreposição de horários
                LocalTime inicioExistente = reservaExistente.getHoraInicio();
                LocalTime fimExistente = reservaExistente.getHoraFim();

                // Lógica de sobreposição:
                // Se a nova reserva começa ANTES do fim da existente E termina DEPOIS do início da existente.
                if (horaInicio.isBefore(fimExistente) && horaFim.isAfter(inicioExistente)) {
                    return true; // Sobreposição encontrada
                }
            }
        }
        return false; // Nenhuma sobreposição
    }
}
