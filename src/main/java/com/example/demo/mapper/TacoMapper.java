package com.example.demo.mapper;

import com.example.demo.entity.Taco;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TacoMapper {

    public static final TacoMapper INSTANCE = Mappers.getMapper(TacoMapper.class);
}
