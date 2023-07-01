package com.example.demo.controller;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Taco;
import com.example.demo.entity.TacoOrder;
import com.example.demo.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Ingredient.Type;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@Slf4j
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private IngredientRepository tacoRepository;

    public DesignTacoController(IngredientRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        tacoRepository.findAll().iterator().forEachRemaining(ingredient -> ingredients.add(ingredient));
        Arrays.stream(Type.values())
            .forEach(type -> {
                model.addAttribute(type.toString().toLowerCase(), filterIngredientByType(ingredients, type));
            }
        );
    }

    private List<Ingredient> filterIngredientByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
            .filter(ingredient -> ingredient.getType().equals(type))
            .collect(Collectors.toList());
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder tacoOrder() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        log.info("errors: {}", errors);
        if (errors.hasErrors()) {
            return "design";
        }

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/order/current";
    }

}
