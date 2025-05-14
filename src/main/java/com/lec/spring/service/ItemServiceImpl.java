package com.lec.spring.service;

import com.lec.spring.domain.Item;
import com.lec.spring.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public List<Item> findByCategory(String category) {
        return itemRepository.findByCategory(category);
    }

    @Override
    public void save(Item item) {
        item.setIsAvailable(true);
        item.setIsExist(true);
        itemRepository.save(item);
    }

    @Override
    public List<Item> findByBrandId(Long brandId) {
        return itemRepository.findByBrandId(brandId);
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
    @Transactional
    public int markAsUnavailable(Long id) {
        return itemRepository.markAsUnavailable(id);
    }

    @Override
    public int deleteById(Item item, Map<String, MultipartFile> files, Long[] delfile) {
        return 0;
    }
}
