package com.haderacher.bcbackend.controller;

import com.haderacher.bcbackend.dto.LoginStudentRequest;
import com.haderacher.bcbackend.entity.aggregates.student.Student;
import com.haderacher.bcbackend.exception.InvalidAuthenticationException;
import com.haderacher.bcbackend.service.StudentService;
import com.haderacher.bcbackend.dto.RegisterStudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/students")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return null;
    }

    @PostMapping("/students/login")
    public ResponseEntity<Object> studentLogin(@RequestBody LoginStudentRequest loginStudentRequest) {
        String token;
        try {
            token = studentService.loginStudentAndGetToken(loginStudentRequest);
        }  catch (InvalidAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/students/register")
    public ResponseEntity<Object> studentRegister(@RequestBody RegisterStudentRequest registerStudentRequest) {
        String token;
        try {
            token = studentService.registerStudentAndGetToken(registerStudentRequest);
        } catch (Exception e) {
            throw new InvalidAuthenticationException();
        }
        return ResponseEntity.status(201)
                .body(token);
    }
}

