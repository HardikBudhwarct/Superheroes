package com.cleartax.superhero.repository;

import com.cleartax.superhero.dto.Superhero;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SuperheroRepository extends MongoRepository<Superhero, String> {
//    void deleteByName(String name);
//
//    Optional<Superhero> findByName(String name);
}
