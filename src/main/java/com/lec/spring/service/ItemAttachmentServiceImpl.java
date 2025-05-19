package com.lec.spring.service;

import com.lec.spring.domain.ItemAttachment;
import com.lec.spring.repository.ItemAttachmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemAttachmentServiceImpl implements ItemAttachmentService {

    private final ItemAttachmentRepository itemAttachmentRepository;

    public ItemAttachmentServiceImpl(ItemAttachmentRepository itemAttachmentRepository) {
        this.itemAttachmentRepository = itemAttachmentRepository;
    }

    @Override
    public int save(ItemAttachment file) {
        return itemAttachmentRepository.save(file);
    }

    @Override
    public List<ItemAttachment> findByItemId(Long itemId) {
        return itemAttachmentRepository.findByItemId(itemId);
    }

    @Override
    public ItemAttachment findById(Long id) {
        return itemAttachmentRepository.findById(id);
    }

    @Override
    public int deleteById(Long id) {
        ItemAttachment file = itemAttachmentRepository.findById(id);
        if (file != null) {
            return itemAttachmentRepository.deleteById(file);
        }
        return 0;
    }
}
