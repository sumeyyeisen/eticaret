package com.proje.eticaret.mapper;

import com.proje.eticaret.dto.ItemDTO;
import com.proje.eticaret.entity.Item;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    private final ModelMapper modelMapper;

    public ItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemDTO toDTO(Item item) {
        return modelMapper.map(item, ItemDTO.class);
    }

    public Item toEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, Item.class);
    }
}
