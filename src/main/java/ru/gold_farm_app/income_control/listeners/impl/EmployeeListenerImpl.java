package ru.gold_farm_app.income_control.listeners.impl;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.EmployeeListener;
import ru.gold_farm_app.income_control.model.Employee;
import ru.gold_farm_app.income_control.services.EmployeeService;

@Component
public class EmployeeListenerImpl implements EmployeeListener {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith("!auth")) {
            String server = event.getMessageContent().replace(" ", "").substring(4);
            employeeService.add(Employee.builder()
                    .discordName(event.getMessageAuthor().getDiscriminatedName())
                    .serverFraction(server)
                    .gold(0L)
                    .build());
            new MessageBuilder()
                    .append("You are with us! Dear " + event.getMessageAuthor().getDisplayName() + "!\n")
                    .send(event.getChannel());
        }
    }
}
