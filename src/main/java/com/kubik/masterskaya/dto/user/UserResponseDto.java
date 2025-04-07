package com.kubik.masterskaya.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private String fName;
    private String sName;
    private String lName;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private String role;
    private Boolean isActive;
}
