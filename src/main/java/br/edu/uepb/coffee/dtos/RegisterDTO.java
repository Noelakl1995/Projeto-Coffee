package br.edu.uepb.coffee.dtos;

import br.edu.uepb.coffee.models.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotNull String email,@NotNull String password, @NotNull UserRole userRole){
    
}
