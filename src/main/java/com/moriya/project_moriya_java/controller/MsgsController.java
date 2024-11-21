package com.moriya.project_moriya_java.controller;

import com.moriya.project_moriya_java.model.Ads;
import com.moriya.project_moriya_java.model.Msgs;
import com.moriya.project_moriya_java.service.AdsRepository;
import com.moriya.project_moriya_java.service.MsgsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/msgs")
public class MsgsController {
    //הודעות
    @Autowired
    private MsgsRepository msgsRepository;
    @Autowired
    private AdsRepository adsRepository;

    public MsgsController(MsgsRepository msgsRepository) {
        this.msgsRepository = msgsRepository;
    }

    @GetMapping("/getMsgsForAd/{adId}")
    public List<Msgs> getMsgsForAd(@PathVariable Long adId)
    {
        return  msgsRepository.findByAdID(adId);
    }


    // Get all msgs
    @GetMapping("/getAllMsgs")
    public ResponseEntity<List<Msgs>> getAllMsgs() {
        return new ResponseEntity<>(msgsRepository.findAll(), HttpStatus.OK);
    }

    // Get a specific ad by id
    @GetMapping("/getMsg/{id}")
    public ResponseEntity<Msgs> getAdById(@PathVariable Long id) {
        Msgs msg = msgsRepository.findById(id).orElse(null);
        if (msg == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // Add a new msg
    @PostMapping("/addMsg")
    public ResponseEntity<Msgs> addMsg(@RequestBody Msgs msg) {
        // בדיקה אם ה-adID תקין (לא null)
        if (msg.getAdID() != null) {
            boolean adExists = adsRepository.existsById(msg.getAdID());
            if (!adExists) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // מודעה לא קיימת
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // adID חייב להיות קיים
        }

        // עדכון תאריך השליחה להודעה
        msg.setSentAt(LocalDate.now());

        // שמירת ההודעה
        Msgs savedMsg = msgsRepository.save(msg);

        return new ResponseEntity<>(savedMsg, HttpStatus.CREATED);
    }


    // Update an existing msg
    @PutMapping("/updateMsg/{id}")
    public ResponseEntity<Msgs> updateMsg(@PathVariable Long id, @RequestBody Msgs msg) {
        if (!id.equals(msg.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Msgs updatedMsg = msgsRepository.save(msg);
        return new ResponseEntity<>(updatedMsg, HttpStatus.OK);
    }

    // Delete a msg
    @DeleteMapping("/deleteMsg/{id}")
    public ResponseEntity deleteMsg(@PathVariable Long id) {
        msgsRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }




}
