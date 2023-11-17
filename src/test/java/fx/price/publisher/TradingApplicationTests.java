package fx.price.publisher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class TradingApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();
	@Test
	 void testRetrieveData() {
		// given
		var headers = new HttpHeaders();
		var payload = """
				107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002
				108, GBP/USD, 21.2500,23.2560,01-06-2020 13:01:02:002
				109, GBP/CHF, 1.2499,1.2561,01-06-2020 12:01:02:100
				""";
		var getEntity = new HttpEntity<>(null, headers);
		var postEntity = new HttpEntity<String>(payload, headers);

		// when
		restTemplate.exchange(
				createURLWithPort("/test/publisher/prices"),
				HttpMethod.POST, postEntity, String.class);

		var getResponse = restTemplate.exchange(
				createURLWithPort("/fxprices/GBPUSD"),
				HttpMethod.GET, getEntity, String.class);
		var getResponse2 = restTemplate.exchange(
				createURLWithPort("/fxprices/EURUSD"),
				HttpMethod.GET, getEntity, String.class);

		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(getResponse.getBody()).contains("21.2288"); // bid price with commission for GBP/USD
		assertThat(getResponse2.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	@Test
	void contextLoads() {
	}

}
