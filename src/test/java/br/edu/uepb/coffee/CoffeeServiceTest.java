package br.edu.uepb.coffee;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.uepb.coffee.exceptions.CoffeeNotFoundException;
import br.edu.uepb.coffee.exceptions.ExistingCoffeeSameNameException;
import br.edu.uepb.coffee.models.Coffee;
import br.edu.uepb.coffee.repositories.CoffeeRepository;
import br.edu.uepb.coffee.services.CoffeeService;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;




public class CoffeeServiceTest {
    
    @Mock
    private CoffeeRepository coffeeRepository;

    @InjectMocks
    private CoffeeService coffeeService;
    
    @BeforeEach
    @SuppressWarnings("deprecation")
    public void InitMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void TearDown(){

    }

    @Test
    void whenNewValidCoffeeInformedThenItShouldBeCreated() throws ExistingCoffeeSameNameException{
        Coffee expectedSaveCoffee = new Coffee("Capuccino");

        when(coffeeRepository.save(expectedSaveCoffee)).thenReturn(expectedSaveCoffee);

        

        Coffee createdCoffee = coffeeService.createCoffee(expectedSaveCoffee);
        assertThat(createdCoffee.getId(), is(equalTo(expectedSaveCoffee.getId())));
        assertThat(createdCoffee.getName(), is(equalTo(expectedSaveCoffee.getName())));
        
        

    }

    @Test
    void whenAlreadyRegisteredCoffeeInformedThenAnExceptionShouldBeThrown() {
        Coffee expectedSaveCoffee = new Coffee("Margiella");
        Coffee duplicateCoffee = expectedSaveCoffee;

        when(coffeeRepository.findByName(expectedSaveCoffee.getName())).thenReturn(Optional.of(duplicateCoffee));

        assertThrows(ExistingCoffeeSameNameException.class,() -> coffeeService.createCoffee(duplicateCoffee));
    }

    @Test
    void whenListCoffeeIsCalledThenReturnAnEmptyListOfCoffee(){
        
        when(coffeeRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        List<Coffee> foundListCoffeesDTO = coffeeService.listAllCoffees();
        assertThat(foundListCoffeesDTO, is(empty()));
    }


    @Test
    void whenRegisteredMoreCoffeesListIsGivenThenReturnAListOfCoffees(){
        Coffee coffee1 =  new Coffee("Capuccino");
        Coffee coffee2 = new Coffee("Gourmet");
        List<Coffee> expectedList = List.of(coffee1, coffee2);

        when(coffeeRepository.findAll()).thenReturn(expectedList);

        List<Coffee> foundListCoffees = coffeeService.listAllCoffees();
        assertThat(foundListCoffees, not(empty()));
        assertThat(foundListCoffees.size(), is(equalTo(expectedList.size())));

    }

    @Test
    void whenRegisteredCoffeeNameIsGivenThenReturnACoffee() throws CoffeeNotFoundException{
        Coffee expectedFoundCoffee = new Coffee("Capuccino");

        when(coffeeRepository.findByName(expectedFoundCoffee.getName())).thenReturn(Optional.of(expectedFoundCoffee));
        
        Coffee foundCoffee = coffeeService.findByName(expectedFoundCoffee.getName());
    }


    @Test
    void whenNotRegisteredCoffeeNameIsGivenThenThrowAnException() {
        Coffee expectedFoundCoffee = new Coffee("Extraforte");
        when(coffeeRepository.findByName(expectedFoundCoffee.getName())).thenReturn(Optional.empty());
        assertThrows(CoffeeNotFoundException.class, () -> coffeeService.findByName(expectedFoundCoffee.getName()));
    }

    @Test
    void whenValidCoffeeIsGivenThenReturnAnUpdatedCoffee() throws CoffeeNotFoundException, ExistingCoffeeSameNameException {
        Coffee expectedFoundCoffee = new Coffee("gourmet");
        Coffee expectedUpdateCoffee = new Coffee("capucinno");

        when(coffeeRepository.findById(expectedFoundCoffee.getId())).thenReturn(Optional.of(expectedFoundCoffee));
        when(coffeeRepository.save(expectedUpdateCoffee)).thenReturn(expectedFoundCoffee);

        Coffee updateCoffee = coffeeService.updateCoffee(expectedFoundCoffee.getId(), expectedUpdateCoffee);

        assertThat(updateCoffee.getId(), is(equalTo(expectedUpdateCoffee.getId())));

    }


    @Test
    void whenInvalidCoffeeIsGivenThenThrowAnException() {
        Coffee expectedFoundCoffee = new Coffee("Capuccino");

        when(coffeeRepository.findById(expectedFoundCoffee.getId())).thenReturn(Optional.empty());

        assertThrows(CoffeeNotFoundException.class, () -> coffeeService.updateCoffee(expectedFoundCoffee.getId(), expectedFoundCoffee));
    }

    
}
