package com.lec.spring.service;

import com.lec.spring.domain.Rental;
import com.lec.spring.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;

    @Override
    public List<Rental> findAllByUserId(Long userId) {
        return rentalRepository.findAllByUserId(userId);
    }
}
