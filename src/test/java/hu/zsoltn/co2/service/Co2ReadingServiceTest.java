package hu.zsoltn.co2.service;

import hu.zsoltn.co2.dto.Co2ReadingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Co2ReadingServiceTest {

  private static final String CITY_ID = "barcelona";
  private static final String DISTRICT_ID = "garcia";
  private static final int CONCENTRATION = 128;

  private Co2ReadingService service;

  @BeforeEach
  public void init() {
    service = new Co2ReadingService();
  }

  @Test
  public void testAddFind() {
    service.add(CITY_ID, DISTRICT_ID, CONCENTRATION);
    final List<Co2ReadingDto> actual = service.findAll(CITY_ID, DISTRICT_ID);
    assertEquals(1, actual.size());
    assertEquals(CITY_ID, actual.get(0).getCityId());
    assertEquals(DISTRICT_ID, actual.get(0).getDistrictId());
    assertEquals(CONCENTRATION, actual.get(0).getConcentration());
  }

}
