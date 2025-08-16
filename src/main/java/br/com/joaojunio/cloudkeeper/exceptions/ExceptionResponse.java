package br.com.joaojunio.cloudkeeper.exceptions;

import java.util.Date;

public record ExceptionResponse(String message, String details, Date timestamp) {
}
