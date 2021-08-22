package pl.sda.beer.service;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import pl.sda.beer.domain.Beer;
import pl.sda.beer.domain.Type;

import java.util.stream.Stream;

public class BeerProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(new Beer(Type.IPA, "piwerko")),
                Arguments.of(new Beer(Type.LAGER, "browar"))
        );
    }
}
