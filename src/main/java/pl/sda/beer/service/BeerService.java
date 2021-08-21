package pl.sda.beer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.beer.IllegalNameException;
import pl.sda.beer.domain.Beer;
import pl.sda.beer.domain.Type;
import pl.sda.beer.repository.BeerRepository;

@Service
@RequiredArgsConstructor
public class BeerService {

    private final BeerRepository beerRepository;

    public Beer create(Type type, String name) {
        return create(new Beer(type, name));
    }

    public Beer create(Beer beer) {
        if(beer.getName().toLowerCase().contains(beer.getType().name().toLowerCase())) {
            throw new IllegalNameException();
        }
        return beerRepository.save(beer);
    }

}
