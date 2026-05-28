package com.example.smart_growth_project.Service;


import com.example.smart_growth_project.Configuration.AppConfig;
import com.example.smart_growth_project.Repository.EventRepository;
import com.example.smart_growth_project.Repository.SkillsRepository;
import com.example.smart_growth_project.Repository.UserRepository;
import com.example.smart_growth_project.entityModel.Event_Details;
import com.example.smart_growth_project.entityModel.Skill_Details;
import com.example.smart_growth_project.entityModel.User_Details;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EventService {

    //User repository
    @Autowired
    UserRepository userrepo;

    @Autowired
    SkillsRepository skillrepo;

    @Autowired
    EventRepository eventrepo;


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient webclient;


    //add user
    public User_Details addUser(User_Details user) {
        return userrepo.save(user);
    }

    public boolean userLogin(String email, String password) {

        return userrepo.existsByEmailAndPassword(email,password);
    }

    public void addSkills(Integer userId, String skill1, String skill2, String skill3) {

        //save all skills  -> only one time  so check before saving
        Skill_Details firstskill = skillrepo.findBySkillName(skill1);
        Skill_Details secondskill = skillrepo.findBySkillName(skill2);
        Skill_Details thirdskill = skillrepo.findBySkillName(skill3);
        List<Skill_Details> list =new  ArrayList<Skill_Details>(Arrays.asList(firstskill,secondskill,thirdskill));


        //1.I need to find the user ,2:set the list of skills using setter
        User_Details user = userrepo.findById(userId).orElse(null);
        user.setSkilllist(list);
        userrepo.save(user);



    }

    public List<Event_Details> getallEvents() {

        return eventrepo.findAll();
    }

    public Event_Details getEventByName(String eventName) {
        return  eventrepo.findByTitle(eventName);
    }

    /// get Api data using restTemplate
    public String getApiData() {

        String api="https://alfa-leetcode-api.onrender.com/contests"; //leetcode
        String response = restTemplate.getForObject(api,String.class);
        return response;
    }
    /// get Api data useing Webclient

    public String getdata(){

        return webclient.get().uri("https://WebDevHarsha.github.io/open-hackathons-api/data.json").retrieve().bodyToMono(String.class).block();
    }


    public void getScrapping() throws IOException {

//        Document doc =
//                Jsoup.connect("https://leetcode.com/contest/")
//                        .userAgent("Mozilla/5.0")
//                        .header("Accept-Language", "en-US,en;q=0.9")
//                        .header("Accept", "text/html")
//                        .get();
//
//        System.out.println(doc);

//        Connection.Response response =
//                Jsoup.connect("https://leetcode.com/contest/")
//                        .ignoreHttpErrors(true)
//                        .execute();
//
//        System.out.println(response.statusCode());
//        System.out.println(response.body());


        Document doc=Jsoup.connect("https://quotes.toscrape.com")
//                .userAgent(
//                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/136.0 Safari/537.36"
//                ).
                .get();
        Elements elist = doc.select(".col-md-8");
        for (Element e:elist){
            System.out.println(e.select("h1").text());
        }
//        System.out.println(doc);
//        Elements titles = doc.select(".title bold");
//        for(Element e:titles){
//
//            System.out.println(e);
//        }

    }
}

