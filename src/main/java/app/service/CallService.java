package app.service;

import app.entity.Call;
import app.entity.City;
import app.entity.PhoneNumber;
import app.repository.CallRepository;
import app.repository.CityRepository;
import app.repository.PhoneNumberRepository;
import org.springframework.stereotype.Service;

@Service
public class CallService {
    private CallRepository callRepository;
    private CityRepository cityRepository;
    private PhoneNumberRepository phoneNumberRepository;

    public CallService(CallRepository callRepository, CityRepository cityRepository,
                       PhoneNumberRepository phoneNumberRepository) {
        this.callRepository = callRepository;
        this.cityRepository = cityRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public boolean exists(Call call) {
        Long callId = call.getId();
        return callId != null && callRepository.existsById(callId);
    }

    public void save(Call call) {
        call.setCallerPhoneNumber(
                getPhoneNumberDB(call.getCallerPhoneNumber().getPhone()));
        call.setRecipientPhoneNumber(
                getPhoneNumberDB(call.getRecipientPhoneNumber().getPhone()));
        call.setCity(getCityDB(call.getCity().getName()));
        if (call.getCity().getId() == null) {
            cityRepository.save(call.getCity());
        }
        callRepository.save(call);
    }

    private PhoneNumber getPhoneNumberDB(String phone) {
        return phoneNumberRepository.findFirstByPhone(phone).stream()
                .findFirst().orElse(null);
    }

    private City getCityDB(String cityName) {
        return cityRepository.findFirstByName(cityName).stream()
                .findFirst().orElse(new City(cityName));
    }
}
