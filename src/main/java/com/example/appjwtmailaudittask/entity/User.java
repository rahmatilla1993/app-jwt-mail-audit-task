package com.example.appjwtmailaudittask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Table(name = "users")
@Check(constraints = "(char_length(password) > 5)")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  protected UUID id;

  @Column(nullable = false, length = 50)
  protected String firstName;

  @Column(nullable = false, length = 50)
  protected String lastName;

  @Column(nullable = false, unique = true)
  protected String email;

  protected String emailCode;

  protected String password;

  @Column(nullable = false, unique = true)
  protected String username;

  @Column(updatable = false, nullable = false)
  @CreationTimestamp
  protected Timestamp createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  protected Timestamp updatedAt;

  protected boolean AccountNonExpired = true;

  protected boolean AccountNonLocked = true;

  protected boolean CredentialsNonExpired = true;

  protected boolean Enabled = false;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return AccountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return AccountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return CredentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return Enabled;
  }
}
