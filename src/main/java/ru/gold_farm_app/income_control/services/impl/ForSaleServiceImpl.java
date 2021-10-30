package ru.gold_farm_app.income_control.services.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.gold_farm_app.income_control.model.*;
import ru.gold_farm_app.income_control.repository.EmployeeRepository;
import ru.gold_farm_app.income_control.repository.ForSaleRepository;
import ru.gold_farm_app.income_control.repository.ResourcePriceRepository;
import ru.gold_farm_app.income_control.repository.ResourceRepository;
import ru.gold_farm_app.income_control.services.EmployeeService;
import ru.gold_farm_app.income_control.services.ForSaleService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ForSaleServiceImpl implements ForSaleService {
    private static final Logger logger = LoggerFactory.getLogger(ForSaleServiceImpl.class);
    @Autowired
    private ForSaleRepository forSaleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourcePriceRepository resourcePriceRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ForSale createForSale(List<String[]> forSaleList, String discordName) {
        Employee employee = employeeRepository.findByDiscordName(discordName);
        ForSale forSale = from(forSaleList);
        Long price = price(forSaleList, employee);
        //adding gold into employee's acc
        forSale.setPrice(price);
        forSale.setEmployee(employee);

        employeeService.addIncome(discordName, forSale.getPrice());
        forSaleRepository.save(forSale);
        logger.info(employee + "///" + forSale);
        return forSale;
    }

    //creating object ForSale from resourceList which is String array
    private ForSale from(List<String[]> forSaleResourceList) {
        ForSale forSale = new ForSale();
        forSale.setDate(LocalDate.now());
        for (int i = 0; i < 27; i++) {
            String[] s = forSaleResourceList.get(i);
            int amount = anInt(s);
            switch (i) {
                case 0:
                    forSale.setFelLotus(amount);
                case 1:
                    forSale.setDreamingGlory(amount);
                case 2:
                    forSale.setNightmareVine(amount);
                case 3:
                    forSale.setManaThistle(amount);
                case 4:
                    forSale.setNightmareSeed(amount);
                case 5:
                    forSale.setNetherbloom(amount);
                case 6:
                    forSale.setFelIronOre(amount);
                case 7:
                    forSale.setEterniumOre(amount);
                case 8:
                    forSale.setAdamantiteOre(amount);
                case 9:
                    forSale.setKhoriumOre(amount);
                case 10:
                    forSale.setBloodGarnet(amount);
                case 11:
                    forSale.setFlameSpessarite(amount);
                case 12:
                    forSale.setGoldenDraenite(amount);
                case 13:
                    forSale.setShadowDraenite(amount);
                case 14:
                    forSale.setDeepPeridot(amount);
                case 15:
                    forSale.setAzureMoonstone(amount);
                case 16:
                    forSale.setDawnstone(amount);
                case 17:
                    forSale.setNightseye(amount);
                case 18:
                    forSale.setStarOfElune(amount);
                case 19:
                    forSale.setTalasite(amount);
                case 20:
                    forSale.setLivingRuby(amount);
                case 21:
                    forSale.setNobleTopaz(amount);
                case 22:
                    forSale.setPrimalMana(amount);
                case 23:
                    forSale.setPrimalFire(amount);
                case 24:
                    forSale.setPrimalLife(amount);
                case 25:
                    forSale.setPrimalEarth(amount);
                case 26:
                    forSale.setPrimalAir(amount);
            }
        }
        return forSale;
    }

    //counting price is here
    private Long price(List<String[]> list, Employee employee) {
        long price = 0L;
        //[ResourceName(DreamingGlory), ]
        for (String[] s : list) {
            price += resourcePriceRepository.findByResourceAndServer(getResourceByName(s[0]), employee.getServerFraction()).orElseThrow(IllegalArgumentException::new).getPrice() * anInt(s);
        }
        return price;
    }

    //simplified method to get Resource because resourceRepository.findByName(name) is long//getResourceByName is Short
    private Resource getResourceByName(String name) {
        return resourceRepository.findByName(name);
    }

    //simplified method to get Integer from array and parse it from String
    private int anInt(String[] s) {
        return Integer.parseInt(s[1]);
    }
}
