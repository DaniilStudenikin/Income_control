package ru.gold_farm_app.income_control.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @JsonProperty("user_id")
    private String user_id;

    //password
    @JsonProperty("user_secret")
    private String user_secret;

    //key to get access token
    @JsonProperty("user_key")
    private String user_key;

    //token to access API
    @JsonProperty("access_token")
    private String access_token;

    //token to refresh access token
    @JsonProperty("refresh_token")
    private String refresh_token;

    private LocalDateTime createdOn;
}
