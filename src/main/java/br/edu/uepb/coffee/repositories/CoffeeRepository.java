package br.edu.uepb.coffee.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.uepb.coffee.models.Coffee;



public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    Optional<Coffee> findByName(String name);
    
}
