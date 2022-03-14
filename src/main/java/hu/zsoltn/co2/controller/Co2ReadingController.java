package hu.zsoltn.co2.controller;

import hu.zsoltn.co2.dto.Co2ReadingDto;
import hu.zsoltn.co2.service.Co2ReadingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("co2readings")
public class Co2ReadingController {

  private final Co2ReadingService service;

  @Operation(summary = "provides list CO2 readings", parameters = {
      @Parameter(name = "cityId", description = "id of the city", example = "barcelona"),
      @Parameter(name = "districtId", description = "id of the district", example = "garcia")
  })
  @GetMapping(value = "cities/{cityId}/districts/{districtId}", produces = {"application/json"})
  public List<Co2ReadingDto> getReadings(
      final @PathVariable("cityId") String cityId,
      final @PathVariable("districtId") String districtId) {
    return service.findAll(cityId, districtId);
  }

  @Operation(summary = "saves a CO2 reading", parameters = {
      @Parameter(name = "cityId", description = "id of the city", example = "barcelona"),
      @Parameter(name = "districtId", description = "id of the district", example = "garcia"),
  }, requestBody =
  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "integer value between 0-1024"))
  @PostMapping(value = "cities/{cityId}/districts/{districtId}", produces = {})
  @ResponseStatus(HttpStatus.CREATED)
  public void postReading(
      final @PathVariable("cityId") String cityId,
      final @PathVariable("districtId") String districtId,
      final @RequestBody int concentration) {
    service.add(cityId, districtId, concentration);
  }

}
