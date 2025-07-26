package com.haderacher.bcbackend.dto;


import lombok.Data;

@Data
public class LoginStudentRequest {
    private String username;
    private String password;
}
