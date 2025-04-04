package com.kubik.masterskaya.mapper;

import com.kubik.masterskaya.dto.product.ProductResponseDto;
import com.kubik.masterskaya.entity.Product;
import com.kubik.masterskaya.mapper.base.Mappable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends Mappable<Product, ProductResponseDto> {

}
