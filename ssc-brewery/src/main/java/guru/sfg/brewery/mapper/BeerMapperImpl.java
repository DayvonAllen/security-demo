package guru.sfg.brewery.mapper;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.web.model.BeerDto;
import org.springframework.stereotype.Component;

@Component
public class BeerMapperImpl implements BeerMapper {
    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        return new BeerDto(beer.getId(),
                beer.getVersion(),
                beer.getCreatedDate(),
                beer.getLastModifiedDate(),
                beer.getBeerName(),
                beer.getBeerStyle(),
                beer.getUpc(),
                beer.getMinOnHand(),beer.getPrice());
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return null;
    }
}
