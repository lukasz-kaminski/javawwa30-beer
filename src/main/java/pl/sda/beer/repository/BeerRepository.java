package pl.sda.beer.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.beer.domain.Beer;

import java.util.Optional;

public interface BeerRepository extends CrudRepository<Beer, Long> {

    public Optional<Beer> findBeerByName (String name);
}
