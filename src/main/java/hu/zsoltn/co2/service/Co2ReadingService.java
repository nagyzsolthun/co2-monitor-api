package hu.zsoltn.co2.service;

import hu.zsoltn.co2.db.Co2Reading;
import hu.zsoltn.co2.db.Co2ReadingRepository;
import hu.zsoltn.co2.dto.Co2ReadingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service to save and find CO2 readings per city and district
 */
@Service
@RequiredArgsConstructor
public class Co2ReadingService {

  private final Co2ReadingRepository repository;

  public List<Co2ReadingDto> findAll(
      final String cityId,
      final String districtId) {
    return repository.findAllByCityIdAndDistrictId(cityId, districtId).stream()
        .map(Co2ReadingService::toDto)
        .collect(Collectors.toList());
  }

  public void add(final String cityId, final String districtId, final int concentration) {
    final Co2Reading entity = new Co2Reading(UUID.randomUUID(), cityId, districtId, Instant.now(), concentration);
    repository.save(entity);
  }

  private static Co2ReadingDto toDto(final Co2Reading entity) {
    return new Co2ReadingDto(
        entity.getCityId(),
        entity.getDistrictId(),
        entity.getCreatedAt(),
        entity.getConcentration());
  }

}
