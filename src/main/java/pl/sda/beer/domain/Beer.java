package pl.sda.beer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Beer {
    @Id
    private Long id;
    private BeerType type;
    private String name;

    public Beer(BeerType type, String name) {
        this.type = type;
        this.name = name;
    }
}
