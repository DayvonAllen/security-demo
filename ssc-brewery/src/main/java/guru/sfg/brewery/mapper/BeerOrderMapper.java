package guru.sfg.brewery.mapper;

import guru.sfg.brewery.domain.BeerOrder;
import guru.sfg.brewery.web.model.BeerOrderDto;

public interface BeerOrderMapper {
    BeerOrderDto beerOrderToBeerOrderDto(BeerOrder beerOrder);
    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDto beerOrderDto);
}
