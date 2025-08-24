package com.agentiAICarBooking.entity;

import jakarta.persistence.*;
import org.springframework.ai.vectorstore.hanadb.HanaVectorEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Booking extends HanaVectorEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String modelName;

    @Column(nullable = false)
    private String buyerName;

    @Column(nullable = false)
    private String buyerEmail;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private char currencySymbol;

    @Column
    private String userDescription;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    public String getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public char getCurrencySymbol() {
        return currencySymbol;
    }

    public LocalDateTime getPurchaseDate(){
        return purchaseDate;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public Booking(String modelName,
                   String buyerName,
                   String buyerEmail,
                   BigDecimal price,
                   char currencySymbol,
                   String userDescription) {

        this.id = java.util.UUID.randomUUID().toString();
        this.modelName = modelName;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.price = price;
        this.currencySymbol = currencySymbol;
        this.purchaseDate = LocalDateTime.now();
        this.userDescription = userDescription;
    }

    public Booking(String modelName,
                   String buyerName,
                   String buyerEmail,
                   String price,
                   String currencySymbol,
                   Timestamp purchaseDate,
                   String id,
                   String content,
                   String userDescription) {

        this.id = id;
        this.modelName = modelName;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.price = new BigDecimal(price);
        this.currencySymbol = currencySymbol != null && !currencySymbol.isEmpty() ? currencySymbol.charAt(0) : ' ';
        this.purchaseDate = purchaseDate != null ? purchaseDate.toLocalDateTime() : null;
        this.userDescription = userDescription;
    }
}
