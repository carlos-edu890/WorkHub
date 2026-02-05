package workhub.entidades;

import java.io.Serializable;
import java.util.Objects;

public abstract class Espaco implements Serializable{
    String id;
    String nome;
    double valorHora;
    boolean disponivel;
    String tamanho;
    int qtdReservas;
    int quantasReservasTeve;
    static int numero = 0;

    public Espaco(String nome, double valorHora, String tamanho) {
        this.nome = nome;
        this.valorHora = valorHora;
        this.id = getTipo() + numeracao();
        this.disponivel = true;
        this.tamanho = tamanho;
        this.qtdReservas = 0;
        this.quantasReservasTeve = 0;
    }

    public String numeracao() {
        numero++;
        String textonumero = String.format("%03d", numero);
        return textonumero;
    }

    public void setProx(int n) {
        this.numero = n;
    }

    public String getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public double getValorHora() {
        return this.valorHora;
    }

    public int getQtdReservas() {
        return this.qtdReservas;
    }

    public int getQuantasReservasTeve() {
        return this.quantasReservasTeve;
    }
    abstract String getTamanho();
    abstract String getTipo();
    abstract void setDisponivel();
    abstract int qtdReservas(int i);
    abstract void setQuantasReservasTeve();
    abstract String isDisponivel();
    abstract String getDescricaoCompleta();
    public String toString() {
        return "Id: " + this.id + 
        " - Nome: " + this.nome + 
        " - Tamanho da Espaço: " + this.tamanho + 
        " - Valor Por Hora: " + this.valorHora + 
        " - Disponibilidade: " + this.isDisponivel();
    }

    public static void main(String[] args) {
        Espaco e = new EstacaoTrabalho("", 0, "");
        Espaco e1 = new Auditorio("", 0, "");

        System.out.println(e.toString());
        System.out.println(e1.toString());
    }

    // Método equals() sobrescrito
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Espaco espaco = (Espaco) o;
        return Objects.equals(id, espaco.id);
    }
}
