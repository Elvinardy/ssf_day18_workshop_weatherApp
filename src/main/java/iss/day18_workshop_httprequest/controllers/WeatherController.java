package iss.day18_workshop_httprequest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iss.day18_workshop_httprequest.model.WeatherModel;
import iss.day18_workshop_httprequest.service.WeatherService;

@RestController
@RequestMapping(path="/weather", produces=MediaType.APPLICATION_JSON_VALUE)
public class WeatherController {
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherService weatherSvc;

    /* @PostMapping(consumes="application/json")
    public ReponseEntity<String> postWeather(@RequestBody String payload) {
        JsonObject body;
        
    } */
    @GetMapping
    public String getWeather(@RequestParam (required = true) String city, WeatherModel model) {
        
       System.out.println("weather: " + city);

       try {
           weatherSvc.getWeather(city);
       } catch (Exception ex) {
           ex.printStackTrace();
       }

       model.addAttribute("city",city);

        return "weather";
    }
}
    

