package com.resliv.bot.TelegramBot.service;

import com.resliv.bot.TelegramBot.model.City;
import com.resliv.bot.TelegramBot.repo.CityRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CityServiceImp implements CityService {

    private final CityRepo cityRepo;

    public CityServiceImp(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    @Override
    public Optional<City> getById(Long id) {
        return cityRepo.findById(id);
    }

    @Override
    public void save(City city) {
        cityRepo.save(city);
    }

    @Override
    public void delete(Long id) {
        cityRepo.deleteById(id);
    }

    @Override
    public List<City> getAll() {
        return cityRepo.findAll();
    }

    @Override
    public boolean existsByNameOfCity(String name) {
        return cityRepo.existsByNameOfCity(name);
    }

    @Override
    public String getDescriptionByCityName(String name) {
        return cityRepo.findByNameOfCity(name).getDescription();
    }
}
