package app.repository;

import app.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    List<PhoneNumber> findFirstByPhone(String phone);
}
