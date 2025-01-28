package com.cleartax.superhero.services;

import com.cleartax.superhero.dto.Superhero;
import com.cleartax.superhero.dto.SuperheroRequestBody;
import com.cleartax.superhero.repository.SuperheroRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    /*public SuperheroService(SuperheroRepository superheroRepository){
        this.superheroRepository = superheroRepository;
    }



     */

    public Optional<Superhero> getSuperhero(String name, String universe) {
        if (name != null && universe != null) {
            return getByNameAndUniverse(name, universe);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Superhero> getByNameAndUniverse(String name, String universe) {
        // Implement the logic to find the superhero by name and universe
        // For example, query the repository or database
        return superheroRepository.findByNameAndUniverse(name, universe);
    }
    private Superhero getByName(String name){
        return getDummyDate(name);
    }

    private Superhero getDummyDate(String name){
        Superhero superhero =  new Superhero();
        superhero.setName(name);
        return superhero;
    }

    public Superhero persistSuperhero(SuperheroRequestBody requestBody){
        Superhero superhero = new Superhero();
        superhero.setName(requestBody.getSuperheroName());
        superhero.setPower(requestBody.getPower());
        superhero.setUniverse(requestBody.getUniverse());

        return superheroRepository.save(superhero);
    }

    public Superhero updateSuperhero(String name, Superhero updatedSuperhero) {
        // Find the user by ID
        Superhero existingSuperhero = superheroRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + name));

        // Update the fields
        existingSuperhero.setName(updatedSuperhero.getName());
        existingSuperhero.setPower(updatedSuperhero.getPower());
        existingSuperhero.setUniverse(updatedSuperhero.getUniverse());

        // Save the updated user
        return superheroRepository.save(existingSuperhero);
    }

    public void deleteSuperhero(String id) {
        superheroRepository.deleteById(id);
    }
}
