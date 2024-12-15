package com.moriya.project_moriya_java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    @GetMapping
    public ResponseEntity<?> getCities(@RequestParam int limit) {
        String apiUrl = "https://data.gov.il/api/3/action/datastore_search";
        String resourceId = "b7cf8f14-64a2-4b33-8d4b-edb286fdbd37";

        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("resource_id", resourceId)
                .queryParam("limit", limit)
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.ok(response.getBody());
    }


}
