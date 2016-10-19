package pl.allegro.interview.model;

public class ExceptionResource {

    private final String code;
    private final String message;

    public ExceptionResource(ExceptionCode code) {
        this.code = code.name();
        this.message = code.getMessage();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
