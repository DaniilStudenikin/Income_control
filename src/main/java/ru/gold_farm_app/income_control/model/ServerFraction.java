package ru.gold_farm_app.income_control.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "server")
public class ServerFraction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String serverFraction;

}
