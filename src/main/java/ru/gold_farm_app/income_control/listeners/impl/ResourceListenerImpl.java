package ru.gold_farm_app.income_control.listeners.impl;

import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.ResourceListener;
import ru.gold_farm_app.income_control.model.Resource;
import ru.gold_farm_app.income_control.services.ResourceService;

import java.util.List;

@Component
public class ResourceListenerImpl implements ResourceListener {

    @Autowired
    private ResourceService resourceService;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equals("!resource_add")) {
            resourceService.addAll();
            List<Resource> resources = resourceService.getAllResources();
            for (Resource resource : resources) {
                event.getChannel().sendMessage(resource.getName() + resource.getScannedOn());
            }
        }
        if (event.getMessageContent().equals("!resource_update")) {
            resourceService.updateAll();
        }
    }
}
