package com.inventario.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonApiResponse<T> {
    private T data;
    private Object meta;

    public JsonApiResponse(T data) {
        this.data = data;
    }

    public JsonApiResponse(T data, Object meta) {
        this.data = data;
        this.meta = meta;
    }
}