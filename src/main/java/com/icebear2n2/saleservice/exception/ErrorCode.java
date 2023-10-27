package com.icebear2n2.saleservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT NOT FOUND."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
