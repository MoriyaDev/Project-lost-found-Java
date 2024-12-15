package com.moriya.project_moriya_java.service;

import com.moriya.project_moriya_java.model.EmailDetails;

public interface EmailRepository {
    String sendSimpleMail(EmailDetails emailDetails);
}
