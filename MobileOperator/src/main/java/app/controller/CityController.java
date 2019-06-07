package app.controller;

import app.entity.City;
import app.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CityController {
    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/city/")
    public ResponseEntity<City> createCity(@RequestBody City city) {
        if (cityService.exists(city)) {
            return new ResponseEntity<City>(city, HttpStatus.CONFLICT);
        }
        cityService.save(city);
        return new ResponseEntity<City>(city, HttpStatus.CREATED);
    }
}