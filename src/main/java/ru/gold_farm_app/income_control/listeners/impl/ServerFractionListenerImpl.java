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
        if (messageCreateEvent.getMessageContent().startsWith("!server_add")) {
            String server = messageCreateEvent.getMessageContent().replace(" ", "").substring(11);
            service.addServer(server);
            messageCreateEvent.getChannel().sendMessage("Server " + server + " added!");
        }
        if (messageCreateEvent.getMessageContent().startsWith("!server_delete")) {
            String server = messageCreateEvent.getMessageContent().replace(" ", "").substring(14);
            service.deleteServer(server);
            messageCreateEvent.getChannel().sendMessage("Server " + server + " deleted!");
        }
    }
}