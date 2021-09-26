package ru.gold_farm_app.income_control.listeners.impl;

import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.ServerFractionListener;

import ru.gold_farm_app.income_control.services.ServerFractionService;

@Component
public class ServerFractionListenerImpl implements ServerFractionListener {

    @Autowired
    private ServerFractionService service;

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if (messageCreateEvent.getMessageContent().startsWith("!server-fraction_add")) {
            service.addServerFraction(messageCreateEvent.getMessageContent().substring(21));
            messageCreateEvent.getChannel().sendMessage("Server " + messageCreateEvent.getMessageContent().substring(21) + " added!");
        }
    }
}