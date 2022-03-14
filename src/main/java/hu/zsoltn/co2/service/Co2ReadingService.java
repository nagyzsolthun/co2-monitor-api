package hu.zsoltn.co2.service;

import hu.zsoltn.co2.dto.Co2ReadingDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service to save and find CO2 readings per city and district
 */
@Service
public class Co2ReadingService {

  private List<Co2ReadingDto> dtos = new ArrayList<>();

  public List<Co2ReadingDto> findAll(
      final String cityId,
      final String districtId) {
    return dtos.stream()
        .filter(dto -> Objects.equals(dto.getCityId(), cityId))
        .filter(dto -> Objects.equals(dto.getDistrictId(), districtId))
        .collect(Collectors.toList());
  }

  public void add(final String cityId, final String districtId, final int concentration) {
    final Co2ReadingDto dto = new Co2ReadingDto(cityId, districtId, Instant.now(), concentration);
    dtos.add(dto);
  }

}
