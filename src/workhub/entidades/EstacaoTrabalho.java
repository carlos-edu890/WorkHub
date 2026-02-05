package workhub.entidades;

public class EstacaoTrabalho extends Espaco{

    public EstacaoTrabalho(String nome, double valorHora, String tamanhoEstacao) {
        super(nome, valorHora, tamanhoEstacao);
    }

    public String getId() {
        return this.id;
    }

    public String getTamanho() {
        return this.tamanho;
    }

    public String getTipo() {
        return "ET";
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
        " - Tamanho da Estacao: " + this.tamanho + 
        " - Valor Por Hora: " + this.valorHora + 
        " - Disponibilidade: " + this.isDisponivel();
    }

    @Override
    public String toString() {
        return "Id: " + this.id + 
        " - Nome: " + this.nome + 
        " - Tamanho da Estacao: " + this.tamanho + 
        " - Valor Por Hora: " + this.valorHora + 
        " - Disponibilidade: " + this.isDisponivel();
    }
}
