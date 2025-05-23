package com.lec.spring.service;

import com.lec.spring.domain.Rental;
import com.lec.spring.repository.RentalRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ItemService itemService;
    private final UserRepository userRepository;

    @Autowired
    public RentalServiceImpl(RentalRepository rentalRepository, ItemService itemService, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.itemService = itemService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public int createRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public Rental findRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    @Override
    public List<Rental> findAllByUserId(Long userId) {
        return rentalRepository.findAllByUserId(userId);
    }

    @Override
    public List<Rental> findRentalsByBrandId(Long brandId) {
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
    public void updateOverdueStatus() {
        rentalRepository.updateOverdue();
    }


    @Override
    @Transactional
    public void rentItem(Rental rental) {
        rentalRepository.save(rental); // 대여 등록
        userRepository.increaseRentalCount(rental.getUser().getId()); // ← rental_cnt 증가
    }

    @Override
    public int countActiveRentalsByUserId(Long userId) {
        return rentalRepository.countActiveRentalsByUserId(userId);
    }
}
