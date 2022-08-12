package com.NewsApp.Controllers;

import com.NewsApp.Models.DataLog;

import com.NewsApp.Models.ResponseModel;
import com.NewsApp.Repository.MongoRepositoty;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/news")
public class NewsControllers {
    @Autowired
    private MongoRepositoty repositoty;

    @GetMapping("/showLogs")
    public List<DataLog> showDataLogs() {
        return repositoty.findAll();
    }

    private void saveDataLogs(DataLog logs) {
        repositoty.save(logs);
    }

    @GetMapping("/getNews")
    public ResponseModel getNews(@RequestParam Map<String,String> allParams) throws Exception {

        try {
        String keyApi = "be337ed609fa45458f69324ec70c97d1";

        String sourceParam = "";

        if (allParams.getOrDefault("q", "null").equals("null") &&
            allParams.getOrDefault("qInTitle", "null").equals("null") &&
            allParams.getOrDefault("sources", "null").equals("null") &&
            allParams.getOrDefault("domains", "null").equals("null")) {
            throw new Exception();
        }
        else {
            sourceParam = allParams.toString().replace("{", "").replace("}", "").replace(",", "&") + "&";
        }

            String source = "https://newsapi.org/v2/everything?" + sourceParam + "apiKey=" + keyApi;

             System.out.println(source);

            URLConnection conn = new URL(source).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String resp = br.lines().collect(Collectors.joining());

            JSONArray json = new JSONArray("["+resp+"]");

            DataLog logs = new DataLog("NewApi", source,  new Date());
            saveDataLogs(logs);

            return new ResponseModel("Ok", json.toList());

        } catch (IOException | JSONException ex) {
            return new ResponseModel("ERROR", ex.getMessage());
//            return null;
        } catch (Exception ex) {
            return new ResponseModel("ERROR", "require domain, q, sources or qInTitle");
        }

    }

    @GetMapping("/getWeather")
    public ResponseModel getWeather() {

        String keyApi = "cf26387c0d1ca5eb8d30aa128fe942ed";

        try {
            String source = "https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=" + keyApi;

            System.out.println(source);

            URLConnection conn = new URL(source).openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String resp = br.lines().collect(Collectors.joining());

            JSONArray json = new JSONArray("["+resp+"]");

            DataLog logs = new DataLog("WeatherApi", source, new Date());
            saveDataLogs(logs);

            System.out.println("response of https://api.openweathermap.org" + resp);
            return new ResponseModel("Ok", json.toList());

        } catch (IOException | JSONException ex) {
            ex.getStackTrace();
            return new ResponseModel("ERROR", ex.getMessage());
        }

    }


}
