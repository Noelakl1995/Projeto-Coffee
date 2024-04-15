package br.edu.uepb.coffee.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponseErrorDTO {
    private String error;
}
