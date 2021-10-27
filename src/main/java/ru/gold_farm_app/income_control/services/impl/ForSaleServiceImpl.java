package ru.gold_farm_app.income_control.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.Employee;
import ru.gold_farm_app.income_control.model.ForSale;
import ru.gold_farm_app.income_control.model.Resource;
import ru.gold_farm_app.income_control.model.ResourcePrice;
import ru.gold_farm_app.income_control.repository.EmployeeRepository;
import ru.gold_farm_app.income_control.repository.ForSaleRepository;
import ru.gold_farm_app.income_control.repository.ResourcePriceRepository;
import ru.gold_farm_app.income_control.repository.ResourceRepository;
import ru.gold_farm_app.income_control.services.ForSaleService;

import java.util.List;
import java.util.Optional;


@Service
public class ForSaleServiceImpl implements ForSaleService {

    @Autowired
    private ForSaleRepository forSaleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourcePriceRepository resourcePriceRepository;

    @Override
    public ForSale createForSale(List<String[]> forSaleList, String discordName) {
        Employee employee = employeeRepository.findByDiscordName(discordName);
        ForSale forSale = from(forSaleList, employee);
        forSaleRepository.save(forSale);
        return forSale;
    }


    private ForSale from(List<String[]> forSaleResourceList, Employee employee) {
        ForSale forSale = new ForSale();
        forSale.setEmployee(employee);
        forSale.setPrice(price(forSaleResourceList, employee));
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

    private Long price(List<String[]> list, Employee employee) {
        long price = 0L;
        ResourcePrice resourcePrice = resourcePriceRepository.findByResourceAndServer(getResourceByName(list.get(0)[0]), employee.getServerFraction()).orElseThrow(IllegalArgumentException::new);

        System.out.println(resourcePrice.getPrice());
        for (String[] s : list) {
            price += resourcePriceRepository.findByResourceAndServer(getResourceByName(s[0]), employee.getServerFraction()).orElseThrow(IllegalArgumentException::new).getPrice() * anInt(s);
        }
        return price;
    }

    private Resource getResourceByName(String name) {
        return resourceRepository.findByName(name);
    }

    private int anInt(String[] s) {
        return Integer.parseInt(s[1]);
    }
}
