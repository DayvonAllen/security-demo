package guru.sfg.brewery.web.model;

import guru.sfg.brewery.domain.MyBeerOrder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MyBeerOrderCustomerPagedList extends PageImpl<MyBeerOrder> {
    public MyBeerOrderCustomerPagedList(List<MyBeerOrder> content) {
        super(content);
    }
    public MyBeerOrderCustomerPagedList(List<MyBeerOrder> content, Pageable pageable, Long total) {
        super(content, pageable, total);
    }




}
