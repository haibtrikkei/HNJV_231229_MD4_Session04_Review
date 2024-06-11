package org.example.demo_review_jwt_security_api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(nullable = false,unique = true,length = 100)
    private String username;
    @Column(nullable = false,length = 100)
    private String password;
    @Column(length = 70)
    private String fullName;
    @Column(length = 200)
    private String address;
    @Column(length = 100, unique = true)
    private String email;
    @Column(length = 20)
    private String phone;
    private Boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roles;
}
