package zhomartov.alikhan.testproject.exception.domain;

public class UsernameExistException extends RuntimeException {
    public UsernameExistException(String message) {
        super(message);
    }
}

