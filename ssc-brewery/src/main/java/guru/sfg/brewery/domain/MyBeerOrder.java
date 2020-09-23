package guru.sfg.brewery.domain;

import org.springframework.data.domain.PageImpl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class MyBeerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String beer;
    private UUID customerId;

    public MyBeerOrder() {
    }

    public MyBeerOrder(Long id, String beer) {
        this.id = id;
        this.beer = beer;
    }

    public MyBeerOrder(String beer, UUID customerId) {
        this.beer = beer;
        this.customerId = customerId;
    }

    public MyBeerOrder(Long id, String beer, UUID customerId) {
        this.id = id;
        this.beer = beer;
        this.customerId = customerId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeer() {
        return beer;
    }

    public void setBeer(String beer) {
        this.beer = beer;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyBeerOrder that = (MyBeerOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(beer, that.beer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beer);
    }
}
