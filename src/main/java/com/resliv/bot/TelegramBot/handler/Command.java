package com.resliv.bot.TelegramBot.handler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum Command {
    START("/start"),
    ALL("/all");

    String name;
}
