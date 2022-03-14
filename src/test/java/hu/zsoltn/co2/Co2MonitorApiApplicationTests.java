package hu.zsoltn.co2;

import hu.zsoltn.co2.dto.Co2ReadingDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = DEFINED_PORT)
class Co2MonitorApiApplicationTests {

	private static final String CITY_ID = "barcelona";
	private static final String DISTRICT_ID = "garcia";
	private static final int CONCENTRATION = 128;

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testPostGet() {
		final ResponseEntity<Void> putResponse = postReading(CITY_ID, DISTRICT_ID, CONCENTRATION);
		assertEquals(putResponse.getStatusCode(), CREATED);

		final ResponseEntity<List<Co2ReadingDto>> getResponse = getReadings(CITY_ID, DISTRICT_ID);
		assertEquals(getResponse.getStatusCode(), OK);
		assertEquals(1, getResponse.getBody().size());
		assertEquals(CITY_ID, getResponse.getBody().get(0).getCityId());
		assertEquals(DISTRICT_ID, getResponse.getBody().get(0).getDistrictId());
		assertEquals(CONCENTRATION, getResponse.getBody().get(0).getConcentration());
	}

	private ResponseEntity<Void> postReading(
			final String cityId,
			final String districtId,
			final int concentration) {
		final HttpEntity<Integer> entity = new HttpEntity<>(concentration);
		final String url = "http://localhost:" + port + "/co2readings/cities/" + cityId + "/districts/" + districtId;
		return restTemplate.exchange(url, POST, entity, Void.class);
	}

	private ResponseEntity<List<Co2ReadingDto>> getReadings(
			final String cityId,
			final String districtId) {
		final HttpHeaders headers = new HttpHeaders();
		final HttpEntity<Void> entity = new HttpEntity<>(headers);
		final String url = "http://localhost:" + port + "/co2readings/cities/" + cityId + "/districts/" + districtId;
		return restTemplate.exchange(url, GET, entity, new ParameterizedTypeReference<List<Co2ReadingDto>>() {});
	}

}
