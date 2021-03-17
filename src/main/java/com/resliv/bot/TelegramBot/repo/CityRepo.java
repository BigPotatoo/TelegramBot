package com.resliv.bot.TelegramBot.repo;

import com.resliv.bot.TelegramBot.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends JpaRepository<City,Long> {
    boolean existsByNameOfCity(String name);
    City findByNameOfCity(String name);
}