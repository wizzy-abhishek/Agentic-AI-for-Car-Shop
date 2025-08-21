package com.cardetailServer.BookCarServer.enitity;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(unique = true)
    private String modelName;

    private String description;

    private Integer availableQuantities;

    private String price;

    @LastModifiedDate
    private LocalDateTime lastModification;

    public UUID getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAvailableQuantities() {
        return availableQuantities;
    }

    public String getPrice() {
        return price;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public void setAvailableQuantities(Integer availableQuantities) {
        this.availableQuantities = availableQuantities;
    }

    public Car() {
    }

    public Car(String modelName, String description, Integer availableQuantities, String price) {
        this.modelName = modelName;
        this.description = description;
        this.availableQuantities = availableQuantities;
        this.price = price;
    }
}
