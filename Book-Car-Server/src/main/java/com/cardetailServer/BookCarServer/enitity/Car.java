package com.cardetailServer.BookCarServer.enitity;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(unique = true)
    private String modelName;

    private String description;

    private Integer availableQuantities;

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

    public void setAvailableQuantities(Integer availableQuantities) {
        this.availableQuantities = availableQuantities;
    }

    public Car() {
    }

    public Car(String modelName, String description, Integer availableQuantities) {
        this.modelName = modelName;
        this.description = description;
        this.availableQuantities = availableQuantities;
    }
}
