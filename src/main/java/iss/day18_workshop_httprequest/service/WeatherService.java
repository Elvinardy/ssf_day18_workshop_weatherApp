package iss.day18_workshop_httprequest.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import iss.day18_workshop_httprequest.Constants;
import iss.day18_workshop_httprequest.model.WeatherModel;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    
    private final String appId;

    public WeatherService() {
        String k = System.getenv(Constants.ENV_OPENWEATHER_KEY);
        if ((null != k) && (k.trim().length() > 0)) {
            appId = k;
        } else {
            appId = "abc123";
        }    
    }
    
    public List<WeatherModel> getWeather(String city){
        
        String url = UriComponentsBuilder
            .fromUriString(Constants.URL_WEATHER)
            .queryParam("q", city)
            .queryParam("appid", appId)
            .toUriString();

        final RequestEntity<Void> req = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(req, String.class);

        if (resp.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException(
                "Error: status code %s".formatted(resp.getStatusCode().toString()));
        }
        final String body = resp.getBody();

        logger.info("payload: " + body);

        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
            
            final JsonReader reader = Json.createReader(is);
            final JsonObject result = reader.readObject();
            final JsonArray readings = result.getJsonArray("weather");
            final String cityName = result.getString("name");
            final float temperature = (float)result.getJsonObject("main").getJsonNumber("temp").doubleValue();
            return readings.stream()
                    .map(v -> (JsonObject)v)
                    .map(WeatherModel::create)
                    .map(w -> { 
                        w.setCityName(cityName);
                        w.setTemp(temperature);
                        return w;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }       

        return Collections.EMPTY_LIST;
    }
}
