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
package guru.sfg.brewery.domain;

import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jt on 2019-01-26.
 */

@Entity
public class Beer extends BaseEntity {

    private String beerName;
    private BeerStyleEnum beerStyle;

    @Column(unique = true)
    private String upc;

    /**
     * Min on hand qty - used to trigger brew
     */
    private Integer minOnHand;
    private Integer quantityToBrew;
    private BigDecimal price;

    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<BeerInventory> beerInventory = new HashSet<>();

    public Beer() {
    }

    public Beer(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate) {
        super(id, version, createdDate, lastModifiedDate);
    }

    public Beer(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, String beerName,
                BeerStyleEnum beerStyle, String upc, Integer minOnHand,
                Integer quantityToBrew, BigDecimal price, Set<BeerInventory> beerInventory) {
        super(id, version, createdDate, lastModifiedDate);
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.minOnHand = minOnHand;
        this.quantityToBrew = quantityToBrew;
        this.price = price;
        this.beerInventory = beerInventory;
    }

    public Beer(String beerName, BeerStyleEnum beerStyle, String upc, Integer minOnHand, Integer quantityToBrew) {
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.minOnHand = minOnHand;
        this.quantityToBrew = quantityToBrew;
    }

    public Beer(String beerName, BeerStyleEnum beerStyle, String upc, Integer minOnHand, Integer quantityToBrew, BigDecimal price) {
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.minOnHand = minOnHand;
        this.quantityToBrew = quantityToBrew;
        this.price = price;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public BeerStyleEnum getBeerStyle() {
        return beerStyle;
    }

    public void setBeerStyle(BeerStyleEnum beerStyle) {
        this.beerStyle = beerStyle;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Integer getMinOnHand() {
        return minOnHand;
    }

    public void setMinOnHand(Integer minOnHand) {
        this.minOnHand = minOnHand;
    }

    public Integer getQuantityToBrew() {
        return quantityToBrew;
    }

    public void setQuantityToBrew(Integer quantityToBrew) {
        this.quantityToBrew = quantityToBrew;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<BeerInventory> getBeerInventory() {
        return beerInventory;
    }

    public void setBeerInventory(Set<BeerInventory> beerInventory) {
        this.beerInventory = beerInventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return Objects.equals(beerName, beer.beerName) &&
                beerStyle == beer.beerStyle &&
                Objects.equals(upc, beer.upc) &&
                Objects.equals(minOnHand, beer.minOnHand) &&
                Objects.equals(quantityToBrew, beer.quantityToBrew);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beerName, beerStyle, upc, minOnHand, quantityToBrew);
    }
}
