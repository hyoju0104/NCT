package com.lec.spring.service;

import com.lec.spring.domain.Rental;

import java.util.List;

public interface RentalService {
    void rentItem(Rental rental);
    List<Rental> findAllByUserId(Long userId);
}
