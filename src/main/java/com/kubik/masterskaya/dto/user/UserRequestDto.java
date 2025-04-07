package com.kubik.masterskaya.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(toBuilder = true)
public class UserRequestDto {
    private String username;
    private String password;
    private String email;
    private String fName;
    private String sName;
    private String lName;
    private String phoneNumber;
    private Date birthday;
}
