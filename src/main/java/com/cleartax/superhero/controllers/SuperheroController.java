package com.cleartax.superhero.controllers;

import com.cleartax.superhero.dto.Superhero;
import com.cleartax.superhero.dto.SuperheroRequestBody;
import com.cleartax.superhero.services.SuperheroService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SuperheroController {

    private SuperheroService superheroService;

    @Autowired
    public SuperheroController(SuperheroService superheroService){
        this.superheroService = superheroService;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "username", defaultValue = "World") String username) {
        return String.format("Hello %s!", username);
    }

    @GetMapping("/superhero")
    public Superhero getSuperhero(@RequestParam(value = "name", defaultValue = "Batman") String name,
                                  @RequestParam(value = "universe", defaultValue = "DC") String universe){
        return superheroService.getSuperhero(name, universe);
    }

    @PostMapping("/superhero")
    public Superhero persistSuperhero(@RequestBody SuperheroRequestBody superhero){
        return superheroService.persistSuperhero(superhero);
    }

    @PutMapping("/{id}")
    public Superhero updateSuperhero(@PathVariable String id, @RequestBody Superhero superhero) {
        return superheroService.updateSuperhero(id, superhero);
    }

    @DeleteMapping("/{id}")
    public void deleteSuperhero(@PathVariable String id){
        superheroService.deleteSuperhero(id);
    }
}
