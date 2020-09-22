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
import guru.sfg.brewery.domain.security.Authority;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * Created by jt on 2019-01-26.
 */
@Component
@Transactional
public class DefaultBreweryLoader implements CommandLineRunner {

    public static final String TASTING_ROOM = "Tasting Room";
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BreweryRepository breweryRepository;
    private final BeerRepository beerRepository;
    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final RoleRepo roleRepo;


    public DefaultBreweryLoader(BreweryRepository breweryRepository, BeerRepository beerRepository, BeerInventoryRepository beerInventoryRepository, BeerOrderRepository beerOrderRepository, CustomerRepository customerRepository, UserRepo userRepo, AuthorityRepo authorityRepo, RoleRepo roleRepo) {
        this.breweryRepository = breweryRepository;
        this.beerRepository = beerRepository;
        this.beerInventoryRepository = beerInventoryRepository;
        this.beerOrderRepository = beerOrderRepository;
        this.customerRepository = customerRepository;
        this.userRepo = userRepo;
        this.authorityRepo = authorityRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public void run(String... args) {
        loadBreweryData();
        loadCustomerData();
        Authority readAuth = authorityRepo.save(new Authority("beer.read"));
        Authority createAuth = authorityRepo.save(new Authority("beer.create"));
        Authority updateAuth = authorityRepo.save(new Authority("beer.update"));
        Authority deleteAuth = authorityRepo.save(new Authority("beer.delete"));

        Role adminRole = roleRepo.save(new Role("ADMIN",Set.of(readAuth,createAuth,updateAuth,deleteAuth)));
        Role customerRole = roleRepo.save(new Role("CUSTOMER",Set.of(readAuth)));
        Role userRole = roleRepo.save(new Role("USER",Set.of(readAuth)));

        UserEntity admin = new UserEntity("jdoe", "jdoe@gmail.com", bCryptPasswordEncoder.encode("password"));
        UserEntity customer = new UserEntity("jane", "janedoe@gmail.com", bCryptPasswordEncoder.encode("p4ssw0rd"));
        UserEntity user = new UserEntity("ballen", "ballen@gmail.com", bCryptPasswordEncoder.encode("barry"));
        admin.getRoles().add(adminRole);
        customer.getRoles().add(customerRole);
        user.getRoles().add(userRole);
        userRepo.save(admin);
        userRepo.save(customer);
        userRepo.save(user);
    }

    private void loadCustomerData() {
        Customer tastingRoom = new Customer(TASTING_ROOM, UUID.randomUUID());
        Set<BeerOrderLine> beerOrderLines = new HashSet<>();
        customerRepository.save(tastingRoom);
        BeerOrder beerOrder = new BeerOrder(tastingRoom,beerOrderLines, OrderStatusEnum.NEW);
        beerRepository.findAll().forEach(beer -> {
            beerOrderLines.add(new BeerOrderLine(beer, 2));
            beerOrderRepository.save(beerOrder);
        });
    }

    private void loadBreweryData() {
        if (breweryRepository.count() == 0) {
            breweryRepository.save(new Brewery("Cage Brewery"));

            Beer mangoBobs = new Beer("Mango Bobs", BeerStyleEnum.IPA, BEER_1_UPC, 12, 200, BigDecimal.valueOf(10));

            beerRepository.save(mangoBobs);
            beerInventoryRepository.save(new BeerInventory(mangoBobs, 500));
            Beer galaxyCat = new Beer("Galaxy Cat", BeerStyleEnum.PALE_ALE, BEER_2_UPC, 12, 200, BigDecimal.valueOf(12));
            beerRepository.save(galaxyCat);
            beerInventoryRepository.save(new BeerInventory(galaxyCat, 200));

        }
    }
}
