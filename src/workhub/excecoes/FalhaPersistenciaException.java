package workhub.excecoes;

public class FalhaPersistenciaException extends Exception{

    public FalhaPersistenciaException(String mensagem) {
        super(mensagem);
    }
}
