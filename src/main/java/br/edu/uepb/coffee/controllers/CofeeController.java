package br.edu.uepb.coffee.controllers;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.uepb.coffee.dtos.CoffeeDTO;
import br.edu.uepb.coffee.dtos.CoffeeWithDiscountDTO;
import br.edu.uepb.coffee.dtos.GenericResponseErrorDTO;
import br.edu.uepb.coffee.exceptions.ExistingCoffeeSameNameException;
import br.edu.uepb.coffee.mapper.CoffeeMapper;
import br.edu.uepb.coffee.models.Coffee;

import br.edu.uepb.coffee.services.CoffeeService;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(value ="/coffees", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class CofeeController {
  
    @Autowired
    private CoffeeMapper coffeeMapper;

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping
    @Operation(summary = "Busca a lista de todos os coffee")
    public ResponseEntity<?> getCoffees() {
        List<Coffee> coffees = coffeeService.listAllCoffees();
        return ResponseEntity.ok().body(coffees.stream().
        map(coffeeMapper::convertToCoffeeDTO)
        .collect(Collectors.toList()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCoffeeById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(coffeeService.findById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new GenericResponseErrorDTO(e.getMessage()));
        }
    }



    @PostMapping
    public ResponseEntity<?> createCoffee(@RequestBody CoffeeDTO coffeeDTO) {
        try {
            Coffee coffee = coffeeMapper.convertFromCoffeeDTO(coffeeDTO);
            return new ResponseEntity<>(coffeeService.createCoffee(coffee), HttpStatus.CREATED);
        } catch (ExistingCoffeeSameNameException e) {
            return ResponseEntity.badRequest().body(new GenericResponseErrorDTO(e.getMessage()));
        }
    }


    
    @PatchMapping
    public ResponseEntity<?> updateDiscountCoffee(@RequestBody CoffeeWithDiscountDTO coffeeDTO) {
        try {
            Coffee coffee = coffeeMapper.convertFromCoffeeWithDiscountDTO(coffeeDTO);
            Coffee coffeeUpdated = coffeeService.updateDiscountCoffee(coffee, coffeeDTO.getDiscount());
            return new ResponseEntity<>(coffeeMapper.convertToCoffeeDTO(coffeeUpdated), HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(new GenericResponseErrorDTO(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um coffee a partir do seu identificador")
    public CoffeeDTO updateCoffee(@PathVariable("id") Long id, @RequestBody Coffee coffee) {
        return coffeeMapper.convertToCoffeeDTO(coffeeService.updateCoffee(id, coffee));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um coffee a apartir do seu identificador")
    public void deleteCoffee(@PathVariable Long id) {
        coffeeService.deleteCoffee(id);
    }

}
    
