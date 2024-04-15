package br.edu.uepb.coffee.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "coffees")
public class Coffee {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO )
    private Long id;

    @Column(name = "name")
    private String name;

    public Coffee(String name){
        this.name = name;

    }

    public double getPrice() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrice'");
    }

    public void setPrice(double d) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPrice'");
    }
}