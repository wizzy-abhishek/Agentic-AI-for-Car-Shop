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

    public String getPrice() {
        return price;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public Car() {
    }

    public Car(String modelName, String description, String price) {
        this.modelName = modelName;
        this.description = description;
        this.price = price;
    }
}
