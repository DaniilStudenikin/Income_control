package ru.gold_farm_app.income_control.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.net.URL;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String uniqueName;

    private Long itemId;

    private URL icon;

}
