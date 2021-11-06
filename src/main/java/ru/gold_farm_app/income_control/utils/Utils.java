package ru.gold_farm_app.income_control.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import org.javacord.api.interaction.SlashCommandInteraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.*;


@Component
public class Utils {

    @Autowired
    private Environment env;

    @Autowired
    private ServerFractionListener serverFractionListener;

    @Autowired
    private ResourceListener resourceListener;

    @Autowired
    private EmployeeListener employeeListener;

    @Autowired
    private ForSaleListener forSaleListener;

    @Autowired
    private ResourcePriceListener resourcePriceListener;

    @Autowired
    private DiscordApi api;

    @Bean
    @ConfigurationProperties(value = "discord-api")
    public DiscordApi discordApi() {

        String token = "ODkxNTU3MTg0NjI0ODIwMjI2.YVAFVQ.K_2LaoemQ4KjBsJOyOQ2L28N8IQ";
        return new DiscordApiBuilder().setToken(token)
                .addMessageCreateListener(serverFractionListener)
                .addMessageCreateListener(resourceListener)
                .addMessageCreateListener(employeeListener)
                .addMessageCreateListener(forSaleListener)
                .addMessageCreateListener(resourcePriceListener)
                .login().join();
    }

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    public void listenersAdding() {
        api.addMessageCreateListener(serverFractionListener);
        api.addMessageCreateListener(resourceListener);
        api.addMessageCreateListener(employeeListener);
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            System.out.println(slashCommandInteraction.getFirstOption().orElse(null));
        });
    }
}
