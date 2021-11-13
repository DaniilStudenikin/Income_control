package ru.gold_farm_app.income_control.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String discordName;

    private Long gold;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "discord_id")
    private String discordId;

    @ManyToOne
    private ServerFraction server;

}
