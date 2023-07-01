package com.example.demo.entity;

import com.example.demo.mapper.TacoMapper;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "taco_order")
public class TacoOrder implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id = UUID.randomUUID().toString();

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    private DeliveryAddress deliveryAddress = new DeliveryAddress();

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment = new Payment();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }

    private Date placedAt;
}
