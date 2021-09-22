package ru.gold_farm_app.income_control.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class NexushubUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //login
    private String user_id;

    //password
    private String user_secret;

    //key to get access token
    private String user_key;

    //token to access API
    @Column(length = 500)
    private String access_token;

    //token to refresh access token
    @Column(length = 500)
    private String refresh_token;

    private LocalDateTime createdOn;
}
