package workhub.entidades;

public class CafePremium implements ServicoAdicional{
    private double valorTotal;
    private String descricao;

    public CafePremium(String descricao, double valorTotal) {
        this.descricao = descricao;
        this.valorTotal = valorTotal;
    }

    @Override
    public String getDescricao() {
        return this.descricao;
    }

    @Override
    public double getValorTotal() {
        return this.valorTotal;
    }
}
