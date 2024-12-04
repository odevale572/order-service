package com.estore.order.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {

    // inject via application.properties
    private String message = "test";

    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("tasks", tasks);

        return "welcome"; //view
    }

    // /hello?name=kotlin
    @GetMapping("/logs")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "") 
			String name, Model model) {

        StringBuffer buffer = new StringBuffer();
        	try{
        	   FileInputStream fstream = new FileInputStream("D:\\ecommerse-microservices\\order-service\\log.txt");
        	   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        	   String strLine;
        	   /* read log line by line */
        	   while ((strLine = br.readLine()) != null)   {
        	     /* parse strLine to obtain what you want */
        		   buffer.append(strLine);
        		   buffer.append("\n");
        	   }
        	   model.addAttribute("message", buffer.toString());
        	   fstream.close();
        	} catch (Exception e) {
        	     System.err.println("Error: " + e.getMessage());
        	}

        return "welcome"; //view
    }

}