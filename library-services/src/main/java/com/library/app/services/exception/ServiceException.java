package com.library.app.services.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException {
    @NonNull
    private final ExceptionCode code;

    private final String message;

    public ServiceException(@NonNull final ExceptionCode code,
                             final String message,
                             final String... args) {
        this.code = code;

        if (args.length > 0) {
            this.message = String.format(message, (Object[]) args);
        } else {
            this.message = message;
        }
    }
}
