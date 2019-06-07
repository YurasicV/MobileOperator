package app.service;

import app.entity.PhoneNumber;
import app.repository.PhoneNumberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneNumberService {
    private PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public boolean exists(PhoneNumber phoneNumber) {
        return (phoneNumber != null &&
                phoneNumberRepository.findFirstByPhone(phoneNumber.getPhone()).size() > 0);
    }

    public void save(PhoneNumber phoneNumber) {
        phoneNumberRepository.save(phoneNumber);
    }

    public List<PhoneNumber> findFirstByPhone(String phone) {
        return phoneNumberRepository.findFirstByPhone(phone);
    }
}
