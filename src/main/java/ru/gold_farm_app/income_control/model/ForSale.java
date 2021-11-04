package ru.gold_farm_app.income_control.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ForSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Herbs
    private Integer dreamingGlory;

    private Integer nightmareVine;

    private Integer manaThistle;

    private Integer nightmareSeed;

    private Integer felLotus;

    private Integer netherbloom;

    private Integer terocone;

    //Ores
    private Integer felIronOre;

    private Integer eterniumOre;

    private Integer adamantiteOre;

    private Integer khoriumOre;

    //Gems
    private Integer bloodGarnet;

    private Integer flameSpessarite;

    private Integer goldenDraenite;

    private Integer shadowDraenite;

    private Integer deepPeridot;

    private Integer azureMoonstone;

    private Integer dawnstone;

    private Integer nightseye;

    private Integer starOfElune;

    private Integer talasite;

    private Integer livingRuby;

    private Integer nobleTopaz;


    //Primal

    private Integer primalEarth;

    private Integer primalFire;

    private Integer primalMana;

    private Integer primalAir;

    private Integer primalLife;


    //other
    private Long price;

    private LocalDate date;

    @ManyToOne
    private Employee employee;
}
