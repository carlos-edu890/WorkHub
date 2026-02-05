package workhub.fronteira;

import java.util.Scanner;

import workhub.controle.AdministradorSistema;

public class MenuPrincipal {
    private MenuClientes menuClientes;
    private MenuReservas menuReservas;
    private MenuEspacos menuEspacos;
    private MenuRelatorios menuRelatorios;
    private AdministradorSistema administradorSistema;

    public MenuPrincipal() {
        this.administradorSistema = new AdministradorSistema();
        this.administradorSistema.carregarDados();

        this.menuClientes = new MenuClientes(administradorSistema);
        this.menuReservas = new MenuReservas(administradorSistema);
        this.menuEspacos = new MenuEspacos(administradorSistema);
        this.menuRelatorios = new MenuRelatorios(administradorSistema);
    }

    public void limparTela() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.println("Erro ao limpar o console no Windows: " + e.getMessage());
        }
    }

    private int getInt(String p) {
        System.out.print(p);
        Scanner r = new Scanner(System.in);
        if(r.hasNextInt()) {
            return r.nextInt();
        }
        return 0;
    }

    public void executar() {
        int opcao;
        do {
            opcao = getOpcao();
            limparTela();
            menus(opcao);
            limparTela();
        }while(opcao != 5);
        administradorSistema.salvarDados("Dados salvos com sucesso.");
    }

    public int getOpcao() {
        int opcao = 0;
        System.out.println("======= MENU GERENTE =======\n1)Menu Clientes\n2)Menu Reservas\n3)Menu Espaços\n4)Menu Relatórios\n5)Sair\n");
        opcao = getInt("Escolha a opcão: ");
        if(opcao != 1 & opcao != 2 & opcao != 3 & opcao != 4 & opcao != 5) {
            opcao = 0;
        }
        return opcao;
    }

    public void menus(int opcao) {
        switch (opcao) {
            case 1:
                menuClientes.executar();
                break;
            case 2:
                menuReservas.executar();
                break;
            case 3:
                menuEspacos.executar();
                break;
            case 4:
                menuRelatorios.executar();
                break;
        }
    }
}
