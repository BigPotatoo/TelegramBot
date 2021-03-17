package com.resliv.bot.TelegramBot.controller;

import com.resliv.bot.TelegramBot.model.City;
import com.resliv.bot.TelegramBot.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/bot-admin/{bot-token}")
public class MainController {

    private final CityService cityService;
    private final TelegramLongPollingBot telegramBotApiListener;

    public MainController(CityService cityService, TelegramLongPollingBot telegramBotApiListener) {
        this.cityService = cityService;
        this.telegramBotApiListener = telegramBotApiListener;
    }

    private boolean checkToken(String token) {
        return telegramBotApiListener.getBotToken().equals(token);
    }

    @GetMapping(path = "/cities/{id}")
    public ResponseEntity<Optional<City>> getCity(
            @PathVariable("bot-token") String token,
            @PathVariable("id") Long cityId) {
        if (checkToken(token)) {
            if (cityId == null)
                return ResponseEntity
                        .notFound()
                        .header("Result", "city not found")
                        .build();
            Optional<City> city = this.cityService.getById(cityId);
            if (city.isEmpty())
                return ResponseEntity
                        .notFound()
                        .header("Result", "city not found")
                        .build();
            return ResponseEntity
                    .ok()
                    .body(city);
        }
        return ResponseEntity
                .badRequest()
                .header("Result", "wrong token")
                .build();
    }

    @PostMapping(path = "/create")
    public ResponseEntity<City> saveCity(
            @PathVariable("bot-token") String token,
            @RequestBody @Valid City city) {
        if (checkToken(token)) {
            if (city == null)
                return ResponseEntity
                        .badRequest()
                        .header("Result", "city is null")
                        .build();
            if (cityService.existsByNameOfCity(city.getNameOfCity()))
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .header("Result", "city already exist")
                        .build();

            this.cityService.save(city);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build();
        }
        return ResponseEntity
                .badRequest()
                .header("Result", "wrong token")
                .build();
    }

    @PutMapping(path = "/update")
    public ResponseEntity<City> updateCity(
            @PathVariable("bot-token") String token,
            @RequestBody @Valid City city) {
        if (checkToken(token)) {
            if (city == null)
                return ResponseEntity
                        .badRequest()
                        .header("Result", "city is null")
                        .build();
            if (cityService.getById(city.getId()).isEmpty())
                return ResponseEntity
                        .notFound()
                        .header("Result", "city not found")
                        .build();
            if (cityService.existsByNameOfCity(city.getNameOfCity()))
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .header("Result", "city already exist")
                        .build();
            this.cityService.save(city);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        }
        return ResponseEntity
                .badRequest()
                .header("Result", "wrong token")
                .build();
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<City> deleteCity(
            @PathVariable("id") Long id,
            @PathVariable("bot-token") String token) {
        Optional<City> city = this.cityService.getById(id);
        if (checkToken(token)) {
            if (city.isEmpty())
                return ResponseEntity
                        .notFound()
                        .header("Result", "city not found")
                        .build();
            this.cityService.delete(id);
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity
                .badRequest()
                .header("Result", "wrong token")
                .build();
    }

    @GetMapping(path = "/cities")
    public ResponseEntity<List<City>> getAllCities(
            @PathVariable("bot-token") String token) {
        List<City> cities = this.cityService.getAll();
        if (checkToken(token)) {
            if (cities.isEmpty())
                return ResponseEntity
                        .notFound()
                        .header("Result", "cities not found")
                        .build();
        }
        return ResponseEntity
                .ok()
                .body(cities);
    }
}
