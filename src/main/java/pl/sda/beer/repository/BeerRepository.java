package pl.sda.beer.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.beer.domain.Beer;

public interface BeerRepository extends CrudRepository<Beer, Long> {

}
