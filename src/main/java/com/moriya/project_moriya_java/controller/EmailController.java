package com.moriya.project_moriya_java.controller;

import com.moriya.project_moriya_java.model.EmailDetails;
import com.moriya.project_moriya_java.service.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailRepository emailRepository;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details) {
        String status=emailRepository.sendSimpleMail(details);
        return status;


    }
}
