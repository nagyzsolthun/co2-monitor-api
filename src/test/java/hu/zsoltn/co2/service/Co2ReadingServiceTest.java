package hu.zsoltn.co2.service;

import hu.zsoltn.co2.db.Co2Reading;
import hu.zsoltn.co2.db.Co2ReadingRepository;
import hu.zsoltn.co2.dto.Co2ReadingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Co2ReadingServiceTest {

  private static final String CITY_ID = "barcelona";
  private static final String DISTRICT_ID = "garcia";
  private static final int CONCENTRATION = 128;

  @Mock
  private Co2ReadingRepository repository;

  private Co2ReadingService service;

  @BeforeEach
  public void init() {
    service = new Co2ReadingService(repository);
  }

  @Test
  public void testAdd() {
    service.add(CITY_ID, DISTRICT_ID, CONCENTRATION);
    service.add(CITY_ID, DISTRICT_ID, CONCENTRATION);
    verify(repository, times(2)).save(any(Co2Reading.class));
  }

  @Test
  public void testFindReadings() {
    final Co2Reading entity = new Co2Reading(UUID.randomUUID(), CITY_ID, DISTRICT_ID, Instant.EPOCH, CONCENTRATION);
    when(repository.findAllByCityIdAndDistrictId(CITY_ID, DISTRICT_ID)).thenReturn(List.of(entity));
    final List<Co2ReadingDto> result = service.findAll(CITY_ID, DISTRICT_ID);
    assertEquals(1, result.size());
    assertEquals(CITY_ID, result.get(0).getCityId());
    assertEquals(DISTRICT_ID, result.get(0).getDistrictId());
    assertEquals(CONCENTRATION, result.get(0).getConcentration());
  }

}
