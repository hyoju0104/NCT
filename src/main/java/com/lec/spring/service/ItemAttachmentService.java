package com.lec.spring.service;

import com.lec.spring.domain.ItemAttachment;

import java.util.List;

public interface ItemAttachmentService {

    int save(ItemAttachment file);

    List<ItemAttachment> findByItemId(Long itemId);

    ItemAttachment findById(Long id);

    int deleteById(Long id);
}
