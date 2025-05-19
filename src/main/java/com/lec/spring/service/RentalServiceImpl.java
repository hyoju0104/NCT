package com.lec.spring.service;

import com.lec.spring.domain.Rental;
import com.lec.spring.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ItemService itemService;

    @Autowired
    public RentalServiceImpl(RentalRepository rentalRepository, ItemService itemService) {
        this.rentalRepository = rentalRepository;
        this.itemService = itemService;
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
    public void updateReturned(Long rentalId) {
        rentalRepository.updateReturned(rentalId);

        Rental rental = rentalRepository.findById(rentalId);
        if (rental != null && rental.getItem() != null) {
            itemService.setAvailable(rental.getItem().getId(), true);
        }
    }

    @Override
    @Transactional
    public int updateOverdue() {
        return rentalRepository.updateOverdue();
    }
}
