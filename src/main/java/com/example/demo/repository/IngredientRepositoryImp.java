package com.example.demo.repository;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Ingredient.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//@Repository
public class IngredientRepositoryImp {

    private static Logger LOG = LoggerFactory.getLogger(IngredientRepositoryImp.class);

    private JdbcTemplate jdbcTemplate;

    public IngredientRepositoryImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ingredient> getIngredients() {
        return jdbcTemplate.query("select id, name, type from ingredient", this::mapRowToIngredient);
    }

    public Optional<Ingredient> findById(String id) {
        List<Ingredient> ingredients = jdbcTemplate.query("select id, name, type from ingredient where id=?", this::mapRowToIngredient, id);
        if (ingredients.isEmpty()) {
            return null;
        }
        return Optional.ofNullable(ingredients.get(0));
    }

    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("insert into ingredient values (?, ?, ?)",
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getType()
        );
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
            row.getString("id"),
            row.getString("name"),
            Type.valueOf(row.getString("type"))
        );
    }
}
