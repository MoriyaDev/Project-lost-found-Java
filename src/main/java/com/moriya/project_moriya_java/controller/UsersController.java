package com.moriya.project_moriya_java.controller;

import com.moriya.project_moriya_java.model.Ads;
import com.moriya.project_moriya_java.model.Users;
import com.moriya.project_moriya_java.security.jwt.JwtUtils;
import com.moriya.project_moriya_java.service.RoleRepository;
import com.moriya.project_moriya_java.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.moriya.project_moriya_java.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.moriya.project_moriya_java.model.Users;
import com.moriya.project_moriya_java.security.CustomUserDetails;
import com.moriya.project_moriya_java.security.jwt.JwtUtils;
import com.moriya.project_moriya_java.service.RoleRepository;
import com.moriya.project_moriya_java.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.moriya.project_moriya_java.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequestMapping("/api/users")
@RestController
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
public class UsersController {

    private UsersRepository usersRepository;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;


    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }



    // Get all users
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(usersRepository.findAll(), HttpStatus.OK);
    }

    // Get a specific user by name
    @GetMapping("/getUser/{name}")
    public ResponseEntity<Users> getUserById(@PathVariable String name) {
        Users user = usersRepository.findByName(name);
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
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Users user) {
        if (!id.equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        usersRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }




    // Delete a user
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        usersRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        // בדיקה אם שם המשתמש או הסיסמה ריקים
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("חסר שם משתמש");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("חסרה סיסמה");
        }

        // חיפוש המשתמש על פי שם
        Users existingUser = usersRepository.findByName(user.getName());

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("משתמש לא נמצא");
        }

        // אימות וסיום תהליך ההתחברות
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        // החזרת המשתמש המלא עם העוגיה
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(existingUser);
    }


    @PostMapping(value = "/signUp")
    public ResponseEntity<Users> signUp(@RequestBody Users user) {


        List<Users> usersList = usersRepository.findAll();
        for (Users u : usersList) {
            if (u.getName().equals(user.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }
        //בדיקה לפי מייל ושם משתמש שהוא אינו קיים במערכת
        user.setPassword(new BCryptPasswordEncoder(8).encode(user.getPassword()));
        //כל משתמש שנרשם באופן אוטומטי נגדיר אותו כהרשאת user
        user.getRoles().add(roleRepository.findById((long) 1).get());
        Users newUser = usersRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED); // 201
    }


    @PostMapping("/signout")
    public ResponseEntity<?> signOut() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("you've been signed out!");
    }


}
