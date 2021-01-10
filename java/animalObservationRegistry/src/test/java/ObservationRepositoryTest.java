import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ObservationRepositoryTest {
    private ObservationFactoryTest oft;

    @BeforeAll
    void before() throws Exception {
        oft = new ObservationFactoryTest();
    }

    @Test
    void getObservationFromMapByIdTest() throws Exception {
        assertNotEquals(oft.observation1Seven, oft.observationRepository.getObservation(2));
        assertEquals(oft.observation1Seven, oft.observationRepository.getObservation(1));
    }

    @Test
    void getAllObservationsAsArraylistTest() throws Exception {
        assertEquals(3, oft.observationRepository.getObservations().size());
    }

    @Test
    void getAnimalFromObservationTest() throws Exception {
        assertNotEquals(oft.reptile, oft.observationRepository.getObservation(1).getAnimal());
        assertEquals(oft.reptile, oft.observationRepository.getObservation(2).getAnimal());
    }

    @Test
    void updateObservationTest() throws Exception {
        String newName = "New";
        oft.observationRepository.updateObservation(1, newName, oft.amphibian, oft.location1, "04-06-2337 08:56", 3, oft.observation1Seven.getPicturePath(), oft.observation1Seven.getComment());
        assertEquals(newName, oft.observationRepository.getObservation(1).getName());
    }

    @AfterAll
    void deleteObservationTest() throws Exception {
        assertTrue(oft.observationRepository.getObservations().contains(oft.observation1Seven));
        oft.observationRepository.deleteObservation(1);
        assertFalse(ObservationFactoryTest.observationRepository.getObservations().contains(oft.observation1Seven));
    }
}
