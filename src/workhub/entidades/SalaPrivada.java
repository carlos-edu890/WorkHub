package workhub.entidades;

public class SalaPrivada extends Espaco{
    
    public SalaPrivada(String nome, double valorHora, String tamanho) {
        super(nome, valorHora, tamanho);
    }

    public String getId() {
        return this.id;
    }

    public String getTipo() {
        return "SP";
    }

    public String getTamanho() {
        return this.tamanho;
    }

    public int qtdReservas(int i) {
        return this.qtdReservas += i;
    }

    public void setQuantasReservasTeve() {
        this.quantasReservasTeve++;
    }

    public void setDisponivel() {
        if(this.disponivel)
            this.disponivel = false;
        else
            this.disponivel = true;
    }

    public String isDisponivel() {
        if(this.disponivel == true) {
            return "Está Disponível";
        }
        return "Não está Disponível";
    }

    public String getDescricaoCompleta() {
        return "Id: " + this.id + 
        " - Nome: " + this.nome + 
        " - Tamanho da Sala Privada: " + this.tamanho +
        " - Valor Por Hora: " + this.valorHora + 
        " - Disponibilidade: " + this.isDisponivel();
    }

    @Override
    public String toString() {
        return "Id: " + this.id + 
        " - Nome: " + this.nome + 
        " - Tamanho da Sala Privada: " + this.tamanho +
        " - Valor Por Hora: " + this.valorHora + 
        " - Disponibilidade: " + this.isDisponivel();
    }
}
