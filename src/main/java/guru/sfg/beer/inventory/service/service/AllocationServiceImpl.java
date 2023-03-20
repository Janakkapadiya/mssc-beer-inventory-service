package guru.sfg.beer.inventory.service.service;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.beer.inventory.service.web.model.BeerOrderDto;
import guru.sfg.beer.inventory.service.web.model.BeerOrderLineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class AllocationServiceImpl implements AllocationService {
    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public Boolean allocateOrder(BeerOrderDto beerOrderDto) {

        log.debug("Allocating OrderId: " + beerOrderDto.getId());

        AtomicInteger totalOrder = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(beerOrderLine -> {
            if ((beerOrderLine.getOrderQuantity() == null ? 0 : beerOrderLine.getOrderQuantity())
                    - (beerOrderLine.getQuantityAllocated() == null ? 0 : beerOrderLine.getQuantityAllocated()) > 0) {
                allocatedBeerOrderLine(beerOrderLine);
            }
            totalOrder.set(totalOrder.get() + beerOrderLine.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (beerOrderLine.getQuantityAllocated() == null ? 0
                    : beerOrderLine.getQuantityAllocated()));
        });

        log.debug("Total Ordered: " + totalOrder.get() + "Total Allocated: " + totalAllocated.get());

        return totalAllocated.get() == totalOrder.get();
    }

    private void allocatedBeerOrderLine(BeerOrderLineDto beerOrderLineDto) {
        List<BeerInventory> beerInventories = beerInventoryRepository.findAllByUpc(beerOrderLineDto.getUpc());

        beerInventories.forEach(beerInventory -> {
            int quantityOnHand = (beerInventory.getQuantityOnHand() == null) ? 0 : beerInventory.getQuantityOnHand();
            int orderQuantity = beerOrderLineDto.getOrderQuantity() == null ? 0 : beerOrderLineDto.getOrderQuantity();
            int allocatedQuantity = beerOrderLineDto.getQuantityAllocated() == null ? 0 : beerOrderLineDto.getQuantityAllocated();
            int quantityToAllocate = orderQuantity - allocatedQuantity;

            if(quantityOnHand >= quantityToAllocate)
            {
                quantityOnHand -= quantityToAllocate;
                beerOrderLineDto.setQuantityAllocated(orderQuantity);
                beerInventory.setQuantityOnHand(quantityOnHand);

                beerInventoryRepository.save(beerInventory);
            }else if(quantityOnHand > 0){
               beerOrderLineDto.setQuantityAllocated(allocatedQuantity + quantityOnHand);
               beerInventory.setQuantityOnHand(0);

               beerInventoryRepository.delete(beerInventory);
            }
        });
    }
}
