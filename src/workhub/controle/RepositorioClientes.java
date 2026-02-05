package workhub.controle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import workhub.entidades.Cliente;
import workhub.entidades.Reserva;
import workhub.excecoes.ClienteJaCadastradoException;
import workhub.excecoes.ClienteNaoEncontradoException;
import workhub.excecoes.FalhaPersistenciaException;

public class RepositorioClientes {
    private Map<String, Cliente> clientes;

    public RepositorioClientes() {
        this.clientes = new HashMap<>();
    }

    public void adicionarCliente(Cliente cliente) throws ClienteJaCadastradoException{
        for (String key : clientes.keySet()) {
            if(key.equals(cliente.getCpf())){
                throw new ClienteJaCadastradoException("Esse CPF já foi cadastrado.");
            }
        }
        clientes.put(cliente.getCpf(), cliente);
    }

    public Cliente buscarCliente(String cpf) throws ClienteNaoEncontradoException{
        if(clientes.get(cpf) == null) {
            throw new ClienteNaoEncontradoException("O Cliente não foi encontrado.");
        }
        return clientes.get(cpf);
    }

    public void removerCliente(String cpf) throws ClienteNaoEncontradoException{
        if(clientes.get(cpf) == null) {
            throw new ClienteNaoEncontradoException("O Cliente não foi encontrado.");
        }
        clientes.remove(cpf);
    }

    public int tamanhoRepositorioClientes() {
        return clientes.size();
    }

    public Collection<Cliente> todosOsClientes() {
        Collection<Cliente> cli = clientes.values();
        return cli;
    }

    public void listarClientes() {
        
    }

    public void salvarArquivo(String caminho) throws FalhaPersistenciaException {
        try (FileOutputStream fileOut = new FileOutputStream(caminho);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            throw new FalhaPersistenciaException("Falha ao salvar pedidos no arquivo");
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarArquivo(String caminho) throws FalhaPersistenciaException {
        try (FileInputStream fileIn = new FileInputStream(caminho);
            ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            clientes = (Map<String, Cliente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FalhaPersistenciaException("Falha ao carregar pedidos do arquivo");
        }
    }

    public static void main(String[] args) {
        RepositorioClientes repositorioClientes = new RepositorioClientes();
        Cliente c = new Cliente("65", "bgr", "rew", "765");
        Cliente c1 = new Cliente("65", "vf", "re3", "76s");
        try {
            repositorioClientes.removerCliente("76");
        } catch (ClienteNaoEncontradoException e) {
            System.out.println("Cliente não Encontrado");   
        }
    }
}
