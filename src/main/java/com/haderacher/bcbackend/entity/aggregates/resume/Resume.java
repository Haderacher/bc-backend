package com.haderacher.bcbackend.entity.aggregates.resume;

import com.haderacher.bcbackend.entity.valueobject.Experience;
import com.haderacher.bcbackend.entity.valueobject.ProjectExp;
import com.haderacher.bcbackend.entity.valueobject.Education;
import com.haderacher.bcbackend.entity.aggregates.student.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.ArrayList; // 使用 ArrayList 更灵活，因为列表是有序的
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "resumes") // 映射到数据库表名为 'resumes'
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 多份简历属于一个学生
    @JoinColumn(name = "student_id", nullable = false) // 外键列名
    private Student student; // 关联的学生实体

    @Column(nullable = false, length = 255)
    private String title; // 简历标题 (例如："我的软件工程师简历", "实习简历")

    @Column(length = 255)
    private String fileUrl; // 如果简历是上传的文件，这里存储文件的URL

    @Column(columnDefinition = "TEXT") // 自我介绍或概述
    private String summary;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "resume_educations",
            joinColumns = @JoinColumn(name = "resume_id")
    )
    private List<Education> educationHistory = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // --- 构造函数 ---
    public Resume() {}

    public Resume(String title, Student student) {
        this.title = title;
        this.student = student;
    }

    // --- 生命周期回调 ---
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Resume resume = (Resume) o;
        return getId() != null && Objects.equals(getId(), resume.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

