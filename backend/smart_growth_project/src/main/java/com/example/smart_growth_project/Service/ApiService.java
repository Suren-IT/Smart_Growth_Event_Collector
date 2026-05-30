package com.example.smart_growth_project.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.HashMap;

@Service
public class ApiService {

    @Autowired
    private WebClient webclient;

    //values and apis

    String username = "Suren";

    String apikey="894c43f85440802f954583559b2abc1a12731051";

    @Autowired
    private RestTemplate restTemplate;
    /// get Api data using restTemplate
    public String getApiData() {
        /* steps1: to authenticate create EventService(constructor) with builder (above this method) optinal i skipped
        step2:create reference of  Httpheader class and  headers.set("Authorization", "ApiKey " + username + ":" + apikey);
        step3:put Http entity and ResponseEntity to get body with exchange */

        String api="https://clist.by/api/v4/contest/" +
                "?username="+username
                +"&api_key="+apikey
                +"&upcoming=true"+
                "&format=json"+
                "&order_by=start"+
                "&limit=50";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","ApiKey "+username+":"+apikey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(api,
                HttpMethod.GET,
                entity,
                String.class);

        return response.getBody();
    }
    /// get Api data useing Webclient

    public String getdata(){

        return webclient.get().uri("https://WebDevHarsha.github.io/open-hackathons-api/data.json").retrieve().bodyToMono(String.class).block();
    }


    public void getScrapping() throws IOException {
/*
         Document doc =
                Jsoup.connect("https://leetcode.com/contest/")
                        .userAgent("Mozilla/5.0")
                        .header("Accept-Language", "en-US,en;q=0.9")
                        .header("Accept", "text/html")
                        .get();

        System.out.println(doc);

        Connection.Response response =
                Jsoup.connect("https://leetcode.com/contest/")
                       .ignoreHttpErrors(true)
                        .execute();

        System.out.println(response.statusCode());
        System.out.println(response.body());

*/
        Document doc= Jsoup.connect("https://quotes.toscrape.com")

                /*useing the useragent
                .userAgent(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/136.0 Safari/537.36"
               ). */
                .get();
        Elements elist = doc.select(".col-md-8");
        for (Element e:elist){
            System.out.println(e.select("h1").text());
        }
        System.out.println(doc);

    }
    //1.hachathon useing
    //NOT USEING ONLY FOR LEARNING
    public String getHackerEarth(int id) {

        /*
                THIS VERSION IS NOT WORKING
        String api="https://clist.by/api/v4/contest/"
                + "?upcoming=true"
                + "&format=json"
                + "&order_by=start"
                + "&limit=10"
                + "&resource__id=73";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","ApiKey "+username+":"+apikey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(api,
                HttpMethod.GET,
                entity,
                String.class);

        return response.getBody(); */

        String api = "https://clist.by/api/v1/contest/"
                + "?username=" + username
                + "&api_key=" + apikey
                + "&resource__id="+id
                + "&upcoming=true"
                + "&format=json"
                + "&order_by=start"
                + "&limit=20";

        String response = restTemplate.getForObject(api,String.class);
        return response;
    }
//    COMMON METHOD WITH RESOURCES ID

    private HashMap<Integer ,String> cacheMap = new HashMap<>();
    private HashMap<Integer,Long> cacheTime = new HashMap<>();

    public String getAllEvents(int id){

        //add cache data so that can handle the multiple request
        long currentTime = System.currentTimeMillis();

        if(cacheMap.containsKey(id) && currentTime - cacheTime.get(id) < 300000){
            return  cacheMap.get(id);
        }
        //get the api data
        String api = "https://clist.by/api/v1/contest/"
                + "?username=" + username
                + "&api_key=" + apikey
                + "&resource__id="+id
                + "&upcoming=true"
                + "&format=json"
                + "&order_by=start"
                + "&limit=20";

        String response = restTemplate.getForObject(api,String.class);
        cacheMap.put(id,response);
        cacheTime.put(id,currentTime);
        return response;
    }
}
