package app.repository;

import app.entity.City;
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
public class CityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    private City dnipro;

    @Before
    public void setUp() throws Exception {
        // given
        dnipro = new City("Dnipro");
        entityManager.persist(dnipro);
        entityManager.flush();
    }

    @Test
    public void whenFindFirstByName_thenReturnCity() {
        // when
        List<City> found = cityRepository.findFirstByName(dnipro.getName());

        // then
        assertEquals(1, found.size());
        assertEquals(dnipro.getName(), found.get(0).getName());
    }

    @Test
    public void whenFindFirstByName_thenReturnEmpty() {
        City poltava = new City("Poltava");

        // when
        List<City> found = cityRepository.findFirstByName(poltava.getName());

        // then
        assertEquals(0, found.size());
    }
}