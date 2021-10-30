package ru.gold_farm_app.income_control.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.Resource;
import ru.gold_farm_app.income_control.model.ResourcePrice;
import ru.gold_farm_app.income_control.model.ServerFraction;
import ru.gold_farm_app.income_control.repository.ResourcePriceRepository;
import ru.gold_farm_app.income_control.repository.ResourceRepository;
import ru.gold_farm_app.income_control.repository.ServerFractionRepository;
import ru.gold_farm_app.income_control.services.ResourcePriceService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResourcePriceServiceImpl implements ResourcePriceService {

    @Autowired
    private ResourceRepository resourceRepository;

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
                            .price(price)
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

        @Scheduled(initialDelay = 1000*60*60*2,fixedDelay = 1000*60*60*2)
    @Override
    public void updatePrice() {
        System.out.println("START UPDATING");
        List<ResourcePrice> resourcePrices = resourcePriceRepository.findAll();
        for (ResourcePrice resP : resourcePrices) {
            try {
                var url = new URL("https://api.nexushub.co/wow-classic/v1/items/" + resP.getServer().getServerFraction() + "/" + resP.getResource().getItemId());
                var json = objectMapper.readTree(url);
                System.out.println(json.toString());
                resP.setPrice(json.get("stats").get("current").get("minBuyout").asLong());
                resP.setScannedOn(LocalDateTime.now());
                resourcePriceRepository.save(resP);
                Thread.sleep(300);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
