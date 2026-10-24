package com.example.pos.network;

import org.springframework.http.HttpStatus;

import java.util.List;

/** Internal service result for a collection response. */
public record ResponseDataPack<T>(List<T> data, HttpStatus httpStatus, String message) {
    public static <T> ResponseDataPack<T> success(List<T> data, String message) {
        return new ResponseDataPack<>(data, HttpStatus.OK, message);
    }

    public static <T> ResponseDataPack<T> error(HttpStatus httpStatus, String message) {
        return new ResponseDataPack<>(null, httpStatus, message);
    }
}
