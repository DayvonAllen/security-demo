package guru.sfg.brewery.repositories;

import guru.sfg.brewery.domain.MyBeerOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MyBeerOrderRepo extends JpaRepository<MyBeerOrder, Long> {
    List<MyBeerOrder> findAllByCustomerId(UUID id, Pageable pageable);
}
