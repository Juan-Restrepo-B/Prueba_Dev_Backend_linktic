package com.inventario.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ProductoClient {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.product-service-url}")
    private String productServiceUrl;

    @Value("${app.api-key}")
    private String apiKey;

    public String obtenerProductoPorId(Long id) {
        String url = productServiceUrl + "/productos/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}