package pl.sda.beer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.beer.domain.Beer;
import pl.sda.beer.domain.Type;
import pl.sda.beer.repository.BeerRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeerServiceTest {

    @Mock
    BeerRepository repository;

    @InjectMocks
    BeerService beerService;

    @Test
    void should() {
        //given
        Beer testBeer = new Beer(Type.IPA, "piwo");
        when(repository.save(testBeer)).thenReturn(new Beer(1L, Type.IPA, "piwo"));

        //when
        Beer piwo = beerService.create(new Beer(Type.IPA, "piwo"));
        //then
        assertNotNull(piwo.getId());
    }


}