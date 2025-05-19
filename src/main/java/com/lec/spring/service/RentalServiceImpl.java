package com.lec.spring.service;

import com.lec.spring.domain.Rental;
import com.lec.spring.repository.ItemRepository;
import com.lec.spring.repository.RentalRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<Rental> findAllByUserId(Long userId) {
        return rentalRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void rentItem(Rental rental) {
        rentalRepository.save(rental); // 대여 등록
        userRepository.increaseRentalCount(rental.getUser().getId()); // ← rental_cnt 증가
    }

}
