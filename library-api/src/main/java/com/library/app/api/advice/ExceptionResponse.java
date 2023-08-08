package com.library.app.api.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.app.services.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ExceptionResponse {
    @NonNull
    private ExceptionCode code;

    private Object data;
}
