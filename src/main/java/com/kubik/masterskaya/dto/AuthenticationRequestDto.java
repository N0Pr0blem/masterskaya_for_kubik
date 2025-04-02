package com.kubik.masterskaya.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthenticationRequestDto {
    @Min(6)
    @Max(16)
    @NotEmpty
    private String username;

    @Min(6)
    @Max(16)
    @NotEmpty
    private String password;
}
