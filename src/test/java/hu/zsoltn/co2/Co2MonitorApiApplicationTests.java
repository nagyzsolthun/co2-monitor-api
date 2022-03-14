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
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = DEFINED_PORT)
class Co2MonitorApiApplicationTests {

	private static final String CITY_ID = "barcelona";
	private static final String DISTRICT_ID = "garcia";
	private static final int CONCENTRATION = 128;

	private static final String CITY_TOKEN = "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaXR5SWQiOiJiYXJjZWxvbmEifQ.QjHdgpqQeM0wVG0pHjEqkLH4jiQoSJpnUhqDk5vt5xx3PASJ9mJI8Qt69hVwlKpAG7kMeZ_4yddtfXSDCr-_iA";
	private static final String SENSOR_TOKEN = "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaXR5SWQiOiJiYXJjZWxvbmEiLCJkaXN0cmljdElkIjoiZ2FyY2lhIn0.SRuaoG0T2FdIIiZIfg88GVWGYCJV89YtLBK24XfI330YjX6XeL-eWCtd33uaDnQJfp0_skOgMQFS7MdZmHbCEA";

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

	@Test
	public void testPostForbidden() {
		final ResponseEntity<Void> putResponse = postReading(CITY_ID, "different-district", CONCENTRATION);
		assertEquals(putResponse.getStatusCode(), FORBIDDEN);
	}

	@Test
	public void testGetForbidden() {
		final ResponseEntity<List<Co2ReadingDto>> getResponse = getReadings("different-city", DISTRICT_ID);
		assertEquals(getResponse.getStatusCode(), FORBIDDEN);
	}

	private ResponseEntity<Void> postReading(
			final String cityId,
			final String districtId,
			final int concentration) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(SENSOR_TOKEN);
		final HttpEntity<Integer> entity = new HttpEntity<>(concentration, headers);
		final String url = "http://localhost:" + port + "/co2readings/cities/" + cityId + "/districts/" + districtId;
		return restTemplate.exchange(url, POST, entity, Void.class);
	}

	private ResponseEntity<List<Co2ReadingDto>> getReadings(
			final String cityId,
			final String districtId) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(CITY_TOKEN);
		final HttpEntity<Void> entity = new HttpEntity<>(headers);
		final String url = "http://localhost:" + port + "/co2readings/cities/" + cityId + "/districts/" + districtId;
		return restTemplate.exchange(url, GET, entity, new ParameterizedTypeReference<List<Co2ReadingDto>>() {});
	}

}
