package workhub.fronteira;

import java.util.Scanner;

import workhub.controle.AdministradorSistema;

public class MenuRelatorios {
    private AdministradorSistema administradorSistema;

    public MenuRelatorios(AdministradorSistema administradorSistema) {
        this.administradorSistema = administradorSistema;
    }

    public void limparTela() {
        try { // 'cmd' - acessa o prompt de comando; '/c' - ao terminar de executar o comando seguinte deve fechar o cmd; 'cls' - limpa a tela
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.println("Erro ao limpar o console no Windows: " + e.getMessage());
        }
    }

    public int getInt(String p) {
        System.out.print(p);
        Scanner r = new Scanner(System.in);
        if(r.hasNextInt()) {
            return r.nextInt();
        }
        return 0;
    }

    private String getString(String p) {
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
            acoes(opcao);
        }while(opcao != 5);
    }

    public int getOpcao() {
        int opcao;
        System.out.println("======= MENU RELATÓRIOS =======\n1)Reservas por Cliente\n2)Utilização de Espaços\n3)Faturamento\n4)Serviços Adicionais\n5)Voltar\n");
        opcao = getInt("Escolha a opção: ");
        return opcao;
    }

    public void acoes(int opcao) {
        switch (opcao) {
            case 1:
                System.out.println(administradorSistema.reservasDoCliente(administradorSistema.buscarCliente(getString("CPF do Cliente: "))));
                enter("\n[PRESSIONE ENTER]");
                limparTela();
                break;
            case 2:
                System.out.println(administradorSistema.espacosComReservas());
                enter("[PRESSIONE ENTER]");
                limparTela();
                break;
            case 3:
                acoes2();
                break;
            case 4:
                System.out.println(administradorSistema.relatoriosDosServicos());
                enter("[PRESSIONE ENTER]");
                limparTela();
                break;
        }
    }

    public void acoes2() {
        int opcao;
        do {
            System.out.println("======= FATURAMENTO =======\n1)Por Dia\n2)Por Espaço\n3)Por Cliente\n4)Voltar");
            opcao = getInt("Escolha a opção: ");
            switch (opcao) {
                case 1:
                    System.out.println(administradorSistema.receitoNoDia());
                    enter("[PRESSIONE ENTER]");
                    limparTela();
                    break;
                case 2:
                    System.out.println(administradorSistema.receitasPorEspaco());
                    enter("[PRESSIONE ENTER]");
                    limparTela();
                    break;
                case 3:
                    System.out.println(administradorSistema.receitasPorCliente());
                    enter("[PRESSIONE ENTER]");
                    limparTela();
                    break;
            }
        } while (opcao != 4);
    }
}
