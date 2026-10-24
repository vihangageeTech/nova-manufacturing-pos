package com.example.pos.network;

import org.springframework.http.HttpStatus;

/** Internal service result for a single response payload. */
public record ResponseData<T>(T data, HttpStatus httpStatus, String message) {
    public static <T> ResponseData<T> success(T data, String message) {
        return new ResponseData<>(data, HttpStatus.OK, message);
    }

    public static <T> ResponseData<T> created(T data, String message) {
        return new ResponseData<>(data, HttpStatus.CREATED, message);
    }

    public static <T> ResponseData<T> error(HttpStatus httpStatus, String message) {
        return new ResponseData<>(null, httpStatus, message);
    }
}
