package guru.sfg.beer.inventory.service.web.model;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.service.AllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class DeAllocationListener {
//
//    private final AllocationService allocationService;
//
//    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
//    public void listen(DeallocateOrderRequest request){
//        allocationService.deallocateOrder(request.getBeerOrderDto());
//    }
//}