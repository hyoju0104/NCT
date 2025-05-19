package com.lec.spring.repository;

import com.lec.spring.domain.ItemAttachment;

import java.util.List;

public interface ItemAttachmentRepository {

    int save(ItemAttachment file);

    List<ItemAttachment> findByItemId(Long itemId);

    ItemAttachment findById(Long id);

    int deleteById(ItemAttachment file);
}
