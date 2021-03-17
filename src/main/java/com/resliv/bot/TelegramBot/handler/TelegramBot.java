package com.resliv.bot.TelegramBot.handler;

import com.resliv.bot.TelegramBot.service.CityService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.stream.Collectors;

public class TelegramBot extends TelegramLongPollingBot {
    private final CityService cityService;

    public TelegramBot(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public String getBotUsername() {
        return "TouristBotForResliv";
    }

    @Override
    public String getBotToken() {
        return "1615058108:AAFs3rqmO65Stq79cp6SytoiMgsYARu7_Z4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (isMessageWithText(update)) {
            final String message = update.getMessage().getText();
            final String chatId = update.getMessage().getChatId().toString();
            if (cityService.existsByNameOfCity(message)) {
                final String description = cityService.getDescriptionByCityName(message);
                sendMessageWithExceptionCheck(chatId, description);
            } else if(message.equals(Command.START.name)){
                sendMessageWithExceptionCheck(chatId, "This is touristic bot." +
                            "\nInput the name of the city to get information about it");
            }
            else if (message.equals(Command.ALL.name)) {
                sendMessageWithExceptionCheck(chatId, getAllCities());
            } else {
                sendMessageWithExceptionCheck(chatId, "City not found");
            }
        }
    }

    private String getAllCities() {
        return cityService.getAll()
                .stream()
                .map(item -> item.getNameOfCity() + "\n")
                .collect(Collectors.joining());
    }

    private synchronized void sendMessageWithExceptionCheck(String chatId, String str) {
        SendMessage response = new SendMessage(chatId, str);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private boolean isMessageWithText(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}