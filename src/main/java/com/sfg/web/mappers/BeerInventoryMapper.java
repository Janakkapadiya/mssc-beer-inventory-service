package com.sfg.web.mappers;

import com.sfg.domain.BeerInventory;
import com.sfg.web.model.BeerInventoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by jt on 2019-05-31.
 */
@Mapper(uses = {DateMapper.class})
public interface BeerInventoryMapper {

    @Mapping(target = "version", ignore = true)
    BeerInventory beerInventoryDtoToBeerInventory(BeerInventoryDto beerInventoryDTO);

    BeerInventoryDto beerInventoryToBeerInventoryDto(BeerInventory beerInventory);
}
