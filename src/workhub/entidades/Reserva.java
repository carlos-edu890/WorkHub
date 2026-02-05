package workhub.entidades;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.Serializable;
import java.time.Duration;

public class Reserva implements Serializable{
    private int id;
    private Cliente cliente;
    private Espaco espaco;
    private LocalDate dataReserva;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private List<ServicoAdicional> servicosAdicionais;
    private double valorTotal;
    private static int numero = 0;

    public Reserva() {
        this.numero++;
        this.id = numero;
        this.servicosAdicionais = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public Espaco getEspaco() {
        return this.espaco;
    }

    public LocalDate getData() {
        return this.dataReserva;
    }

    public LocalTime getHoraInicio() {
        return this.horaInicio;
    }

    public LocalTime getHoraFim() {
        return this.horaFim;
    }

    public void proxReser(int proxRe) {
        numero = proxRe;
    }

    public boolean isDisponivel(Espaco espaco, LocalDate dataReserva) {
        if(getCliente() == null && this.dataReserva != dataReserva && this.espaco == null) {
            return true;
        } else if(getCliente() == null && this.dataReserva != dataReserva && this.espaco.equals(espaco)) {
            return true;
        }
        return false;
    }

    public void fazerReserva(Cliente cliente, Espaco espaco, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim) {
        if(isDisponivel(espaco, dataReserva)) {
            this.cliente = cliente;
            this.espaco = espaco;
            this.dataReserva = dataReserva;
            this.horaInicio = horaInicio;
            this.horaFim = horaFim;
            this.espaco.setDisponivel();
            this.espaco.qtdReservas(+1);
            this.espaco.setQuantasReservasTeve();
        } else {
            System.out.println("Não foi possivel fazer a reserva");
        }
    }

    private void set(ServicoAdicional servicoAdicional) {
        servicosAdicionais.add(servicoAdicional);
    }

    public void adicionarServicoAdicionalLO(String descricao, double valorTotal) {
        ServicoAdicional s = new Locker(descricao, valorTotal);
        set(s);
    }

    public void adicionarServicoAdicionalCP(String descricao, double valorTotal) {
        ServicoAdicional s = new CafePremium(descricao, valorTotal);
        set(s);
    }

    public void adicionarServicoAdicionalES(String descricao, double valorTotal) {
        ServicoAdicional s = new Estacionamento(descricao, valorTotal);
        set(s);
    }

    public void adicionarServicoAdicionalRC(String descricao, double valorTotal) {
        ServicoAdicional s = new RecebimentoCorrespondencia(descricao, valorTotal);
        set(s);
    }

    public ServicoAdicional getServicoAdicionalLO() {
        ServicoAdicional s = new Locker(null, 0);
        return s;
    }

    public ServicoAdicional getServicoAdicionalCP() {
        ServicoAdicional s = new CafePremium(null, 0);
        return s;
    }

    public ServicoAdicional getServicoAdicionalES() {
        ServicoAdicional s = new Estacionamento(null, 0);
        return s;
    }

    public ServicoAdicional getServicoAdicionalRC() {
        ServicoAdicional s = new RecebimentoCorrespondencia(null, 0);
        return s;
    }

    public double todosOsServicos(ServicoAdicional sa) {
        double valor = 0;
        for (ServicoAdicional servicoAdicional : servicosAdicionais) {
            if(servicoAdicional.getClass() == sa.getClass())
                valor += servicoAdicional.getValorTotal();
        }
        return valor;
    }

    public int qtdServico(ServicoAdicional sa) {
        int qtd = 0;
        for (ServicoAdicional servicoAdicional : servicosAdicionais) {
            if (servicoAdicional.getClass() == sa.getClass()) {
                qtd++;
            }
        }
        return qtd;
    }

    public double getValor() {
        valorTotal = espaco.getValorHora();
        Duration duracao = Duration.between(horaInicio, horaFim);
        if(duracao.toHours() > 1) valorTotal *= duracao.toHours(); 
        if(totalDeServicos() > 0) {
            for (ServicoAdicional servicoAdicional : servicosAdicionais) {
                valorTotal += servicoAdicional.getValorTotal(); 
            }
        }
        return this.valorTotal;
    }

    public double getValorPorEspaco() {
        valorTotal = espaco.getValorHora();
        Duration duracao = Duration.between(horaInicio, horaFim);
        if(duracao.toHours() > 1) valorTotal *= duracao.toHours();
        return valorTotal;
    }

    public int totalDeServicos() {
        return this.servicosAdicionais.size();
    }

    public StringBuilder todosServicos() {
        StringBuilder b = new StringBuilder();
        for (ServicoAdicional servicoAdicional : servicosAdicionais) {
            b.append("\n")
                .append(servicoAdicional.getDescricao())
                .append(" ")
                .append("Valor: " + servicoAdicional.getValorTotal());
        }
        return b;
    }

    public boolean podeCancelar() {
        LocalDate d = LocalDate.now();
        LocalTime t = LocalTime.now();
        return (this.dataReserva.isAfter(d) | this.horaInicio.isAfter(t));
    }

    public void cancelarReserva() {
        if(this.podeCancelar()) {
            this.can();
        }
    }

    public boolean acabouReserva() {
        LocalDate d = LocalDate.now();
        LocalTime t = LocalTime.now();
        return !(this.dataReserva.isAfter(d) && this.horaFim.isAfter(t));
    }

    public void encerradoReserva() {
        if(this.acabouReserva()) {
            this.can();
        }
    }

    private void can() {
        this.espaco.setDisponivel();
        this.espaco.qtdReservas(-1);
        this.cliente = null;
        this.espaco = null;
        this.dataReserva = null;
        this.horaInicio = null;
        this.horaFim = null;
        this.servicosAdicionais.clear();
    }

    public int espacosReservas(Espaco espaco) {
        LocalDate d = LocalDate.now();
        LocalTime t = LocalTime.now();
        if(this.espaco.equals(espaco) && this.dataReserva.isEqual(d) && (this.horaInicio.isBefore(t) && this.horaFim.isAfter(t))) {
            Duration duration = Duration.between(horaInicio, t);
            if((int) duration.toHours() < 0) {
                return 0;
            }
            return (int) duration.toHours();
        }
        return 0; 
    }

    public String toString() {
        return "========== Reserva ID: " + this.id + " ==========\nCliente " + this.cliente.getSituacao() + "\nEspaço: " + this.espaco.getDescricaoCompleta() + 
        "\nData da Reserva: " + this.dataReserva + " - Hora de Inicio: " + this.horaInicio + " - Hora de Termino: " + this.horaFim + " - Horas Usadas: " + this.espacosReservas(espaco) +
        "\nServiços Adicionais: " + this.totalDeServicos() + this.todosServicos() + "\n========== VALOR TOTAL: " + this.getValor() + " ==========\n";
    }

    public String descricaoCompleta() {
        return "========== Reserva ID: " + this.id + " ==========\nCliente " + this.cliente.getSituacao() + "\nEspaço: " + this.espaco.getDescricaoCompleta() + 
        "\nData da Reserva: " + this.dataReserva + " - Hora de Inicio: " + this.horaInicio + " - Hora de Termino: " + this.horaFim + " - Horas Usadas: " + this.espacosReservas(espaco) +
        "\nServiços Adicionais: " + this.totalDeServicos() + this.todosServicos() + "\n========== VALOR TOTAL: " + this.getValor() + " ==========\n";
    }

    public static void main(String[] args) {
        Reserva r = new Reserva();
        Reserva r1 = new Reserva();
        Cliente c = new Cliente("null", "null", "null", "null");
        Espaco e = new EstacaoTrabalho("null", 70, "null");
        Espaco e1 = new Auditorio("null", 120, "null");
        ServicoAdicional s = new Locker("O Sucesso do Brasil", 10);
        ServicoAdicional s1 = new CafePremium("Para uma manhã agitada", 11);

        int ano = 2025;
        int mes = 7;       // 1-12 (janeiro = 1)
        int dia = 18;
        LocalDate data = LocalDate.of(ano, mes, dia);

        r.fazerReserva(c, e, data, LocalTime.parse("14:30:00"), LocalTime.parse("16:30:00"));
        r.set(s);
        r.set(s1);
        r1.fazerReserva(c, e1, data, LocalTime.parse("14:30:00"), LocalTime.parse("16:30:00"));
        System.out.println(r.descricaoCompleta());
        System.out.println(r1.descricaoCompleta());
    }
}
