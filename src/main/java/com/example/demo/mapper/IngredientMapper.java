package com.example.demo.mapper;

import com.example.demo.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IngredientMapper {

    public static final IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

}
