package com.example.demo.mapper;

import com.example.demo.entity.DeliveryAddress;
import com.example.demo.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TacoOrderMapper {

    public static final TacoOrderMapper INSTANCE = Mappers.getMapper(TacoOrderMapper.class);

}
