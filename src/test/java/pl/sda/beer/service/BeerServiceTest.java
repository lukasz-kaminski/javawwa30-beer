package pl.sda.beer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.beer.IllegalNameException;
import pl.sda.beer.domain.Beer;
import pl.sda.beer.domain.Type;
import pl.sda.beer.repository.BeerRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
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

    @ParameterizedTest
    @ValueSource(strings = {"piwo1", "piwo2", "piwo3"})
    void shouldCallRepo(String beerName){
        //given

        //when
        beerService.create(new Beer(Type.IPA, beerName));

        //then
        verify(repository).save(new Beer(Type.IPA, beerName));
    }

    @Test
    void shouldNotSaveBeerWithNameSameAsType() {
        //given

        //expect
        assertThrows(
                IllegalNameException.class,
                () -> beerService.create(new Beer(Type.IPA, "ipa"))
        );

//        try {
//            beerService.create(new Beer(Type.IPA, "Ipa"));
//            fail();
//        } catch (IllegalNameException e) {
//
//        }
    }

    @Test
    void shouldFindBeerBeforeSave() {
        //given

        //when
        beerService.create(new Beer(Type.IPA, "BRO"));
        //then
        InOrder inOrder= Mockito.inOrder(repository);
        inOrder.verify(repository).findBeerByName("BRO");
        inOrder.verify(repository).save(new Beer(Type.IPA, "BRO"));
    }




}