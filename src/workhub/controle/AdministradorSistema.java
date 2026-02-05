package workhub.controle;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.IllegalFormatException;

import workhub.entidades.Cliente;
import workhub.entidades.Espaco;
import workhub.entidades.Reserva;
import workhub.excecoes.ClienteJaCadastradoException;
import workhub.excecoes.ClienteNaoEncontradoException;
import workhub.excecoes.EspacoIndisponivelException;
import workhub.excecoes.FalhaPersistenciaException;
import workhub.excecoes.ReservaNaoEncontradaException;

public class AdministradorSistema {
    private RepositorioClientes repositorioClientes;
    private RepositorioEspacos repositorioEspacos;
    private RepositorioReservas repositorioReservas;

    public AdministradorSistema() {
        this.repositorioClientes = new RepositorioClientes();
        this.repositorioEspacos = new RepositorioEspacos();
        this.repositorioReservas = new RepositorioReservas();
    }

    // Métodos auxiliares de formatação
    public static String formatarCpf(String cpf) {
        // Remove todos os caracteres não numéricos
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");

        return cpfNumerico.substring(0, 3) + "." +
                cpfNumerico.substring(3, 6) + "." +
                cpfNumerico.substring(6, 9) + "-" +
                cpfNumerico.substring(9, 11);
    }

    public static String formatarTelefone(String telefone) {
        // Remove todos os caracteres não numéricos
        String telNumerico = telefone.replaceAll("[^0-9]", "");

        // Verifica se tem 10 (DDD + 8 dígitos) ou 11 (DDD + 9 dígitos)
        if (telNumerico.length() == 10) { // Ex: (XX) XXXX-XXXX
            return "(" + telNumerico.substring(0, 2) + ") " +
                   telNumerico.substring(2, 6) + "-" +
                   telNumerico.substring(6, 10);
        } else { // Ex: (XX) XXXXX-XXXX (com 9º dígito)
            return "(" + telNumerico.substring(0, 2) + ") " +
                   telNumerico.substring(2, 7) + "-" +
                   telNumerico.substring(7, 11);
        }
    }


    // Métodos de validação
    public boolean isCpf(String cpf) {
        return cpf.length() != 11;
    }

    public boolean isTelefone(String telefone) {
        if(telefone.length() == 10)
            return false;
        if(telefone.length() == 11)
            return false;
        return true;
    }

    public boolean verificaValor(double valor) {
        if(valor > 0)
            return true;
        System.out.println("Valor invalido.");
        return false;
    }

    public boolean verificaTamanho(String tamanho) {
        String t = tamanho.toLowerCase();
        if (t.equals("pequeno") || t.equals("medio") || t.equals("grande")) {
            return true;
        }
        System.out.println("Tamanho invalido.");
        return false;
    }


    // Convertendo Hora e Data

    public LocalDate data(int dia, int mes) {
        try {
            LocalDate data = LocalDate.of(Year.now().getValue(), mes, dia);
            return data;
        } catch (DateTimeException e) {
            System.out.println("A Data está invalida.");
            return null;
        }
    }

    public LocalTime horaInicio(int hora, int minuto) {
        
        try {
            LocalTime horaIni = LocalTime.of(hora, minuto);
            return horaIni;
        } catch (DateTimeException e) {
            System.out.println("Horario invalido.");
            return null;
        }
    }

    public LocalTime horaFim(int hora, int minuto) {
        try {
            LocalTime horaFim = LocalTime.of(hora, minuto);    
            return horaFim;
        } catch (DateTimeException e) {
            System.out.println("Horario invalido.");
            return null;
        }
    }

    public boolean dataIsAnterior(LocalDate d) {
        LocalDate i = LocalDate.now();
        if(i.isAfter(d))
            return true;
        return false;
    }


    // Usa Repositório de Clientes

    public boolean adicionarCliente(String cpf, String nome, String email, String telefone) {
        //Cliente cliente = new Cliente(cpf, nome, email, telefone);
        if(isCpf(cpf) || isTelefone(telefone)) {
            System.out.println("CPF ou Numero de Telefone incorreto(s).\n");
            return false;
        }
        try {
            repositorioClientes.adicionarCliente(new Cliente(formatarCpf(cpf), nome, email, formatarTelefone(telefone)));
            System.out.println("Adicionado com sucesso!");
            return true;
        } catch (ClienteJaCadastradoException e) {
            System.out.println("O Cliente já foi cadastrado.");
            return false;
        }
    }

    public Cliente buscarCliente(String cpf) {
        if(isCpf(cpf) && cpf.length() != 14){
            System.out.println("CPF incorreto.\n");
            return null;
        }
        try {
            return repositorioClientes.buscarCliente(formatarCpf(cpf));
        } catch (ClienteNaoEncontradoException e) {
            System.out.println("O Cliente não foi encontrado.");
            return null;
        }
    }

    public boolean removerCliente(String cpf) {
        if(isCpf(cpf)){
            System.out.println("CPF incorreto.\n");
            return false;
        }
        try {
            repositorioClientes.removerCliente(formatarCpf(cpf));
            System.out.println("O Cliente foi removido!");
            return true;
        } catch (ClienteNaoEncontradoException e) {
            System.out.println("O Cliente não foi encontrado.");
            return false;
        }
    }

    public int tamanhoRepositorioClientes() {
        return repositorioClientes.tamanhoRepositorioClientes();
    }



    // Usa Repositório de Espaços

    public void adicionarEspacosAU(String nome, double valorHora, String tamanho) {
        if(verificaValor(valorHora) == false) return;
        if(verificaTamanho(tamanho) == false) return;
        repositorioEspacos.adicionarEspacosAU(nome, valorHora, tamanho);
    }

    public void adicionarEspacosSP(String nome, double valorHora, String tamanho) {
        if(verificaValor(valorHora) == false) return;
        if(verificaTamanho(tamanho) == false) return;
        repositorioEspacos.adicionarEspacosSP(nome, valorHora, tamanho);
    }

    public void adicionarEspacosSR(String nome, double valorHora, String tamanho) {
        if(verificaValor(valorHora) == false) return;
        if(verificaTamanho(tamanho) == false) return;
        repositorioEspacos.adicionarEspacosSR(nome, valorHora, tamanho);
    }

    public void adicionarEspacosET(String nome, double valorHora, String tamanho) {
        if(verificaValor(valorHora) == false) return;
        if(verificaTamanho(tamanho) == false) return;
        repositorioEspacos.adicionarEspacosET(nome, valorHora, tamanho);
    }

    public Espaco buscarEspaco(String nome) {
        try {
            return repositorioEspacos.buscarEspaco(nome);
        } catch (EspacoIndisponivelException e) {
            System.out.println("Não foi encontrado o Espaço.");
            return null;
        }
    }

    public void removerEspaco(String id) {
        try {
            repositorioEspacos.removerEspaco(id);
        } catch (EspacoIndisponivelException e) {
            System.out.println("Não foi possivel remover o Espaço.");
        }
    }

    public int tamanhoRepositorioEspacos() {
        return repositorioEspacos.tamanhoRepositorioEspacos();
    }


    // Usa Repositório de Reservas

    public StringBuilder reservasDoCliente(Cliente cliente) {
        return repositorioReservas.listarReservasDoCliente(cliente);
    }

    public void adicionarReserva() {
        repositorioReservas.adicionarReserva();
    }

    public Reserva buscarReserva(int id) {
        try {
            return repositorioReservas.buscarReserva(id);
        } catch (ReservaNaoEncontradaException e) {
            System.out.println("Reserva não encontrada.");
            return null;
        }
    }

    public Reserva buscarReservaCliente(String cpf, String nome) {
        if(isCpf(cpf)) {System.out.println("CPF invalido.");; return null;}
        return repositorioReservas.buscarReservaCliente(buscarCliente(formatarCpf(cpf)), buscarEspaco(nome));
    }

    public boolean removerReserva(int id) {
        try {
            repositorioReservas.removerReserva(id);
            return true;
        } catch (ReservaNaoEncontradaException e) {
            System.out.println("Reserva não encontrada.");
            return false;
        }
    }

    public int tamanhoRepositorioReservas() {
        return this.repositorioReservas.tamanhoRepositorioReservas();
    }

    public boolean cancelarReserva(int id, String cpf) {
        if(isCpf(cpf)) return false;
        return repositorioReservas.cancelarReserva(id, this.buscarCliente(formatarCpf(cpf)));
    }

    public StringBuilder relatoriosDosServicos() {
        return repositorioReservas.servicosAdicionais();
    }


    // Usa Vários Repositórios

    public StringBuilder espacosComReservas() {
        StringBuilder b = new StringBuilder();
        Collection<Espaco> esp = repositorioEspacos.todosOsEspacos();
        for (Espaco espaco : esp) {
            b.append(repositorioEspacos.espacosComReservas(espaco))
            .append(repositorioReservas.horaisTotais(espaco));
        }
        return b;
    }

    public boolean fazerReserva(String cpf, String nomeEspaco, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim) {
        if(repositorioClientes.tamanhoRepositorioClientes() > 0 && 
        repositorioEspacos.tamanhoRepositorioEspacos() > 0 && repositorioReservas.tamanhoRepositorioReservas() > 0) {
            try {
                if(isCpf(cpf)) {System.out.println("CPF invalido!"); return false;}
                if(dataIsAnterior(dataReserva)) {System.out.println("A reserva não pode ser feita."); return false;}
                if(dataReserva == null && horaInicio == null && horaFim == null) {System.out.println("\nErro ao fazer a reserva."); return false;}
                if(horaInicio.isAfter(horaFim)) {System.out.println("A reserva só pode ser no mesmo dia."); return false;}
                boolean c = repositorioReservas.fazerReserva(repositorioClientes.buscarCliente(formatarCpf(cpf)), repositorioEspacos.buscarEspaco(nomeEspaco), dataReserva, horaInicio, horaFim);
                if(c){
                    System.out.println("Reserva Feita.");
                    return true;
                }else{
                    System.out.println("Não foi possivel fazer a reserva.");
                    return false;
                }
            } catch (ClienteNaoEncontradoException e) {
                System.out.println("Erro! Cliente não encontrado.");
                return false;
            } catch (EspacoIndisponivelException e) {
                System.out.println("Erro! Espaço não encontrado.");
                return false;
            }
        }
        return false;
    }

    public StringBuilder receitasPorCliente() {
        StringBuilder b = new StringBuilder();
        Collection<Cliente> clientes = repositorioClientes.todosOsClientes();
        for (Cliente cliente : clientes) {
            b.append("\n")
            .append("Cliente " + cliente.getNome())
            .append(" - Receita: " + repositorioReservas.receitaPorCliente(cliente));
        }
        return b;
    }

    public StringBuilder receitasPorEspaco() {
        StringBuilder b = new StringBuilder();
        Collection<Espaco> espacos = repositorioEspacos.todosOsEspacos();
        for (Espaco espaco : espacos) {
            b.append("\n")
            .append("Espaço " + espaco.getNome())
            .append(" - Receita: " + repositorioReservas.receitaPorEspaco(espaco));
        }
        return b;
    }

    public double receitoNoDia() {
        LocalDate d = LocalDate.now();
        LocalTime t = LocalTime.now();
        return repositorioReservas.reservasFeitasNoDia(d, t);
    }

    public void salvarDados(String p) {
        try {
            repositorioClientes.salvarArquivo("src\\workhub\\dados\\clientes.dat");
            repositorioReservas.salvarArquivo("src\\workhub\\dados\\reservas.dat");
            repositorioEspacos.salvarArquivo("src\\workhub\\dados\\espacos.dat");
            System.out.println(p + "\n");
        } catch (FalhaPersistenciaException e) {
            System.out.println("Erro no Salvamento de dados.\n");
        }
    }

    public void carregarDados() {
        try {
            repositorioClientes.carregarArquivo("src\\workhub\\dados\\clientes.dat");
            repositorioReservas.carregarArquivo("src\\workhub\\dados\\reservas.dat");
            repositorioEspacos.carregarArquivo("src\\workhub\\dados\\espacos.dat");
        } catch (FalhaPersistenciaException e) {
            System.out.println("Erro no Carregamento de dados.\n");
        }
    }


    
}
