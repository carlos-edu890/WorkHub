package workhub.fronteira;

import java.util.Scanner;

import workhub.controle.AdministradorSistema;

public class MenuClientes {
    private boolean modo = true;
    private AdministradorSistema administradorSistema;

    public MenuClientes(AdministradorSistema administradorSistema) {
        this.administradorSistema = administradorSistema;
    }

    public void limparTela() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.println("Erro ao limpar o console no Windows: " + e.getMessage());
        }
    }

    public void setModo() {
        if (modo) {
            this.modo = false;
        } else {
            this.modo = true;
        }
    }

    public char getChar(String p) {
        System.out.print(p);
        Scanner r = new Scanner(System.in);
        if(r.hasNext()) {
            return r.next().charAt(0);
        }
        return 'o';
    }

    public int getInt(String p) {
        System.out.print(p);
        Scanner r = new Scanner(System.in);
        if(r.hasNextInt()) {
            return r.nextInt();
        }
        return 0;
    }

    private String geString(String p) {
        System.out.print(p);
        Scanner r = new Scanner(System.in);
        if(r.hasNextLine()) {
            return r.nextLine();
        }
        return "";
    }

    private String enter(String p) {
        System.out.print(p);
        Scanner r = new Scanner(System.in);
        return r.nextLine();
    }

    public void executar() {
        int opcao;
        do{
            opcao = getOpcao();
            if(this.modo) {
                acoes(opcao);
            } else {
                gerenciar(opcao);
            }
        }while(opcao != 4);
    }

    public int getOpcao() {
        int opcao = 0;
        if(this.modo) {
            System.out.println("======= MENU GERENTE =======\n1)Cadastrar Cliente\n2)Remover Cliente\n3)Gerenciar Cliente\n4)Voltar");
            opcao = getInt("Escolha a opção: ");
            if(opcao != 1 & opcao != 2 & opcao != 3 & opcao != 4) {
                opcao = 0;
            }
            limparTela();
        } else if(administradorSistema.tamanhoRepositorioClientes() > 0) {
            limparTela();
            System.out.println("======= MENU GERENCIAMENTO CLIENTE =======\n1)Fazer Reserva\n2)Cancelar Reserva\n3)Voltar");
            opcao = getInt("Escolha a opção: ");
            if(opcao != 1 & opcao != 2 & opcao != 3) {
                opcao = 0;
            }
            limparTela();
        } else {
            System.out.println("Não existe nenhum cliente cadastrado.\n");
            enter("[PRESSIONE ENTER]");
            setModo();
        }
        return opcao;
    }

    public void acoes(int opcao) {
        switch (opcao) {
            case 1:
                if(administradorSistema.adicionarCliente(geString("Numero de CPF: "), geString("Nome do Cliente: "), 
                geString("Email do Cliente: "), geString("Telefone do Cliente: "))) administradorSistema.salvarDados("O Cliente foi cadastrado.");
                break;
            case 2:
                if(administradorSistema.removerCliente(geString("CPF do Cliente: "))) administradorSistema.salvarDados("");
                break;
            case 3:
                setModo();
                break;
        }
    }

    public void gerenciar(int opcao) {
        switch (opcao) {
            case 1:
                if(administradorSistema.fazerReserva(geString("CPF do Cliente: "), geString("Nome do Espaço: "), 
                administradorSistema.data(getInt("Dia da Reserva: "), getInt("Mês da Reserva: ")), 
                administradorSistema.horaInicio(getInt("Hora de inicio: "), getInt("Minutos: ")), 
                administradorSistema.horaFim(getInt("Hora de fim: "), getInt("Minuto: ")))) {
                    administradorSistema.salvarDados("");
                    enter("[PRESSIONE ENTER]");
                    adicionarServicoAdicional();
                }
                enter("[PRESSIONE ENTER]");
                limparTela();
                break;
            case 2:
                if(administradorSistema.cancelarReserva(getInt("ID da Reserva: "), geString("CPF do Cliente: "))) administradorSistema.salvarDados("Foi cancelado.");
                enter("[PRESSIONE ENTER]");
                break;
            case 3:
                setModo();
                break;
        }
    }

    public void adicionarServicoAdicional() {
        int opcao;
        char sORn;
        do {
            limparTela();
            sORn = getChar("Quer um Serviço Adicional (S ou N)? ");
            limparTela();
            if(sORn == 'S' || sORn == 's') {
                System.out.println("======= MENU SERVIÇO ADICIONAL =======\n1)Adicionar Locker\n2)Adicionar Café Premium\n3)Adicionar Estacionamento\n4)Adicionar Recebimento de Correspondência\n");
                opcao = getInt("Escolha a opção: ");
                aSA(opcao);
            }
        }while(sORn != 'N' && sORn != 'n');
    }

    public void aSA(int opcao) {
        switch (opcao) {
            case 1:
                administradorSistema.buscarReservaCliente(geString("CPF do Cliente: "), geString("Nome do Espaço: ")).adicionarServicoAdicionalLO(geString("Descrição do Locker: "), (double) getInt("Valor Total do Serviço:"));
                break;
            case 2:
                administradorSistema.buscarReservaCliente(geString("CPF do Cliente: "), geString("Nome do Espaço: ")).adicionarServicoAdicionalCP(geString("Descrição do Café Premium: "), (double) getInt("Valor Total do Serviço:"));
                break;
            case 3:
                administradorSistema.buscarReservaCliente(geString("CPF do Cliente: "), geString("Nome do Espaço: ")).adicionarServicoAdicionalES(geString("Descrição do Estacionamento: "), (double) getInt("Valor Total do Serviço:"));
                break;
            case 4:
                administradorSistema.buscarReservaCliente(geString("CPF do Cliente: "), geString("Nome do Espaço: ")).adicionarServicoAdicionalRC(geString("Descrição do Recebimento de Correspondência: "), (double) getInt("Valor Total do Serviço:"));
                break;
        }
    }
}
