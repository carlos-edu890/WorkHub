package workhub.fronteira;

import java.util.Scanner;

import workhub.controle.AdministradorSistema;

public class MenuReservas {
    private AdministradorSistema administradorSistema;

    public MenuReservas(AdministradorSistema administradorSistema) {
        this.administradorSistema = administradorSistema;
    }

    public int getInt(String p) {
        System.out.print(p);
        Scanner r = new Scanner(System.in);
        if(r.hasNextInt()) {
            return r.nextInt();
        }
        return 0;
    }

    public void limparTela() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.println("Erro ao limpar o console no Windows: " + e.getMessage());
        }
    }

    public void executar() {
        int opcao;
        do {
            opcao = getOpcao();
            acoes(opcao);
        }while(opcao != 3);
    }

    public int getOpcao() {
        int opcao = 0;
        System.out.println("======= MENU RESERVAS =======\n1)Cadastrar Reserva\n2)Remover Reserva\n3)Voltar");
        opcao = getInt("Escolha a opção: ");
        if(opcao != 1 & opcao != 2 & opcao != 3) {
            opcao = 0;
        }
        return opcao;
    }

    public void acoes(int opcao) {
        switch (opcao) {
            case 1:
                administradorSistema.adicionarReserva();

                limparTela();
                break;
            case 2:
                administradorSistema.removerReserva(getInt("ID da Reserva: "));

                limparTela();
                break;
        }
    }
}
