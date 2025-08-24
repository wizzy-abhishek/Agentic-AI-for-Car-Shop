package com.cardetailServer.BookCarServer.config;

import com.cardetailServer.BookCarServer.enitity.Car;
import com.cardetailServer.BookCarServer.repo.CarRepo;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class VectorLoad implements ApplicationListener<ContextRefreshedEvent> {

    private final CarRepo carRepo;
    private final VectorStore vectorStore;
    private final ExecutorService threads = Executors.newFixedThreadPool(10);
    private static final Logger logger = LoggerFactory.getLogger(VectorLoad.class);

    public VectorLoad(CarRepo carRepo, VectorStore vectorStore) {
        this.carRepo = carRepo;
        this.vectorStore = vectorStore;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        List<Car> carsToLoad = carRepo.findCarsNotInVectorStore();

        if (carsToLoad.isEmpty()) {
            logger.info("No new cars to add to vector store.");
            return;
        }

        List<Document> docs = carsToLoad.stream()
                .map(car -> new Document(
                        car.getId().toString(),
                        "id: %s, modelName: %s, description: %s, price: %s".formatted(
                                car.getId(),
                                car.getModelName(),
                                car.getDescription(),
                                car.getPrice()
                        ),
                        Map.of("carId", car.getId(), "type", "car")
                ))
                .toList();

        CompletableFuture.runAsync(() -> {
            try {
                vectorStore.add(docs);
                logger.info("Added {} cars to vector store.", docs.size());
            } catch (Exception e) {
                logger.error("Failed to add documents to vector store: {}", e.getMessage());
                e.printStackTrace();
            }
        }, threads);
    }

    @PreDestroy
    public void shutdownExecutor() {
        threads.shutdown();
    }

    private void a(){
        List<Car> carsToLoad = List.of(
                new Car("Toyota Camry", "Reliable midsize sedan",  "$26,000"),
                new Car("Honda Accord", "Popular family car",  "$28,000"),
                new Car("Ford Mustang", "Classic American muscle car",  "$35,000"),
                new Car("Chevrolet Camaro", "Sporty coupe",  "$32,000"),
                new Car("Tesla Model S", "Electric luxury sedan",  "$85,000"),
                new Car("BMW 3 Series", "Compact executive sedan",  "€42,000"),
                new Car("Audi A4", "Luxury sedan with tech features", "€45,000"),
                new Car("Mercedes C-Class", "Premium luxury sedan",  "€48,000"),
                new Car("Volkswagen Golf", "Compact hatchback",  "€23,000"),
                new Car("Honda Civic", "Popular compact car",  "$24,000"),
                new Car("Toyota Corolla", "Reliable compact sedan",  "$22,000"),
                new Car("Nissan Altima", "Comfortable midsize sedan", "$25,000"),
                new Car("Hyundai Elantra", "Affordable sedan", "₹21,00,000"),
                new Car("Kia Forte", "Economical compact sedan","₹20,00,000"),
                new Car("Mazda 3", "Fun-to-drive compact car", "$23,000"),
                new Car("Subaru Impreza", "All-wheel drive compact car", "$24,000"),
                new Car("Chevrolet Malibu", "Midsize sedan", "$25,000"),
                new Car("Ford Fusion", "Comfortable family sedan", "$26,000"),
                new Car("Tesla Model 3", "Popular electric sedan", "$40,000"),
                new Car("BMW 5 Series", "Executive luxury sedan", "€58,000"),
                new Car("Audi A6", "Luxury midsize sedan", "€55,000"),
                new Car("Mercedes E-Class", "Premium sedan with comfort", "€60,000"),
                new Car("Lexus ES", "Reliable luxury sedan", "$43,000"),
                new Car("Infiniti Q50", "Luxury sport sedan", "$40,000"),
                new Car("Jaguar XE", "Sporty luxury sedan", "£45,000"),
                new Car("Alfa Romeo Giulia", "Italian performance sedan", "€48,000"),
                new Car("Volvo S60", "Safe luxury sedan", "$40,000"),
                new Car("Acura TLX", "Sporty luxury sedan", "$38,000"),
                new Car("Cadillac CT5", "American luxury sedan", "$39,000"),
                new Car("Genesis G70", "Premium sport sedan", "$41,000"),
                new Car("Porsche Panamera", "Luxury performance sedan", "€90,000"),
                new Car("Chevrolet Corvette", "High-performance sports car", "$65,000"),
                new Car("Ford Explorer", "Midsize SUV", "$35,000"),
                new Car("Toyota RAV4", "Compact SUV", "$28,000"),
                new Car("Honda CR-V", "Popular compact SUV", "$29,000"),
                new Car("Nissan Rogue", "Compact family SUV", "$27,000"),
                new Car("Hyundai Tucson", "Affordable SUV", "₹25,00,000"),
                new Car("Kia Sportage", "Compact SUV",  "₹26,00,000"),
                new Car("Mazda CX-5", "Stylish compact SUV", "$27,000"),
                new Car("Subaru Forester", "All-wheel drive SUV", "$28,000"),
                new Car("Jeep Cherokee", "Off-road capable SUV", "$29,000"),
                new Car("Ford Escape", "Compact SUV", "$26,000"),
                new Car("BMW X3", "Luxury compact SUV",  "€45,000"),
                new Car("Audi Q5", "Premium compact SUV",  "€47,000"),
                new Car("Mercedes GLC", "Luxury SUV",  "€50,000"),
                new Car("Lexus NX", "Luxury compact SUV",  "$42,000"),
                new Car("Volvo XC60", "Safe and premium SUV", "$43,000"),
                new Car("Tesla Model X", "Electric SUV", "$95,000"),
                new Car("Porsche Macan", "Compact luxury SUV", "€55,000"),
                new Car("Jaguar F-Pace", "Sporty luxury SUV", "£50,000")
        );
        carRepo.saveAll(carsToLoad);
    }
}
