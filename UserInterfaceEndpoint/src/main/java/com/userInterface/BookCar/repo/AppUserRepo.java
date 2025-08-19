package com.userInterface.BookCar.repo;

import com.userInterface.BookCar.entity.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUsers, String > {

    Optional<AppUsers> findByEmailIgnoreCase(String email);

}
