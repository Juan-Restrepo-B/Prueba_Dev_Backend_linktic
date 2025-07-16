package com.inventario.exception;

public class RequestException extends RuntimeException {
    private final String code;

    public RequestException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public boolean canEqual(Object other) {
        return other instanceof RequestException;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RequestException)) return false;
        RequestException other = (RequestException) obj;
        return this.getMessage().equals(other.getMessage())
                && this.code.equals(other.code)
                && other.canEqual(this);
    }

    @Override
    public int hashCode() {
        int result = getMessage().hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RequestException{code='" + code + "', message='" + getMessage() + "'}";
    }
}
