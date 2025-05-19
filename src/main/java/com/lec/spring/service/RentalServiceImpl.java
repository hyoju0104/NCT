package com.lec.spring.service;

import com.lec.spring.domain.Rental;
import com.lec.spring.repository.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;

    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    @Transactional
    public int createRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    @Override
    public List<Rental> getRentalsByUserId(Long userId) {
        return rentalRepository.findAllByUserId(userId);
    }

    @Override
    public List<Rental> getRentalsByBrandId(Long brandId) {
        return rentalRepository.findAllByBrandId(brandId);
    }

    @Override
    @Transactional
    public int updateReturned(Long rentalId) {
        return rentalRepository.updateReturned(rentalId);
    }

    @Override
    @Transactional
    public int updateOverdue() {
        return rentalRepository.updateOverdue();
    }
}
