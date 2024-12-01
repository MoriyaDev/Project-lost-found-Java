package com.moriya.project_moriya_java.controller;

import com.moriya.project_moriya_java.model.Ads;
import com.moriya.project_moriya_java.model.Users;
import com.moriya.project_moriya_java.service.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("/api/users")
@RestController
@CrossOrigin
public class UsersController {

    private UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // Get all users
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(usersRepository.findAll(), HttpStatus.OK);
    }

    // Get a specific user by id
    @GetMapping("/getUser/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Add a new user
    @PostMapping("/addUser")
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        Users newUser = usersRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,@RequestBody Users user) {
        if (!id.equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        usersRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

   //   @PutMapping("/updateAd/{id}")
   //   public ResponseEntity<?> updateAd(@PathVariable Long id, @RequestBody Ads ad) {
   //     if (!id.equals(ad.getId())) {
   //          return new ResponseEntity<>(HttpStatus.CONFLICT);
   //      }
   //     adsRepository.save(ad);
   //     return new ResponseEntity<>(HttpStatus.OK);
   // }



    // Delete a user
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        usersRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("חסר שם");
        }

        List<Users> usersList = usersRepository.findAll();
        for (Users u : usersList) {
            if (u.getName().equals(user.getName())) {
                if (u.getPassword().equals(user.getPassword())) {
                    return ResponseEntity.ok(u);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("הסיסמא שגויה");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("המשתמש לא נמצא");
    }


    @PostMapping(value = "/signUp")
    public ResponseEntity<Users> signUp(@RequestBody Users user) {
        List<Users> usersList = usersRepository.findAll();
        for (Users u : usersList) {
            if (u.getName().equals(user.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }
        Users newUser = usersRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED); // 201
    }




}
