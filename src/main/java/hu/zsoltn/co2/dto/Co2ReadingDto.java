package hu.zsoltn.co2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * DTO representing a CO2 reading
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Co2ReadingDto {
  private String cityId;
  private String districtId;
  private Instant createdAt;
  private int concentration;
}
