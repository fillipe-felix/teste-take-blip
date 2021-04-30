package com.takeblip.testetakeblip.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeblip.testetakeblip.api.entity.TakeBlip;
import com.takeblip.testetakeblip.api.repository.TakeBlipRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TakeBlipService {

    @Autowired
    private TakeBlipRepository takeBlipRepository;

    private static final String url = "https://api.github.com/orgs/takenet/repos?per_page=100&sort=created";

    public List<TakeBlip> getRepositorios(String linguagem) {
        HttpRequest request = null;

        List<TakeBlip> listTake = new ArrayList<>();
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


            listTake = desseralizarObjeto(response.body());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return  listTake.stream()
                .sorted(Comparator.comparing(TakeBlip::getId))
                .filter(c -> c.getLanguage() != null && c.getLanguage().equals(linguagem))
                .collect(Collectors.toList()).subList(0, 5);
    }

    public List<TakeBlip> desseralizarObjeto(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, new TypeReference<List<TakeBlip>>(){});
    }
}
