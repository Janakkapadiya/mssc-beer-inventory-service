package guru.sfg.beer.inventory.service.service;

import guru.sfg.beer.inventory.service.web.model.BeerOrderDto;

public interface AllocationService {
    Boolean allocateOrder(BeerOrderDto beerOrderDto);


    void deallocateOrder(BeerOrderDto beerOrderDto);
}
