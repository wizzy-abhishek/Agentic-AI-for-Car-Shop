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
                        "id: %s, modelName: %s, description: %s, available_quantity: %s".formatted(
                                car.getId(), car.getModelName(), car.getDescription(), car.getAvailableQuantities()
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
                new Car("Toyota Camry", "Reliable midsize sedan", 1, "$26,000"),
                new Car("Honda Accord", "Popular family car", 2, "$28,000"),
                new Car("Ford Mustang", "Classic American muscle car", 3, "$35,000"),
                new Car("Chevrolet Camaro", "Sporty coupe", 1, "$32,000"),
                new Car("Tesla Model S", "Electric luxury sedan", 2, "$85,000"),
                new Car("BMW 3 Series", "Compact executive sedan", 3, "€42,000"),
                new Car("Audi A4", "Luxury sedan with tech features", 4, "€45,000"),
                new Car("Mercedes C-Class", "Premium luxury sedan", 0, "€48,000"),
                new Car("Volkswagen Golf", "Compact hatchback", 2, "€23,000"),
                new Car("Honda Civic", "Popular compact car", 2, "$24,000"),
                new Car("Toyota Corolla", "Reliable compact sedan", 8, "$22,000"),
                new Car("Nissan Altima", "Comfortable midsize sedan",9, "$25,000"),
                new Car("Hyundai Elantra", "Affordable sedan",3, "₹21,00,000"),
                new Car("Kia Forte", "Economical compact sedan",12, "₹20,00,000"),
                new Car("Mazda 3", "Fun-to-drive compact car",5, "$23,000"),
                new Car("Subaru Impreza", "All-wheel drive compact car",5, "$24,000"),
                new Car("Chevrolet Malibu", "Midsize sedan",2, "$25,000"),
                new Car("Ford Fusion", "Comfortable family sedan",8, "$26,000"),
                new Car("Tesla Model 3", "Popular electric sedan",1, "$40,000"),
                new Car("BMW 5 Series", "Executive luxury sedan",9, "€58,000"),
                new Car("Audi A6", "Luxury midsize sedan",4, "€55,000"),
                new Car("Mercedes E-Class", "Premium sedan with comfort",2, "€60,000"),
                new Car("Lexus ES", "Reliable luxury sedan",8, "$43,000"),
                new Car("Infiniti Q50", "Luxury sport sedan",9, "$40,000"),
                new Car("Jaguar XE", "Sporty luxury sedan",0, "£45,000"),
                new Car("Alfa Romeo Giulia", "Italian performance sedan",12, "€48,000"),
                new Car("Volvo S60", "Safe luxury sedan",3, "$40,000"),
                new Car("Acura TLX", "Sporty luxury sedan",1, "$38,000"),
                new Car("Cadillac CT5", "American luxury sedan",7, "$39,000"),
                new Car("Genesis G70", "Premium sport sedan",9, "$41,000"),
                new Car("Porsche Panamera", "Luxury performance sedan",9, "€90,000"),
                new Car("Chevrolet Corvette", "High-performance sports car",4, "$65,000"),
                new Car("Ford Explorer", "Midsize SUV",7, "$35,000"),
                new Car("Toyota RAV4", "Compact SUV",1, "$28,000"),
                new Car("Honda CR-V", "Popular compact SUV",7, "$29,000"),
                new Car("Nissan Rogue", "Compact family SUV",0, "$27,000"),
                new Car("Hyundai Tucson", "Affordable SUV",7, "₹25,00,000"),
                new Car("Kia Sportage", "Compact SUV", 9, "₹26,00,000"),
                new Car("Mazda CX-5", "Stylish compact SUV",5, "$27,000"),
                new Car("Subaru Forester", "All-wheel drive SUV",2, "$28,000"),
                new Car("Jeep Cherokee", "Off-road capable SUV",4, "$29,000"),
                new Car("Ford Escape", "Compact SUV",7, "$26,000"),
                new Car("BMW X3", "Luxury compact SUV", 1, "€45,000"),
                new Car("Audi Q5", "Premium compact SUV", 9, "€47,000"),
                new Car("Mercedes GLC", "Luxury SUV", 7, "€50,000"),
                new Car("Lexus NX", "Luxury compact SUV", 5, "$42,000"),
                new Car("Volvo XC60", "Safe and premium SUV",4, "$43,000"),
                new Car("Tesla Model X", "Electric SUV",6, "$95,000"),
                new Car("Porsche Macan", "Compact luxury SUV",2, "€55,000"),
                new Car("Jaguar F-Pace", "Sporty luxury SUV",1, "£50,000")
        );
        carRepo.saveAll(carsToLoad);
    }
}
