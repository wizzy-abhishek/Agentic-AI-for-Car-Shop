package com.agentiAICarBooking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.ai.vectorstore.hanadb.HanaVectorEntity;

@Data
@Entity
@Builder
@Jacksonized
@NoArgsConstructor
@Table(name = "BOOKING_EMBED")
@AllArgsConstructor
public class BookingEmbed extends HanaVectorEntity {

    private String content;
}
