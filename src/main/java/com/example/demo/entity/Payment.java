package com.example.demo.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

//    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;

//    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
//            message="Must be formatted MM/YY")
    private String ccExpiration;

//    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @OneToOne
    @JoinColumn(name = "order_id")
    private TacoOrder order;

}