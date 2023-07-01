package com.example.demo.repository;

import com.example.demo.entity.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@Repository
public class JdbcOrderRepositoryImpl {

    private JdbcTemplate jdbcTemplate;

    private JdbcOperations jdbcOperations;

    public JdbcOrderRepositoryImpl(JdbcTemplate jdbcTemplate, JdbcOperations jdbcOperations) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcOperations = jdbcOperations;
    }

//    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        order.setId(UUID.randomUUID().toString());
        order.setPlacedAt(new Date());

        PreparedStatementCreatorFactory statementCreatorFactory = new PreparedStatementCreatorFactory(
            "insert into taco_order (id, placed_at) values (?, ?)",
            Types.VARCHAR,
            Types.TIMESTAMP
        );

        PreparedStatementCreator preparedStatement = statementCreatorFactory.newPreparedStatementCreator(
            List.of(order.getId(), order.getPlacedAt())
        );

        jdbcOperations.update(preparedStatement);

//        DeliveryAddress deliveryAddress = saveDeliveryAddress(order.getId(), order.getDeliveryAddress());
//        Payment payment = savePayment(order.getId(), order.getPayment());
//        List<Taco> tacos = order.getTacos().stream()
//            .map((taco -> saveTaco(order.getId(), taco)))
//            .collect(Collectors.toList());
//
//        order.setDeliveryAddress(deliveryAddress);
//        order.setPayment(payment);
//        order.setTacos(tacos);

        return order;
    }

    private DeliveryAddress saveDeliveryAddress(String orderId, DeliveryAddress deliveryAddress) {
        deliveryAddress.setId(UUID.randomUUID().toString());
        PreparedStatementCreatorFactory statementCreatorFactory = new PreparedStatementCreatorFactory(
            "insert into delivery_address (id, name, street, city, state, zip, taco_order_id) values (?, ?, ?, ?, ?, ?, ?)",
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR
        );

        PreparedStatementCreator preparedStatement = statementCreatorFactory.newPreparedStatementCreator(
            List.of(deliveryAddress.getId(),
                deliveryAddress.getDeliveryName(),
                deliveryAddress.getDeliveryStreet(),
                deliveryAddress.getDeliveryCity(),
                deliveryAddress.getDeliveryState(),
                deliveryAddress.getDeliveryZip(),
                orderId
            )
        );

        jdbcOperations.update(preparedStatement);
        return deliveryAddress;
    }

    private Payment savePayment(String orderId, Payment payment) {
        payment.setId(UUID.randomUUID().toString());
        PreparedStatementCreatorFactory statementCreatorFactory = new PreparedStatementCreatorFactory(
            "insert into delivery_address (id, cc_number, cc_expiration, cc_cvv, taco_order_id) values (?, ?, ?, ?, ?)",
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR
        );
        PreparedStatementCreator preparedStatement = statementCreatorFactory.newPreparedStatementCreator(
            List.of(payment.getId(),
                    payment.getCcNumber(),
                    payment.getCcExpiration(),
                    payment.getCcCVV(),
                    orderId
            )
        );

        jdbcOperations.update(preparedStatement);
        return payment;
    }

    private Taco saveTaco(String orderId, Taco taco) {
        taco.setId(UUID.randomUUID().toString());
        taco.setCreateAt(new Date());
        PreparedStatementCreatorFactory statementCreatorFactory = new PreparedStatementCreatorFactory(
            "insert into taco (id, name, taco_order_id, created_at) values (?, ?, ?, ?)",
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.TIMESTAMP
        );

        PreparedStatementCreator preparedStatement = statementCreatorFactory.newPreparedStatementCreator(
            List.of(taco.getId(),
                taco.getName(),
                orderId,
                taco.getCreateAt()
            )
        );

        jdbcOperations.update(preparedStatement);
//        saveIngredientRefs(taco.getId(), taco.getIngredients());
        return taco;
    }

    private void saveIngredientRefs( String tacoId, List<Ingredient> ingredients) {
        PreparedStatementCreatorFactory statementCreatorFactory = new PreparedStatementCreatorFactory(
            "insert into ingredient_ref (taco_id, ingredient_id) values (?, ?)",
            Types.VARCHAR,
            Types.VARCHAR
        );

        ingredients.forEach(ingredient -> {
            PreparedStatementCreator preparedStatement = statementCreatorFactory.newPreparedStatementCreator(
                List.of(tacoId, ingredient.getId())
            );
            jdbcOperations.update(preparedStatement);
        });
    }
}
