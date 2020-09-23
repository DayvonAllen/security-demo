package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BeerOrderAuthenticationManager {

    public Boolean customerIdMatches(Authentication authentication, UUID customerId){
        //principal is an object therefore we have to cast to a user entity object, if exception happens here, let it fail hard.
        UserEntity authenticatedUser = (UserEntity) authentication.getPrincipal();

        System.out.println("Auth customer id:" + authenticatedUser.getCustomer().getId() + " Customer Id to compare:" + customerId);
        return authenticatedUser.getCustomer().getId().equals(customerId);
    }
}
