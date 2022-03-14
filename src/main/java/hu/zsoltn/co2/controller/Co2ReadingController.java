package hu.zsoltn.co2.controller;

import hu.zsoltn.co2.dto.Co2ReadingDto;
import hu.zsoltn.co2.service.Co2ReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("co2readings")
public class Co2ReadingController {

  private final Co2ReadingService service;

  @GetMapping("cities/{cityId}/districts/{districtId}")
  public List<Co2ReadingDto> getReadings(
      final @PathVariable("cityId") String cityId,
      final @PathVariable("districtId") String districtId) {
    return service.findAll(cityId, districtId);
  }

  @PostMapping("cities/{cityId}/districts/{districtId}")
  @ResponseStatus(HttpStatus.CREATED)
  public void postReading(
      final @PathVariable("cityId") String cityId,
      final @PathVariable("districtId") String districtId,
      final @RequestBody int concentration) {
    service.add(cityId, districtId, concentration);
  }

}
