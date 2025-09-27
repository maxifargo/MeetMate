package agenda;

public class HoraInvalidaException extends Exception {
    private static final long serialVersionUID = 1L;

    public HoraInvalidaException(String mensaje) {
        super(mensaje);
    }
}
