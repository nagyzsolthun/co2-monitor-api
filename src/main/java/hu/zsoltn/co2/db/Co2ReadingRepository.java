package hu.zsoltn.co2.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface Co2ReadingRepository extends JpaRepository<Co2Reading, UUID> {
  List<Co2Reading> findAllByCityIdAndDistrictId(final String cityId, final String districtId);
}
