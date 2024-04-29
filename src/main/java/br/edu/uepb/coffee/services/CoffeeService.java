package br.edu.uepb.coffee.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.edu.uepb.coffee.exceptions.CoffeeNotFoundException;
import br.edu.uepb.coffee.exceptions.ExistingCoffeeSameNameException;
import br.edu.uepb.coffee.models.Coffee;
import br.edu.uepb.coffee.repositories.CoffeeRepository;

@Service
public class CoffeeService {
    
    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee updateDiscountCoffee(Coffee coffee, double discount) throws NotFoundException {
        if (!coffeeRepository.findByName(coffee.getName()).isPresent())
            throw new NotFoundException();
            
        Coffee coffeeEntity = coffeeRepository.findByName(coffee.getName()).get();
        
        double discountFinal = coffeeEntity.getPrice() * discount;
        coffeeEntity.setPrice(coffeeEntity.getPrice() - discountFinal);
        
        return coffeeRepository.save(coffeeEntity);
    }

    public Coffee createCoffee(Coffee coffee) throws ExistingCoffeeSameNameException {
        if (coffeeRepository.findByName(coffee.getName()).isPresent())
            throw new ExistingCoffeeSameNameException("Já existe um café com esse nome!");
        return coffeeRepository.save(coffee);
    }

    public Coffee findByName(String name) throws CoffeeNotFoundException {
        Optional<Coffee> optionalCoffee = coffeeRepository.findByName(name);
        return optionalCoffee.orElseThrow(() -> new CoffeeNotFoundException(name));
    }
    

    public Coffee updateCoffee(Long id, Coffee coffee) {
        coffee.setId(id);
        return coffeeRepository.save(coffee);
    }

    public List<Coffee> listAllCoffees() {
        return coffeeRepository.findAll();
    }

    public  Coffee findById(Long id) throws NotFoundException {
        return coffeeRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public void deleteCoffee(Long id) {
        Coffee coffeeToDelete = coffeeRepository.findById(id).get();
        coffeeRepository.delete(coffeeToDelete);
    }

}
