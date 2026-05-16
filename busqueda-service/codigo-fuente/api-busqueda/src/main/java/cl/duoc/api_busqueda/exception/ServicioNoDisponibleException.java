package cl.duoc.api_busqueda.exception;

public class ServicioNoDisponibleException extends RuntimeException {
    public ServicioNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
