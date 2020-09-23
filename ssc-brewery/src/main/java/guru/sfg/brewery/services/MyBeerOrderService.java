package guru.sfg.brewery.services;

import guru.sfg.brewery.domain.MyBeerOrder;
import guru.sfg.brewery.web.model.MyBeerOrderCustomerPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface MyBeerOrderService {
    MyBeerOrderCustomerPagedList getAllByCustomerId(UUID id, PageRequest page);
    MyBeerOrder getById(Long id);
    MyBeerOrder createOrder(MyBeerOrder myBeerOrder);
    MyBeerOrder updateOrder(Long id, MyBeerOrder myBeerOrder);
    void deleteOrder(Long id);
}
