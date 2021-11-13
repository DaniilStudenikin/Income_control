package ru.gold_farm_app.income_control.listeners.impl;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.EmployeeListener;
import ru.gold_farm_app.income_control.model.ServerFraction;
import ru.gold_farm_app.income_control.repository.ServerFractionRepository;
import ru.gold_farm_app.income_control.services.EmployeeService;

@Component
public class EmployeeListenerImpl implements EmployeeListener {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ServerFractionRepository serverFractionRepository;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith("!auth")) {
            try {
                ServerFraction server = serverFractionRepository
                        .findByServerFraction(event.getMessageContent().replace(" ", "").substring(5)).orElseThrow(IllegalArgumentException::new);

                Boolean createEmployee = employeeService.add(event.getMessageAuthor(), server);
                if (createEmployee) {

                    new MessageBuilder()
                            .append(event.getMessageAuthor().getDisplayName() + " вы успешно зарегистрировались!" + "!\n")
                            .append("Ваш сервер " + server.getServerFraction() + "!\n")
                            .append("Желаю вам успешного фарма!")
                            .send(event.getChannel());
                } else
                    new MessageBuilder().
                            append("Упс. Вы уже зарегистрированы! Больше этого делать не нужно))))").send(event.getChannel());

            } catch (IllegalArgumentException e) {
                event.getChannel().sendMessage("Ваше сообщение не правильно,проверьте его,может проблема в названии сервера.");
            }
        }
        if (event.getMessageContent().equals("!update")) {
            try {
                employeeService.update(event.getMessageAuthor());
                event.getChannel().sendMessage(event.getMessageAuthor().getDisplayName() + " обновление вашего аккаунта прошло успешно!");
            } catch (IllegalArgumentException e) {
                event.getChannel().sendMessage("Вы не зарегистрированы. Проследуйте инструкциям выше для регистрации");
            }
        }
    }
}
