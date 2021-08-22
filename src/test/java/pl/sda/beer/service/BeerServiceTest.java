package pl.sda.beer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.beer.IllegalNameException;
import pl.sda.beer.domain.Beer;
import pl.sda.beer.domain.Type;
import pl.sda.beer.repository.BeerRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void shouldCallRepo(){
        //given

        //when
        beerService.create(new Beer(Type.IPA, "piwo"));

        //then
        verify(repository).save(any());
        verifyNoMoreInteractions(repository);
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