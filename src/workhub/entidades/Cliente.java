package workhub.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Cliente implements Serializable{
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataCadastro;

    public Cliente(String cpf, String nome, String email, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = LocalDate.now();
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getNome() {
        return this.nome;
    }

    @Override
    public String toString() {
        return "Nome: " + this.nome +
        " - CPF: " + this.cpf + 
        " - Email: " + this.email +
        " - Telefone: " + this.telefone +
        " - Data do Cadastro: " + this.dataCadastro;
    }

    public String getSituacao() {
        return "Nome: " + this.nome +
        " - CPF: " + this.cpf + 
        " - Email: " + this.email +
        " - Telefone: " + this.telefone +
        " - Data do Cadastro: " + this.dataCadastro;
    }

    // Método equals() sobrescrito
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }
}
