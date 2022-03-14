package hu.zsoltn.co2.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

/**
 * Entity representing a CO2 reading
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Co2Reading {

  @Id
  private UUID id;

  private String cityId;
  private String districtId;
  private Instant createdAt;
  private int concentration;
}
