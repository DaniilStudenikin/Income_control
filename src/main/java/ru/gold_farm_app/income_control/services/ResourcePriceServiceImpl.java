package ru.gold_farm_app.income_control.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.ResourcePrice;
import ru.gold_farm_app.income_control.repository.ResourcePriceRepository;

import static java.util.Map.entry;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ResourcePriceServiceImpl implements ResourcePriceService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourcePriceRepository repository;

    private List<String> servers = new ArrayList<>();

    private final Map<Integer, String> RESOURCES_MAP = Map.ofEntries(
            entry(22786, "dreaming-glory"),
            entry(22792, "nightmare-vine"),
            entry(22793, "mana-thistle"),
            entry(22797, "nightmare-seed"),
            entry(22794, "fel-lotus"),
            entry(23424, "fel-iron-ore"),
            entry(23427, "eternium-ore"),
            entry(23425, "adamantite-ore"),
            entry(23077, "blood-garnet"),
            entry(21929, "flame-spessarite"),
            entry(23112, "golden-draenite"),
            entry(23107, "shadow-draenite"),
            entry(23079, "deep-peridot"),
            entry(23117, "azure-moonstone"),
            entry(23440, "dawnstone"),
            entry(23441, "nightseye"),
            entry(23438, "star-of-elune"),
            entry(23437, "talasite"),
            entry(23436, "living-ruby"),
            entry(23439, "noble-topaz"),
            entry(22573, "mote-of-earth"),
            entry(22574, "mote-of-fire"),
            entry(22576, "mote-of-mana"),
            entry(22575, "mote-of-life")
    );


    @Override
    public void addAll() {
        for (String server : servers) {
            for (var entry : RESOURCES_MAP.entrySet()) {
                StringBuilder urlBuilder = new StringBuilder("https://api.nexushub.co/wow-classic/v1/items/")
                        .append(server).append("/").append(entry.getKey());
                try {
                    var url = new URL(urlBuilder.toString());
                    JsonNode json = objectMapper.readTree(url);

                    ResourcePrice resourcePrice = ResourcePrice.builder()
                            .itemId(Long.valueOf(entry.getKey()))
                            .uniqueName(entry.getValue())
                            .price(json.get("stats").get("current").get("minBuyout").asLong())
                            .scannedOn(LocalDateTime.now())
                            .name(json.get("name").asText())
                            .serverFraction(server)
                            .build();

                    repository.save(resourcePrice);


                    //Because 20 request per 5 sec
                    Thread.sleep(200);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    @Override
    public void updateData() {
        List<ResourcePrice> resourcePrices = repository.findAll();

        for (var resourcePrice : resourcePrices) {
            StringBuilder urlBuilder = new StringBuilder("https://api.nexushub.co/wow-classic/v1/items/")
                    .append(resourcePrice.getServerFraction()).append("/").append(resourcePrice.getItemId());
            try {
                var url = new URL(urlBuilder.toString());
                JsonNode json = objectMapper.readTree(url);
                resourcePrice.setPrice(json.get("stats").get("current").get("minBuyout").asLong());
                repository.save(resourcePrice);
                Thread.sleep(200);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void add(String server) {
        servers.add(server);
    }


    @Override
    public Optional<ResourcePrice> get(ResourcePrice resourcePrice) {
        return Optional.of(repository.getById(resourcePrice.getId()));
    }

    //TODO:сделать проверку на директорию чтобы добавить цены по новому серверу
    public void createDirectory() {
        var pathOfDirectory = new StringBuilder().append("src/main/resources/json_response/").append(LocalDate.now());
        try {
            Files.createDirectory(Path.of(pathOfDirectory.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
