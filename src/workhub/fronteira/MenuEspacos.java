package workhub.fronteira;

import java.util.Scanner;

import workhub.controle.AdministradorSistema;

public class MenuEspacos {
    private AdministradorSistema administradorSistema;

    public MenuEspacos(AdministradorSistema administradorSistema) {
        this.administradorSistema = administradorSistema;
    }

    public void limparTela() {
        try {
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

    private String geString(String p) {
        System.out.print(p);
        Scanner r = new Scanner(System.in);
        if(r.hasNextLine()) {
            return r.nextLine();
        }
        return "";
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
        System.out.println("======= MENU ESPACOS =======\n1)Cadastrar Espaco\n2)Remover Espaco\n3)Voltar");
        opcao = getInt("Escolha a opção: ");
        if(opcao != 1 & opcao != 2 & opcao != 3) {
            opcao = 0;
        }
        return opcao;
    }

    public void acoes(int opcao) {
        int escolha;
        switch (opcao) {
            case 1:
                do {
                    System.out.println("======= MENU ESPAÇO CADASTRO =======\n1)Cadastrar Auditorio\n2)Cadastrar Sala Privada\n3)Cadastrar Sala Reunião\n4)Cadastrar Estação de Trabalho\n5)Voltar");
                    escolha = getInt("Escolha a opção: ");
                    escolher(escolha);
                    limparTela();
                }while(escolha != 5);
                break;
            case 2:
                administradorSistema.removerEspaco(geString("ID do Espaço: "));
                limparTela();
                break;
        }
    }

    public void escolher(int escolha) {
        switch (escolha) {
            case 1:
                administradorSistema.adicionarEspacosAU(geString("Nome do Espaço: "), (double) getInt("Valor por Hora: "), geString("Qual o Tamanho? Pequeno? Médio? Grande? "));
                break;
            case 2:
                administradorSistema.adicionarEspacosSP(geString("Nome do Espaço: "), (double) getInt("Valor por Hora: "), geString("Qual o Tamanho? Pequeno? Médio? Grande? "));
                break;
            case 3:
                administradorSistema.adicionarEspacosSR(geString("Nome do Espaço: "), (double) getInt("Valor por Hora: "), geString("Qual o Tamanho? Pequeno? Médio? Grande? "));
                break;
            case 4:
                administradorSistema.adicionarEspacosET(geString("Nome do Espaço: "), (double) getInt("Valor por Hora: "), geString("Qual o Tamanho? Pequeno? Médio? Grande? "));
                break;
        }
    }
}
