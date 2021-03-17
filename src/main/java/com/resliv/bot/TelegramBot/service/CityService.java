package com.resliv.bot.TelegramBot.service;

import com.resliv.bot.TelegramBot.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    Optional<City> getById(Long id);
    void save(City city);
    void delete(Long id);
    List<City> getAll();
    boolean existsByNameOfCity(String name);
    String getDescriptionByCityName(String name);
}
