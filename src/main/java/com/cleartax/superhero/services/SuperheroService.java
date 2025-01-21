package com.cleartax.superhero.services;

import com.cleartax.superhero.dto.Superhero;
import com.cleartax.superhero.dto.SuperheroRequestBody;
import com.cleartax.superhero.repository.SuperheroRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    public SuperheroService(SuperheroRepository superheroRepository){
        this.superheroRepository = superheroRepository;
    }

    public Superhero getSuperhero(String name, String universe){
        if(null != name){
            return getByName(name);
        } else{
            throw new RuntimeException("Not found");
        }
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
        superhero.setName(requestBody.getName());
        superhero.setPower(requestBody.getPower());
        superhero.setUniverse(requestBody.getUniverse());

        return superheroRepository.save(superhero);
    }

    public Superhero updateSuperhero(String id, Superhero updatedSuperhero) {
        // Find the user by ID
        Superhero existingSuperhero = superheroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

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
