package workhub.controle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import workhub.entidades.Auditorio;
import workhub.entidades.Espaco;
import workhub.entidades.EstacaoTrabalho;
import workhub.entidades.SalaPrivada;
import workhub.entidades.SalaReuniao;
import workhub.excecoes.EspacoIndisponivelException;
import workhub.excecoes.FalhaPersistenciaException;

public class RepositorioEspacos {
    private Map<String, Espaco> espacos;

    public RepositorioEspacos() {
        this.espacos = new HashMap<>();
    }

    public void adicionarEspacosET(String nome, double valorHora, String tamanho) {
        Espaco e = new EstacaoTrabalho(nome, valorHora, tamanho);
        espacos.put(e.getId(), e);
    }

    public void adicionarEspacosSP(String nome, double valorHora, String tamanho) {
        Espaco e = new SalaPrivada(nome, valorHora, tamanho);
        espacos.put(e.getId(), e);
    }

    public void adicionarEspacosSR(String nome, double valorHora, String tamanho) {
        Espaco e = new SalaReuniao(nome, valorHora, tamanho);
        espacos.put(e.getId(), e);
    }

    public void adicionarEspacosAU(String nome, double valorHora, String tamanho) {
        Espaco e = new Auditorio(nome, valorHora, tamanho);
        espacos.put(e.getId(), e);
    }

    public Espaco buscarEspaco(String nome) throws EspacoIndisponivelException{
        Collection<Espaco> valores = espacos.values();
        for (Espaco espaco : valores) {
            if(espaco.getNome().equals(nome)) {
                return espaco;
            }
        }
        throw new EspacoIndisponivelException("Reserva não foi encontrada.");
    }

    public void removerEspaco(String id) throws EspacoIndisponivelException{
        if(espacos.get(id) == null) {
            throw new EspacoIndisponivelException("Espaço não encontrado.");
        }
        espacos.remove(id);
    }

    public int tamanhoRepositorioEspacos() {
        return espacos.size();
    }

    public Collection<Espaco> todosOsEspacos() {
        Collection<Espaco> esp = espacos.values();
        return esp;
    }

    public StringBuilder espacosComReservas(Espaco espaco) {
        StringBuilder b = new StringBuilder();
        b.append("\n")
        .append("Espaço " + espaco.getId())
        .append(":\nQuantidades de Reservas: " + espaco.getQtdReservas())
        .append(" - Quantas Reservas já Teve: " + espaco.getQuantasReservasTeve());
        return b;
    }

    public void salvarArquivo(String caminho) throws FalhaPersistenciaException {
        try (FileOutputStream fileOut = new FileOutputStream(caminho);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut)) {
            oos.writeObject(espacos);
        } catch (IOException e) {
            throw new FalhaPersistenciaException("Falha ao salvar pedidos no arquivo");
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarArquivo(String caminho) throws FalhaPersistenciaException {
        try (FileInputStream fileIn = new FileInputStream(caminho);
            ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            espacos = (Map<String, Espaco>) ois.readObject();
            proximoEspaco();
        } catch (IOException | ClassNotFoundException e) {
            throw new FalhaPersistenciaException("Falha ao carregar pedidos do arquivo");
        }
    }

    public void proximoEspaco() {
        Espaco e = new Auditorio("", 0, "");
        e.setProx(this.tamanhoRepositorioEspacos());
    }
}
