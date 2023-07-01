package com.example.demo.converter;

import com.example.demo.entity.Ingredient;
import com.example.demo.repository.IngredientRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository tacoRepository;

    public IngredientByIdConverter(IngredientRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @Override
    public Ingredient convert(String s) {
        return tacoRepository.findById(s).get();
    }
}
