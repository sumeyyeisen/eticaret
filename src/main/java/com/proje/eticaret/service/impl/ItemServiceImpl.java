package com.proje.eticaret.service.impl;

import com.proje.eticaret.dto.ItemDTO;
import com.proje.eticaret.entity.Item;
import com.proje.eticaret.exception.ResourceNotFoundException;
import com.proje.eticaret.mapper.ItemMapper;
import com.proje.eticaret.repository.ItemRepository;
import com.proje.eticaret.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        return itemMapper.toDTO(item);
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(itemMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ItemDTO createItem(ItemDTO itemDTO) {
        Item item = itemMapper.toEntity(itemDTO);
        Item savedItem = itemRepository.save(item);
        return itemMapper.toDTO(savedItem);
    }

    @Override
    public ItemDTO updateItem(Long id, ItemDTO itemDTO) {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        existingItem.setQuantity(itemDTO.getQuantity());
        existingItem.setProductPrice(itemDTO.getProductPrice());

        Item updatedItem = itemRepository.save(existingItem);
        return itemMapper.toDTO(updatedItem);
    }

    @Override
    public void deleteItem(Long id) {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        itemRepository.delete(existingItem);
    }
}
