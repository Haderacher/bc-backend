package com.haderacher.bcbackend.entity.valueobject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    private String schoolName; // 学校名称
    private String degree;     // 学位，如：学士、硕士
    private String fieldOfStudy; // 专业领域
    private LocalDate startYear;     // 开始年份
    private LocalDate endYear;       // 结束年份
}
