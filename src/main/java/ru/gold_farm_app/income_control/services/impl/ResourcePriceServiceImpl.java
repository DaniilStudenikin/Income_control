package ru.gold_farm_app.income_control.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gold_farm_app.income_control.model.Resource;
import ru.gold_farm_app.income_control.model.ResourcePrice;
import ru.gold_farm_app.income_control.model.ServerFraction;
import ru.gold_farm_app.income_control.repository.EmployeeRepository;
import ru.gold_farm_app.income_control.repository.ResourcePriceRepository;
import ru.gold_farm_app.income_control.repository.ResourceRepository;
import ru.gold_farm_app.income_control.repository.ServerFractionRepository;
import ru.gold_farm_app.income_control.services.ResourcePriceService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourcePriceServiceImpl implements ResourcePriceService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourcePriceServiceImpl.class);

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ResourcePriceRepository resourcePriceRepository;

    @Autowired
    private ServerFractionRepository serverFractionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void priceAdd() {
        List<Resource> resources = resourceRepository.findAll();
        List<ServerFraction> serverFractions = serverFractionRepository.findAll();
        for (ServerFraction server : serverFractions) {
            for (Resource res : resources) {
                try {
                    var url = new URL("https://api.nexushub.co/wow-classic/v1/items/" + server.getServerFraction() + "/" + res.getItemId());
                    var json = objectMapper.readTree(url);
                    Long price = json.get("stats").get("current").get("minBuyout").asLong();
                    resourcePriceRepository.save(ResourcePrice.builder()
                            .resource(res)
                            .price((long) (price - price * 0.05))
                            .server(server)
                            .scannedOn(LocalDateTime.now())
                            .build());
                    Thread.sleep(300);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(initialDelay = 1000 * 60 * 60 * 2, fixedDelay = 1000 * 60 * 60 * 2)
    @Override
    @Transactional
    public void updatePrice() {
        LOG.info("Начало обновления цен");
        List<ResourcePrice> resourcePrices = resourcePriceRepository.findAll();
        for (ResourcePrice resP : resourcePrices) {
            try {
                var url = new URL("https://api.nexushub.co/wow-classic/v1/items/" + resP.getServer().getServerFraction() + "/" + resP.getResource().getItemId());
                var json = objectMapper.readTree(url);
                var price = json.get("stats").get("current").get("minBuyout").asLong();
                System.out.println(json.toString());
                resP.setPrice((long) (price - price * 0.05));
                resP.setScannedOn(LocalDateTime.now());
                resourcePriceRepository.save(resP);
                Thread.sleep(300);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.info("Обновление цен прошло успешно");
    }

    @Override
    public List<ResourcePrice> resourcePrices(MessageCreateEvent event) {
        List<Resource> resourceList = resourceRepository.findAll();
        List<ResourcePrice> resourcePrices = new ArrayList<>();
        for (Resource resource : resourceList) {
            resourcePrices.add(resourcePriceRepository.findByResourceAndServer(resource, employeeRepository.findByDiscordName(event.getMessageAuthor().getDiscriminatedName()).getServer())
                    .orElseThrow(IllegalArgumentException::new));
        }
        return resourcePrices;
    }

    @Override
    public List<ResourcePrice> resourcePricesByServer(MessageCreateEvent event, Long server) {
        List<Resource> resourceList = resourceRepository.findAll();
        List<ResourcePrice> resourcePrices = new ArrayList<>();
        for (Resource resource : resourceList) {
            resourcePrices.add(resourcePriceRepository
                    .findByResourceAndServer(resource, serverFractionRepository.findById(server)
                            .orElseThrow(IllegalArgumentException::new)).orElseThrow(IllegalArgumentException::new));
        }
        return resourcePrices;
    }

}
