package br.edu.uepb.coffee.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CoffeeWithDiscountDTO {
    private String name;
    private double discount;
    
}
