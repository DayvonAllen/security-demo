/*
 *  Copyright 2020 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.*;
import guru.sfg.brewery.domain.security.PermissionsEntity;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.UserEntity;
import guru.sfg.brewery.repositories.*;
import guru.sfg.brewery.repositories.security.AuthorityRepo;
import guru.sfg.brewery.repositories.security.RoleRepo;
import guru.sfg.brewery.repositories.security.UserRepo;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Created by jt on 2019-01-26.
 */
@Component
@Transactional
public class DefaultBreweryLoader implements CommandLineRunner {

    public static String tastingRoom = "Tasting Room";
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
    private static Integer count = 0;
    private static String breweryName = "Cage Brewery ";

    private final BreweryRepository breweryRepository;
    private final BeerRepository beerRepository;
    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final RoleRepo roleRepo;
    private final MyBeerOrderRepo myBeerOrderRepo;

    public DefaultBreweryLoader(BreweryRepository breweryRepository, BeerRepository beerRepository, BeerInventoryRepository beerInventoryRepository, BeerOrderRepository beerOrderRepository, CustomerRepository customerRepository, UserRepo userRepo, AuthorityRepo authorityRepo, RoleRepo roleRepo, MyBeerOrderRepo myBeerOrderRepo) {
        this.breweryRepository = breweryRepository;
        this.beerRepository = beerRepository;
        this.beerInventoryRepository = beerInventoryRepository;
        this.beerOrderRepository = beerOrderRepository;
        this.customerRepository = customerRepository;
        this.userRepo = userRepo;
        this.authorityRepo = authorityRepo;
        this.roleRepo = roleRepo;
        this.myBeerOrderRepo = myBeerOrderRepo;
    }

    @Override
    public void run(String... args) {
        loadBeerStuff("Blue Moon", "Asahi", breweryName + ++count, BeerStyleEnum.WHEAT, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        loadBeerStuff("Budlight", "Modelo", breweryName + ++count, BeerStyleEnum.ALE, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        loadBeerStuff("Sapporo", "Miller Lite",breweryName + ++count, BeerStyleEnum.LAGER, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        loadBeerStuff("Corona", "Guinness", breweryName + ++count, BeerStyleEnum.PALE_ALE, UUID.randomUUID().toString(), UUID.randomUUID().toString());

        //beer
        PermissionsEntity readAuth = authorityRepo.save(new PermissionsEntity("beer.read"));
        PermissionsEntity createAuth = authorityRepo.save(new PermissionsEntity("beer.create"));
        PermissionsEntity updateAuth = authorityRepo.save(new PermissionsEntity("beer.update"));
        PermissionsEntity deleteAuth = authorityRepo.save(new PermissionsEntity("beer.delete"));

        PermissionsEntity readOrder = authorityRepo.save(new PermissionsEntity("order.read"));
        PermissionsEntity createOrder = authorityRepo.save(new PermissionsEntity("order.create"));
        PermissionsEntity updateOrder = authorityRepo.save(new PermissionsEntity("order.update"));
        PermissionsEntity deleteOrder = authorityRepo.save(new PermissionsEntity("order.delete"));

        PermissionsEntity customerReadOrder = authorityRepo.save(new PermissionsEntity("customer.order.read"));
        PermissionsEntity customerCreateOrder = authorityRepo.save(new PermissionsEntity("customer.order.create"));
        PermissionsEntity customerUpdateOrder = authorityRepo.save(new PermissionsEntity("customer.order.update"));
        PermissionsEntity customerDeleteOrder = authorityRepo.save(new PermissionsEntity("customer.order.delete"));

        Role adminRole = roleRepo.save(new Role("ADMIN",Set.of(readAuth,
                createAuth,updateAuth,deleteAuth, readOrder, createOrder, updateOrder, deleteOrder, customerReadOrder,
                customerCreateOrder, customerUpdateOrder, customerDeleteOrder)));

        Role customerRole = roleRepo.save(new Role("CUSTOMER",Set.of(readAuth, customerCreateOrder, customerReadOrder, customerUpdateOrder, customerDeleteOrder)));
        Role userRole = roleRepo.save(new Role("USER",Set.of(readAuth)));

        UserEntity admin = new UserEntity("jdoe", "jdoe@gmail.com", bCryptPasswordEncoder.encode("password"));
        UserEntity customer = new UserEntity("jane", "janedoe@gmail.com", bCryptPasswordEncoder.encode("p4ssw0rd"));
        UserEntity user = new UserEntity("ballen", "ballen@gmail.com", bCryptPasswordEncoder.encode("barry"));
        admin.getRoles().add(adminRole);
        customer.getRoles().add(customerRole);
        user.getRoles().add(userRole);
        admin = userRepo.save(admin);
        customer = userRepo.save(customer);
        user = userRepo.save(user);

        loadCustomerData(tastingRoom + " XL", admin);
        loadCustomerData(tastingRoom + " L", customer);
        loadCustomerData(tastingRoom + " M", customer);
        loadCustomerData(tastingRoom + " S", user);

        List<Customer> customers = customerRepository.findAll();

        customers.forEach((c) -> {
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 1", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 2", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 3", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 4", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 5", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 6", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 7", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 8", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 9", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 10", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 11", c.getId()));
            myBeerOrderRepo.save(new MyBeerOrder(c.getCustomerName() + " beer 12", c.getId()));
        });


    }

    private void loadCustomerData(String name, UserEntity userEntity) {
        Customer tastingRoom = new Customer(name, UUID.randomUUID());
        tastingRoom.getUsers().add(userEntity);
        userEntity.setCustomer(tastingRoom);
        Set<BeerOrderLine> beerOrderLines = new HashSet<>();
        Customer customer = customerRepository.save(tastingRoom);
        BeerOrder beerOrder = new BeerOrder(customer,beerOrderLines, OrderStatusEnum.NEW);
        beerRepository.findAll().forEach(beer -> {
            beerOrderLines.add(new BeerOrderLine(beer, 2));
            beerOrderRepository.save(beerOrder);
        });
    }

    private void loadBreweryData(String name) {
            breweryRepository.save(new Brewery(name));
    }

    private void loadBeerStuff(String beerName, String beerName2,  String breweryName, BeerStyleEnum beerStyleEnum, String upc1, String upc2){
        loadBreweryData(breweryName);
        Beer mangoBobs = new Beer(beerName, beerStyleEnum, upc1, 12, 200, BigDecimal.valueOf(10));

        beerRepository.save(mangoBobs);
        beerInventoryRepository.save(new BeerInventory(mangoBobs, 500));
        Beer galaxyCat = new Beer(beerName2, beerStyleEnum, upc2, 12, 200, BigDecimal.valueOf(12));
        beerRepository.save(galaxyCat);
        beerInventoryRepository.save(new BeerInventory(galaxyCat, 200));
    }
}
