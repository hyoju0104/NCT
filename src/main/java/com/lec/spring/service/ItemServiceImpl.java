package com.lec.spring.service;

import com.lec.spring.domain.Item;
import com.lec.spring.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> list() { return itemRepository.findAll(); }

    @Override
    public int write(Item item, Map<String, MultipartFile> files) {
        return 0;
    }

    @Override
    public Item detail(Long id) {
        Item item = itemRepository.findById(id);
        return item;
    }

    @Override
    public int update(Item item, Map<String, MultipartFile> files, Long[] delfile) {
        return 0;
    }

    @Override
    public int deleteById(Item item, Map<String, MultipartFile> files, Long[] delfile) {
        return 0;
    }
}
