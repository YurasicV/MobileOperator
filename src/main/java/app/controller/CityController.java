package app.controller;

import app.entity.City;
import app.service.CityService;
import app.validation.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CityController {
    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/city/")
    public ResponseEntity<City> createCity(@Valid  @RequestBody City city) throws CustomException {
        if (cityService.exists(city)) {
            throw new CustomException(String.format("City %s already exists",
                    city.getName()), HttpStatus.CONFLICT);
        }
        cityService.save(city);
        return new ResponseEntity<City>(city, HttpStatus.CREATED);
    }
}