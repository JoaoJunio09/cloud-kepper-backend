package br.com.joaojunio.cloudkeeper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ProcessingNotCompleted extends RuntimeException {
    public ProcessingNotCompleted(String message) {
        super(message);
    }
}
