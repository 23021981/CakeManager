package com.waracle.cakemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemanager.exception.ErrorMessage;
import com.waracle.cakemanager.model.Cake;
import com.waracle.cakemanager.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

/*
* Author : Atul Kumar
* It has four end points
* 1. cakes- It will return the list of cakes in database
* 2. cakes/{cakeId} - It will return details of particular cake
* 3. addCake - It will insert the new cake details
* 4. cakeJsonData - It will download the cake data into json file*/
@RestController
@RequestMapping("/")
public class CakeController {

    @Autowired
    CakeService cakeService;

    @GetMapping(value = "/cakes", produces = "application/json")
    public List<Cake> getAllCakeDetails() throws ErrorMessage {
        List<Cake> cakeList = cakeService.getAllCakes();
        return cakeList;
    }

    @GetMapping(value = "/cakes/{cakeId}")
    public Cake getCakeDetail(@PathVariable("cakeId") String cakeId) throws ErrorMessage {
        Cake cake = cakeService.getCake(cakeId);
        if(cake == null) throw new ErrorMessage("CE01","Cake Id not found");
        return cake;
    }

    @PostMapping(path= "/addCake", consumes = "application/json", produces = "application/json")
    public Cake addNewCake(@RequestBody Cake cake){
        cakeService.addCake(cake);
        return cake;
    }

    @ExceptionHandler(value=ErrorMessage.class)
    public ResponseEntity<ErrorMessage> handler(ErrorMessage error){
        return new ResponseEntity<ErrorMessage>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/cakeJsonData", method = RequestMethod.GET)
    public ResponseEntity<byte[]> generateFile() throws IOException {

        List<Cake> cakeList = cakeService.getAllCakes();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cakeList);
        byte[] isr = json.getBytes();

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "json"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CakeData.json");

        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }
}
