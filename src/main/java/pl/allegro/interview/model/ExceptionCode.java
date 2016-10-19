package pl.allegro.interview.model;

public enum ExceptionCode {

    EXC_000("The application has encountered an unknown error."),
    EXC_001("Github repository with this owner and name could not be found."),
    EXC_002("Github API is currently unavailable. Try later.");

    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
