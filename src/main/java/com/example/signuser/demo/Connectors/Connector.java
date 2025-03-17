package com.example.signuser.demo.Connectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.example.signuser.demo.UserModel;
import com.example.signuser.demo.Services.AiService;
import com.example.signuser.demo.Services.RegisterService;

import org.json.JSONObject;
import org.json.JSONArray;


import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins="http://127.0.0.1:5500")
@RestController
public class Connector {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/add")
    public UserModel addUser(@RequestBody UserModel userModel){
        return registerService.save(userModel);
    }

    @PostMapping("/signin")
    public UserModel signin(@RequestBody UserModel userModel){

        return registerService.loadUser(userModel);
    }

@GetMapping("/all")
public List<UserModel> getAll(){
    return registerService.getAllData();
}




@Autowired
private AiService aiService;

@Autowired
private JdbcTemplate jdbcTemplate;


@PostMapping("/dowithai")
public String processAIQuery(@RequestBody String userInput) {
    try {
        // Parse JSON input properly
        JSONObject jsonInput = new JSONObject(userInput);
        String userQuery = jsonInput.getString("query"); // Expecting {"query": "your text here"}

        // Generate SQL from AI Service
        String sql = aiService.generateSQL(userQuery);



        // Execute the SQL
        jdbcTemplate.execute(sql);
        return "{\"status\": \"success\", \"message\": \"Query executed successfully\"}";
    } catch (Exception e) {
        return "{\"status\": \"error\", \"message\": \"Error in AI processing: " + e.getMessage() + "\"}";
    }
}





}
