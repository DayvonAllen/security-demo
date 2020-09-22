package guru.sfg.brewery.mapper;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.web.model.BeerDto;

public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDto beerDto);
}
