package ru.gold_farm_app.income_control.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.Resource;
import ru.gold_farm_app.income_control.model.ServerFraction;
import ru.gold_farm_app.income_control.repository.ResourceRepository;
import ru.gold_farm_app.income_control.services.ResourceService;
import ru.gold_farm_app.income_control.services.ServerFractionService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ServerFractionService serverFractionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void addAll() {
        List<ServerFraction> serverFractions = serverFractionService.getAllServerFractions();
        for (ServerFraction serverFraction : serverFractions) {
            for (Map.Entry<Integer, String> resource : RESOURCES_MAP.entrySet()) {
                StringBuilder urlBuilder = new StringBuilder("https://api.nexushub.co/wow-classic/v1/items/")
                        .append(serverFraction.getServerFraction()).append("/").append(resource.getKey());
                try {
                    var url = new URL(urlBuilder.toString());
                    JsonNode json = objectMapper.readTree(url);
                    resourceRepository.save(Resource.builder()
                            .itemId(resource.getKey().longValue())
                            .serverFraction(serverFraction)
                            .scannedOn(LocalDateTime.now())
                            .uniqueName(resource.getValue())
                            .name(json.get("uniqueName").asText())
                            .price(json.get("stats").get("current").get("minBuyout").asLong())
                            .name(json.get("name").asText())
                            .build());
                    Thread.sleep(200);
                } catch (IOException | InterruptedException e) {
                    logger.info(e.toString());
                }
            }
        }
    }


    @Override
    public void updatePrice(Long id) {
        Resource resource = resourceRepository.getById(id);
        StringBuilder urlBuilder = new StringBuilder("https://api.nexushub.co/wow-classic/v1/items/")
                .append(resource.getServerFraction()).append("/").append(resource.getItemId());
        try {
            var url = new URL(urlBuilder.toString());
            JsonNode json = objectMapper.readTree(url);
            resource.setPrice(json.get("stats").get("current").get("minBuyout").asLong());
            resource.setScannedOn(LocalDateTime.parse(json.get("stats").get("lastUpdated").asText()));
            resourceRepository.save(resource);
            Thread.sleep(200);
        } catch (IOException | InterruptedException e) {
            logger.info(e.toString());
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 3)
    @Override
    public void updateAll() {
        List<Resource> resources = resourceRepository.findAll();
        for (Resource resource : resources) {
            updatePrice(resource.getId());
        }
    }

    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

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
            entry(22457, "primal-mana"),
            entry(21884, "primal-fire"),
            entry(21886, "primal-life"),
            entry(22452, "primal-earth"),
            entry(22451, "primal-air")
    );
}
