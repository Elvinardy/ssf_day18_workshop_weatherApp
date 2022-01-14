package iss.day18_workshop_httprequest.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class WeatherModel {
    
    private String cityName;
    private String main;
    private String description;
    private String icon;
    private Float temp;
    private Float latitude;
    private Float longtitude;

    public String getCityName() {
        return this.cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getMain() {
        return this.main;
    }
    public void setMain(String main) {
        this.main = main;
    }
    public String GetDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = "http://openweathermap.org/img/wn/%s@2x.png";
    }
    public Float getTemp() {
        return this.temp;
    }
    public void setTemp(Float temp) {
        this.temp = temp;
    }
    public Float getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
    public Float getLongtitude() {
        return this.longtitude;
    }
    public void setLongtitude(Float longtitude) {
        this.longtitude = longtitude;
    }

    public static WeatherModel create(JsonObject obj) {
        final WeatherModel w = new WeatherModel();
        w.setMain(obj.getString("main"));
        w.setDescription(obj.getString("description"));
        w.setIcon(obj.getString("icon"));
        return w;
    }
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("cityName",cityName)
            .add("main", main)
            .add("desciption", description)
            .add("icon", icon)
            .add("tempertature",temp)
            .build();
    }
    public void addAttribute(String string, String city) {
    }
}
