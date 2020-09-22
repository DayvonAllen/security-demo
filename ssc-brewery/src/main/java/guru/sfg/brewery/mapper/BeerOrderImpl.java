package guru.sfg.brewery.mapper;

import guru.sfg.brewery.domain.BeerOrder;
import guru.sfg.brewery.web.model.BeerOrderDto;
import org.springframework.stereotype.Component;

@Component
public class BeerOrderImpl implements BeerOrderMapper {
    @Override
    public BeerOrderDto beerOrderToBeerOrderDto(BeerOrder beerOrder) {
        return null;
    }

    @Override
    public BeerOrder beerOrderDtoToBeerOrder(BeerOrderDto beerOrderDto) {
        return null;
    }
}
