package com.proje.eticaret.service;

import com.proje.eticaret.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    ItemDTO getItemById(Long id);
    List<ItemDTO> getAllItems();
    ItemDTO createItem(ItemDTO itemDTO);
    ItemDTO updateItem(Long id, ItemDTO itemDTO);
    void deleteItem(Long id);
}
