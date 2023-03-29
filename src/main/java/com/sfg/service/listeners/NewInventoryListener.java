package com.sfg.service.listeners;

import com.sfg.config.JmsConfig;
import com.sfg.domain.BeerInventory;
import com.sfg.repositories.BeerInventoryRepository;
import com.sfg.common.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
@Slf4j
public class NewInventoryListener {
    private final BeerInventoryRepository inventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event) {

        log.info("Get Inventory -> {}", event.toString());

        inventoryRepository.save(BeerInventory
                .builder()
                .beerId(event.getBeerDto().getId())
                .upc(event.getBeerDto().getUpc())
                .quantityOnHand(event.getBeerDto().getQuantityOnHand())
                .build());
    }
}
