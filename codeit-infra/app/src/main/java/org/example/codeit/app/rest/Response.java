package org.example.codeit.app.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response {
    public static ResponseEntity<Object> handleError(String message, Object reason, HttpStatus httpStatus) {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("timestamp",getTimestamp());
        hm.put("status", httpStatus.value());
        hm.put("message",message);
        if (reason != null) hm.put("reason",reason);
        return new ResponseEntity<>(hm, new HttpHeaders(), httpStatus);
    }

    public static ResponseEntity<Object> handleGet(Object obj) {
        return new ResponseEntity<>(
                obj,
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    public static ResponseEntity<Object> handlePost(Object obj) {
        return new ResponseEntity<>(
                obj,
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    public static ResponseEntity<Object> handleDelete() {
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    public static ResponseEntity<Object> handlePatch(Object obj) {
        return new ResponseEntity<>(
                obj,
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Timestamp(System.currentTimeMillis()));
    }
}
