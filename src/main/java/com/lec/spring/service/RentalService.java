package com.lec.spring.service;

import com.lec.spring.domain.Rental;

import java.util.List;

public interface RentalService {
    List<Rental> findAllByUserId(Long userId);
}
