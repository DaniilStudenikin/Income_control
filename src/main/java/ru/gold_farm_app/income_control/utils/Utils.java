package ru.gold_farm_app.income_control.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.ResourceListener;
import ru.gold_farm_app.income_control.listeners.ServerFractionListener;

@Component
public class Utils {

    @Autowired
    private Environment env;

    @Autowired
    private ServerFractionListener serverFractionListener;

    @Autowired
    private ResourceListener resourceListener;

    @Bean
    @ConfigurationProperties(value = "discord-api")
    public DiscordApi discordApi() {
        String token = env.getProperty("TOKEN");
        return new DiscordApiBuilder().setToken(token)
                .addMessageCreateListener(serverFractionListener)
                .addMessageCreateListener(resourceListener)
                .login().join();
    }

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

}
