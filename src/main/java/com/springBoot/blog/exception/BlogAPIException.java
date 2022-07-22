package com.springBoot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{

    private HttpStatus status;
    private String message;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BlogAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus(){
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
