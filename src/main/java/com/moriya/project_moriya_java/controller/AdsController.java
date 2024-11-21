package com.moriya.project_moriya_java.controller;

import com.moriya.project_moriya_java.dto.AdsDto;
import com.moriya.project_moriya_java.model.Ads;
import com.moriya.project_moriya_java.model.Categories;
import com.moriya.project_moriya_java.service.AdsRepository;
import com.moriya.project_moriya_java.service.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/ads")
public class AdsController {

    @Autowired
    private  final AdsRepository adsRepository;
    private static String DIRECTORY_PATH=System.getProperty("user.dir")+"//images//";

    @Autowired
    private CategoriesRepository categoriesRepository;

    private static final String UPLOAD_DIR = "/Project_Moriya_React/images/";  // נתיב אחסון התמונות


    @Autowired
    public AdsController(AdsRepository adsRepository) throws IOException {
        this.adsRepository = adsRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<Ads> upload(@RequestPart("ad") Ads ad,
                                      @RequestPart("image") MultipartFile file) throws IOException {

        Path pathFile=Paths.get(DIRECTORY_PATH+file.getOriginalFilename());
        //byte[] arrImage=Files.readAllBytes(pathFile);
         Files.write(pathFile,file.getBytes());
         ad.setImageUrl(pathFile.toString());
         Ads newAd= adsRepository.save(ad);
         return new ResponseEntity<>(newAd, HttpStatus.OK);
}

//public ResponseEntity<Ads>




    @PostMapping("/addAd")
    public ResponseEntity<?> addAd(@Valid @RequestBody AdsDto adDto) {
        try {
            Ads newAd = new Ads();
            Categories category = categoriesRepository.findById(adDto.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            newAd.setUserId(adDto.getUserId());
            newAd.setCategory(category);
            newAd.setDatePosted(adDto.getDatePosted());
            newAd.setUpDate(adDto.getUpDate());
            newAd.setTitle(adDto.getTitle());
            newAd.setDesc(adDto.getDesc());
            newAd.setCity(adDto.getCity());
            newAd.setLocation(adDto.getLocation());
            newAd.setDateFoundOrLost(adDto.getDateFoundOrLost());
            newAd.setAvailability(adDto.getAvailability());
            newAd.setPhone(adDto.getPhone());
            newAd.setType(adDto.getType());
            newAd.setStatus(adDto.getStatus());


            Ads savedAd = adsRepository.save(newAd);
            return new ResponseEntity<>(savedAd, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving ad: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    // קבלת כל המודעות
    @GetMapping("/getAllAds")
    public ResponseEntity<List<AdsDto>> getAllAds()  throws IOException{
        List<Ads> adsList = adsRepository.findAll();
        List<AdsDto> adsDtoList = new ArrayList<>();
        for (Ads ad : adsList) {
            // קריאת קובץ התמונה עבור כל מודעה
            Path path = Paths.get(ad.getImageUrl());
            byte[] arr = Files.readAllBytes(path);

            // יצירת AdsDto והעתקת הנתונים
            AdsDto adsDto = new AdsDto();
            adsDto.setUserId(ad.getUserId());
            adsDto.setCategory(ad.getCategory());
            adsDto.setDatePosted(ad.getDatePosted());
            adsDto.setUpDate(ad.getUpDate());
            adsDto.setTitle(ad.getTitle());
            adsDto.setDesc(ad.getDesc());
            adsDto.setCity(ad.getCity());
            adsDto.setLocation(ad.getLocation());
            adsDto.setDateFoundOrLost(ad.getDateFoundOrLost());
            adsDto.setAvailability(ad.getAvailability());
            adsDto.setPhone(ad.getPhone());
            adsDto.setImage(arr); // הוספת מערך הבייטים
            adsDto.setType(ad.getType());
            adsDto.setStatus(ad.getStatus());

            // הוספה לרשימת AdsDto
            adsDtoList.add(adsDto);
        }

        // החזרת הרשימה
        return new ResponseEntity<>(adsDtoList, HttpStatus.OK);
    }


    @GetMapping("/getAd/{id}")
    public ResponseEntity<AdsDto> getAdById(@PathVariable Long id) throws IOException {
        // חיפוש המודעה לפי מזהה
        Ads ad = adsRepository.findById(id).orElse(null);

        if (ad == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        // קריאת קובץ התמונה
        Path path = Paths.get(ad.getImageUrl());
        byte[] arr = Files.readAllBytes(path);

        // יצירת אובייקט AdsDto והעתקת נתונים מהמודעה
        AdsDto adsDto = new AdsDto();
        adsDto.setUserId(ad.getUserId());
        adsDto.setCategory(ad.getCategory());
        adsDto.setDatePosted(ad.getDatePosted());
        adsDto.setUpDate(ad.getUpDate());
        adsDto.setTitle(ad.getTitle());
        adsDto.setDesc(ad.getDesc());
        adsDto.setCity(ad.getCity());
        adsDto.setLocation(ad.getLocation());
        adsDto.setDateFoundOrLost(ad.getDateFoundOrLost());
        adsDto.setAvailability(ad.getAvailability());
        adsDto.setPhone(ad.getPhone());
        adsDto.setImage(arr);
        adsDto.setType(ad.getType());
        adsDto.setStatus(ad.getStatus());

        return new ResponseEntity<>(adsDto, HttpStatus.OK);
    }


    // עדכון מודעה קיימת
    @PutMapping("/updateAd/{id}")
    public ResponseEntity<?> updateAd(@PathVariable Long id, @RequestBody Ads ad) {
        if (!id.equals(ad.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        adsRepository.save(ad);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // מחיקת מודעה
    @DeleteMapping("/deleteAd/{id}")
    public ResponseEntity deleteAd(@PathVariable Long id) {
        adsRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
