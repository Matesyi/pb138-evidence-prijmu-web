package entities;

/**
 *
 * @author L
 */
public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException(String msg) {
        super(msg);
    }
    
    public DatabaseAccessException(Throwable cause) {
        super(cause);
    }
    
    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
