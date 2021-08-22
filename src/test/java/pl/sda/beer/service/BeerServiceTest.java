package pl.sda.beer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.beer.IllegalNameException;
import pl.sda.beer.domain.Beer;
import pl.sda.beer.domain.BeerType;
import pl.sda.beer.repository.BeerRepository;

import java.util.stream.Stream;

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
        Beer testBeer = new Beer(BeerType.IPA, "piwo");
        when(repository.save(testBeer)).thenReturn(new Beer(1L, BeerType.IPA, "piwo"));

        //when
        Beer piwo = beerService.create(new Beer(BeerType.IPA, "piwo"));
        //then
        assertNotNull(piwo.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"piwo1", "piwo2", "piwo3"})
    void shouldCallRepo(String beerName) {
        //given

        //when
        beerService.create(new Beer(BeerType.IPA, beerName));

        //then
        verify(repository).save(new Beer(BeerType.IPA, beerName));
    }

    static Stream<Beer> beerGenerator() {
        return Stream.of(
                new Beer(BeerType.IPA, "piwo1"),
                new Beer(BeerType.IPA, "piwo2"),
                new Beer(BeerType.IPA, "piwo3"),
                new Beer(BeerType.IPA, "piwo4")
        );
    }

    @ParameterizedTest
    @MethodSource("beerGenerator")
    void shouldCallRepo(Beer beer) {
        //given

        //when
        beerService.create(beer);

        //then
        verify(repository).save(beer);
    }

    @ParameterizedTest
    @ArgumentsSource(BeerProvider.class)
    void shouldCallRepoWithArgumentsSource(Beer beer) {
        //given

        //when
        beerService.create(beer);

        //then
        verify(repository).save(beer);
    }

    @ParameterizedTest
    @CsvSource(value = {"IPA, zywiec", "APA, Krol", "LAGER, wojak"})
    void shouldCallRepoWithArgumentsSource(String typ, String name) {
        //given
        Beer beer = new Beer(BeerType.valueOf(typ), name);
        //when
        beerService.create(beer);

        //then
        verify(repository).save(beer);
    }

    @ParameterizedTest
    @EnumSource(value = BeerType.class, names = {".*A.*"}, mode = EnumSource.Mode.MATCH_ANY)
    void shouldNotSaveBeerWithNameSameAsType(BeerType type) {
        //given

        //expect
        assertThrows(
                IllegalNameException.class,
                () -> beerService.create(new Beer(type, type.name()))
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
        beerService.create(new Beer(BeerType.IPA, "BRO"));
        //then
        InOrder inOrder = Mockito.inOrder(repository);
        inOrder.verify(repository).findBeerByName("BRO");
        inOrder.verify(repository).save(new Beer(BeerType.IPA, "BRO"));
    }


}