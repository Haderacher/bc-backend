package com.haderacher.bcbackend.service;

import com.haderacher.bcbackend.dto.LoginStudentRequest;
import com.haderacher.bcbackend.dto.RegisterStudentRequest;
import com.haderacher.bcbackend.entity.aggregates.student.Student;
import com.haderacher.bcbackend.entity.aggregates.student.StudentRepository;
import com.haderacher.bcbackend.exception.UserExistException;
import com.haderacher.bcbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Component
public class StudentService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> student = studentRepository.findByUsername(username);
        student.orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(student.get().getUsername(), student.get().getPassword(), AuthorityUtils.createAuthorityList("ROLE_STUDENT"));
    }

    @Transactional
    public String registerStudentAndGetToken(RegisterStudentRequest registerStudentRequest) {
        if (studentRepository.findByUsername(registerStudentRequest.getUsername()).isPresent()) {
            throw new UserExistException("student " + registerStudentRequest.getUsername() + " already exists");
        }
        Student student = Student.builder()
                .username(registerStudentRequest.getUsername())
                .password(registerStudentRequest.getPassword())
                .phoneNumber(registerStudentRequest.getPhone())
                .build();
        studentRepository.save(student);
        return jwtUtil.toToken(student);
    }

    public String loginStudentAndGetToken(LoginStudentRequest loginStudentRequest) {
        Optional<Student> student = studentRepository.findByUsername(loginStudentRequest.getUsername());
        if (student.isEmpty() || !student.get().getPassword().equals(loginStudentRequest.getPassword())) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return jwtUtil.toToken(student.get());
    }
}
