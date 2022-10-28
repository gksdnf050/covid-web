package com.covid.web.model.transfer.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.covid.web.exception.Error;

@Data
@AllArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ResponseBase<T> {
    Boolean success;
    Error error;
    T payload;

    public static <T> ResponseEntity<ResponseBase> success(){
        return ResponseEntity.ok(
                ResponseBase.of(true, null, null));
    }

    public static <T> ResponseEntity<ResponseBase> success(T payload){
        return ResponseEntity.ok(
                ResponseBase.of(true, null, payload));
    }

    public static ResponseEntity<ResponseBase> fail(Error error, HttpStatus status){
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ResponseBase.of(false, error, null));
    }
}