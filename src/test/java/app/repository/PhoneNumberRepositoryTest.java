package app.repository;

import app.entity.PhoneNumber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class PhoneNumberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    private PhoneNumber phoneNumber;

    @Before
    public void setUp() throws Exception {
        // given
        phoneNumber = new PhoneNumber();
        phoneNumber.setPhone("0123456789");
        entityManager.persist(phoneNumber);
        entityManager.flush();
    }

    @Test
    public void whenFindFirstByPhone_thenReturnPhoneNumber() {
        // when
        List<PhoneNumber> found = phoneNumberRepository.findFirstByPhone(phoneNumber.getPhone());

        // then
        assertEquals(1, found.size());
        assertEquals(phoneNumber.getPhone(), found.get(0).getPhone());
    }

    @Test
    public void whenFindFirstByPhone_thenReturnEmpty() {
        PhoneNumber anotherPhoneNumber = new PhoneNumber();
        anotherPhoneNumber.setPhone("9876543210");

        // when
        List<PhoneNumber> found = phoneNumberRepository.findFirstByPhone(anotherPhoneNumber.getPhone());

        // then
        assertEquals(0, found.size());
    }
}