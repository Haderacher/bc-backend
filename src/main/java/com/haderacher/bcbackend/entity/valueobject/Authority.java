package com.haderacher.bcbackend.entity.valueobject;

import com.haderacher.bcbackend.entity.aggregates.student.Student;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Authority {

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public static enum RoleType {
        ROLE_STUDENT,
        ROLE_RECRUITER,
        ROLE_ADMIN;
    }

    @Override
    public String toString() {
        return this.role.toString();
    }

}
