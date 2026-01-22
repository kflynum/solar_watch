package org.example.solar_watch.service;

import org.example.solar_watch.model.CityGeoResponse;
import org.example.solar_watch.model.SunriseSunsetResponse;
import org.example.solar_watch.model.SunriseSunsetResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class OpenWeatherServiceTest {

    private OpenWeatherService service;
    private WebClient webClient;
    private WebClient.RequestHeadersUriSpec<?> uriSpec;
    private WebClient.RequestHeadersSpec<?> headersSpec;
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        webClient = Mockito.mock(WebClient.class);
        uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        service = new OpenWeatherService(webClient);

        // use doReturn(...) to avoid generics type-inference issues when stubbing fluent WebClient
        Mockito.doReturn(uriSpec).when(webClient).get();
        Mockito.doReturn(headersSpec).when(uriSpec).uri(anyString());
        Mockito.doReturn(responseSpec).when(headersSpec).retrieve();
    }

    @Test
    void getCordForCity_shouldReturnFirstItem_whenResponseNotEmpty() {
        CityGeoResponse r = new CityGeoResponse(10.5, -20.5, null, null, null);

        CityGeoResponse[] arr = new CityGeoResponse[]{r};

        when(responseSpec.bodyToMono(eq(CityGeoResponse[].class))).thenReturn(Mono.just(arr));

        CityGeoResponse result = service.getCordForCity("TestCity");

        assertNotNull(result);
        assertEquals(10.5, result.lat());
        assertEquals(-20.5, result.lon());
    }

    @Test
    void getCordForCity_shouldThrow_whenResponseEmpty() {
        when(responseSpec.bodyToMono(eq(CityGeoResponse[].class))).thenReturn(Mono.just(new CityGeoResponse[]{}));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.getCordForCity("NoCity"));
        assertTrue(ex.getMessage().contains("City not found"));
    }

    @Test
    void getSunRiseSet_shouldReturnResults_whenStatusOk() {
        // match expected assertions: sunrise 07:00 and sunset 17:00
        SunriseSunsetResults results = new SunriseSunsetResults("2020-01-01T07:00:00+00:00",
                "2020-01-01T17:00:00+00:00", null,0,null,null);

        SunriseSunsetResponse response = new SunriseSunsetResponse(results, "OK");

        when(responseSpec.bodyToMono(eq(SunriseSunsetResponse.class))).thenReturn(Mono.just(response));

        SunriseSunsetResults r = service.getSunRiseSet(1.0, 2.0, LocalDate.of(2020,1,1));

        assertNotNull(r);
        assertEquals("2020-01-01T07:00:00+00:00", r.sunrise());
        assertEquals("2020-01-01T17:00:00+00:00", r.sunset());
    }

    @Test
    void getSunRiseSet_shouldThrow_whenResponseInvalid() {
        // simulate empty (Mono.empty -> block() returns null)
        when(responseSpec.bodyToMono(eq(SunriseSunsetResponse.class))).thenReturn(Mono.empty());

        assertThrows(IllegalStateException.class, () -> service.getSunRiseSet(1.0,2.0, LocalDate.now()));

        SunriseSunsetResponse bad = new SunriseSunsetResponse(null, "ERROR");
        when(responseSpec.bodyToMono(eq(SunriseSunsetResponse.class))).thenReturn(Mono.just(bad));

        assertThrows(IllegalStateException.class, () -> service.getSunRiseSet(1.0,2.0, LocalDate.now()));
    }
}
