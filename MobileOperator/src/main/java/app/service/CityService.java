package app.service;

import app.entity.City;
import app.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public boolean exists(City city) {
        Long cityId = city.getId();
        String cityName = city.getName();
        return (cityId != null && cityRepository.existsById(cityId)) ||
                (cityName != null && cityRepository.findFirstByName(cityName).size() > 0);
    }

    public void save(City city) {
        cityRepository.save(city);
    }
}
