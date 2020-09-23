package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.Customer;
import guru.sfg.brewery.domain.MyBeerOrder;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.MyBeerOrderService;
import guru.sfg.brewery.web.model.MyBeerOrderCustomerPagedList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders")
public class BeerOrderController {

    private final MyBeerOrderService myBeerOrderService;
    private final CustomerRepository customerRepository;
    private static final Integer DEFAULT_PAGE_NUMBER=0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    public BeerOrderController(MyBeerOrderService myBeerOrderService, CustomerRepository customerRepository) {
        this.myBeerOrderService = myBeerOrderService;
        this.customerRepository = customerRepository;
    }

    @GetMapping(value = "/customer/{id}")
    public MyBeerOrderCustomerPagedList getAllBeerOrdersByCustomer(
            @PathVariable UUID id,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if(pageNumber <= 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if(pageSize <= 0){
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return myBeerOrderService.getAllByCustomerId(id, PageRequest.of(pageNumber, pageSize, Sort.by("id").descending()));
    }
    @GetMapping(value = "customers/list")
    public List<Customer> getAllCustomersTest(){
        System.out.println(SecurityContextHolder.getContext());
        return customerRepository.findAll();
    }

    @GetMapping(value = "{id}")
    public MyBeerOrder getBeerById(@PathVariable Long id){
        return myBeerOrderService.getById(id);
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public MyBeerOrder createBeerOrder(@RequestBody MyBeerOrder myBeerOrder){
        return myBeerOrderService.createOrder(myBeerOrder);
    }

    @PutMapping(value = "{id}")
    public MyBeerOrder updateBeerOrder(@PathVariable Long id, @RequestBody MyBeerOrder myBeerOrder){
        return myBeerOrderService.updateOrder(id, myBeerOrder);
    }

    @DeleteMapping(value = "")
    public void deleteBeer(Long id){
        myBeerOrderService.deleteOrder(id);
    }
}
