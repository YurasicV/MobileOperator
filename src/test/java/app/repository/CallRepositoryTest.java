package app.repository;

import app.dto.CallsPerCity;
import app.dto.ClientAndDateRange;
import app.entity.Call;
import app.entity.City;
import app.entity.Client;
import app.entity.PhoneNumber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CallRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CallRepository callRepository;

    private Call firstCall;
    private Call secondCall;
    private Client ivanov;
    private Client petrov;
    PhoneNumber phoneNumberIvanov;
    PhoneNumber phoneNumberPetrov;
    private City dnipro;
    private City kyiv;

    @Before
    public void setUp() throws Exception {
        // given
        dnipro = new City("Dnipro");
        entityManager.persist(dnipro);

        kyiv = new City("Kyiv");
        entityManager.persist(kyiv);

        ivanov = new Client();
        ivanov.setFullName("Ivanov");

        petrov = new Client();
        petrov.setFullName("Petrov");

        phoneNumberIvanov = new PhoneNumber();
        phoneNumberIvanov.setClient(ivanov);
        phoneNumberIvanov.setPhone("0123456789");
        entityManager.persist(phoneNumberIvanov);

        phoneNumberPetrov = new PhoneNumber();
        phoneNumberPetrov.setClient(petrov);
        phoneNumberPetrov.setPhone("9876543210");
        entityManager.persist(phoneNumberPetrov);

        ivanov.setPhoneNumbers(new HashSet<PhoneNumber>(Collections.singletonList(phoneNumberIvanov)));
        entityManager.persist(ivanov);

        petrov.setPhoneNumbers(new HashSet<PhoneNumber>(Collections.singletonList(phoneNumberPetrov)));
        entityManager.persist(petrov);

        firstCall = new Call();
        firstCall.setCallerPhoneNumber(phoneNumberIvanov);
        firstCall.setRecipientPhoneNumber(phoneNumberPetrov);
        firstCall.setDuration(200.0);
        firstCall.setCallDt(Date.from(LocalDate.of(2019, 6, 7)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        firstCall.setCity(dnipro);
        entityManager.persist(firstCall);

        secondCall = new Call();
        secondCall.setCallerPhoneNumber(phoneNumberPetrov);
        secondCall.setRecipientPhoneNumber(phoneNumberIvanov);
        secondCall.setDuration(300.0);
        secondCall.setCallDt(Date.from(LocalDate.of(2019, 6, 8)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        secondCall.setCity(kyiv);
        entityManager.persist(secondCall);

        entityManager.flush();
    }

    @Test
    public void findCallsPerCitiesTest() {
        // when
        List<CallsPerCity> found = callRepository.findCallsPerCities();
        found.sort(Comparator.comparing(CallsPerCity::getCityName));

        // then
        assertEquals(2, found.size());
        assertEquals(dnipro.getName(), found.get(0).getCityName());
        assertEquals(kyiv.getName(), found.get(1).getCityName());
        assertEquals(1, (long)found.get(0).getCallsNumber());
        assertEquals(1, (long)found.get(1).getCallsNumber());
    }

    @Test
    public void findCallsByClientAndDataRangeTest() {
        // given
        ClientAndDateRange params = new ClientAndDateRange();
        params.setClientId(ivanov.getId());
        params.setDateFrom(Date.from(LocalDate.of(2019, 6, 5)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        params.setDateTo(Date.from(LocalDate.of(2019, 6, 10)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));

        // when
        List<Call> found = callRepository.findCallsByClientAndDataRange(params,
                PageRequest.of(0, Integer.MAX_VALUE));

        // then
        assertEquals(secondCall, found.get(0));
    }
}