package ru.gold_farm_app.income_control.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.NexushubUser;
import ru.gold_farm_app.income_control.repository.NexushubUserRepository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Service
public class NexushubServiceImpl implements NexushubService {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private NexushubUserRepository repository;

    @Override
    public void register() {
        ObjectNode jsonToRequest = mapper.createObjectNode();
        NexushubUser user = NexushubUser.builder()
                .user_id("fastrapier12")
                .user_secret("qwerty13")
                .createdOn(LocalDateTime.now())
                .build();

        jsonToRequest.put("user_id", user.getUser_id());
        jsonToRequest.put("user_secret", user.getUser_secret());

        HttpClient client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://auth.nexushub.co/register"))
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonToRequest.toString()))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode jsonFromResponse = mapper.readTree(response.body());
            user.setUser_key(jsonFromResponse.get("user_key").asText());
            repository.save(user);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void authenticate() {
        NexushubUser user = repository.getById(1L);
        ObjectNode jsonToRequest = mapper.createObjectNode();
        jsonToRequest.put("user_key", user.getUser_key());
        jsonToRequest.put("user_secret", user.getUser_secret());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        request = HttpRequest.newBuilder()
                .uri(URI.create("https://auth.nexushub.co/authenticate"))
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonToRequest.toString()))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode jsonFromResponse = mapper.readTree(response.body());
            user.setAccess_token(jsonFromResponse.get("access_token").asText());
            user.setRefresh_token(jsonFromResponse.get("refresh_token").asText());
            repository.save(user);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Scheduled(fixedRate = 60 * 60 * 1000, initialDelay = 60 * 60 * 500)
    public void refresh() {
        NexushubUser user = repository.getById(1L);
        ObjectNode jsonToRequest = mapper.createObjectNode();
        jsonToRequest.put("refresh_token", user.getRefresh_token());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://auth.nexushub.co/refresh"))
                    .header("Content-type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(user)))
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode jsonFromResponse = mapper.readTree(response.body());
            user.setAccess_token(jsonFromResponse.get("access_token").asText());
            repository.save(user);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
