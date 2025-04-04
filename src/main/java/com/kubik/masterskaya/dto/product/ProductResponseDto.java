package com.kubik.masterskaya.dto.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductResponseDto {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private String url;
    private String brand;
    private String carBrand;
}
