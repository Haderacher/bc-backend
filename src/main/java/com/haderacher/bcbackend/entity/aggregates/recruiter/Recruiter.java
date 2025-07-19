package com.haderacher.bcbackend.entity.aggregates.recruiter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "recruiter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recruiter {

    @Id // 标记为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增长策略
    private Long id;

    @Column(nullable = false, unique = true, length = 50) // 用户名，不允许为空，唯一，最大长度50
    private String username;

    @Column(nullable = false, length = 100) // 密码，不允许为空，需要加密存储
    private String password;

    @Column(nullable = false, unique = true, length = 100) // 邮箱，不允许为空，唯一，最大长度100
    private String email;

    @Column(nullable = false, length = 50) // 真实姓名
    private String fullName;

    @Column(length = 20)
    private String phoneNumber;

    @Lob // 大对象类型，适用于存储长文本（例如：自我介绍、个人描述）
    @Column(columnDefinition = "TEXT") // 明确指定数据库字段类型为 TEXT
    private String selfIntroduction;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
        Recruiter recruiter = (Recruiter) o;
        return getId() != null && Objects.equals(getId(), recruiter.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}

