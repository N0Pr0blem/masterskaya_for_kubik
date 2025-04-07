package com.kubik.masterskaya.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String fName;
    private String sName;
    private String lName;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ToString.Include(name = "password")
    private String makePassword(){return "********";}

}

