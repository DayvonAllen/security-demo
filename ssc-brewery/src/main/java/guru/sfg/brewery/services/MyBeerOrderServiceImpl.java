package guru.sfg.brewery.services;

import guru.sfg.brewery.domain.MyBeerOrder;
import guru.sfg.brewery.repositories.MyBeerOrderRepo;
import guru.sfg.brewery.web.model.MyBeerOrderCustomerPagedList;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class MyBeerOrderServiceImpl implements MyBeerOrderService {

    private final MyBeerOrderRepo myBeerOrderRepo;

    public MyBeerOrderServiceImpl(MyBeerOrderRepo myBeerOrderRepo) {
        this.myBeerOrderRepo = myBeerOrderRepo;
    }

    @Override
    public MyBeerOrderCustomerPagedList getAllByCustomerId(UUID id, PageRequest pageRequest) {
        return new MyBeerOrderCustomerPagedList(myBeerOrderRepo.findAllByCustomerId(id, pageRequest));
    }

    @Override
    public MyBeerOrder getById(Long id) {
        return myBeerOrderRepo.findById(id).get();
    }

    @Override
    public MyBeerOrder createOrder(MyBeerOrder myBeerOrder) {
        return myBeerOrderRepo.save(myBeerOrder);
    }

    @Override
    public MyBeerOrder updateOrder(Long id, MyBeerOrder myBeerOrder) {
        MyBeerOrder myBeerOrder1 = myBeerOrder;
        myBeerOrder1.setId(id);
        return myBeerOrderRepo.save(myBeerOrder1);
    }

    @Override
    public void deleteOrder(Long id) {
        myBeerOrderRepo.deleteById(id);
    }
}
