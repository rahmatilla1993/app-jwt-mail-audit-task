package com.example.appjwtmailaudittask.entity;

import com.example.appjwtmailaudittask.enums.RoleName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Employee extends User {

    @CreationTimestamp
    private UUID createdBy;

    @UpdateTimestamp
    private UUID updatedBy;

    private Double salary;

    //for salary
    private Integer month_number;

    @OneToMany
    private Set<Task> tasks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return RoleName.EMPLOYEE.getAuthorities();
    }
}
