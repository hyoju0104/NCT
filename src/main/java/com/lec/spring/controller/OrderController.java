package com.lec.spring.controller;

import com.lec.spring.config.PrincipalUserDetails;
import com.lec.spring.domain.*;
import com.lec.spring.service.ItemAttachmentService;
import com.lec.spring.service.ItemService;
import com.lec.spring.service.RentalService;
import com.lec.spring.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	private final ItemService itemService;
	private final OrderValidator orderValidator;
	private final RentalService rentalService;
	private final ItemAttachmentService itemAttachmentService;
	private final UserService userService;
	
	public OrderController(
			ItemService itemService, OrderValidator orderValidator,
			RentalService rentalService, ItemAttachmentService itemAttachmentService,
			UserService userService) {
		this.itemService = itemService;
		this.orderValidator = orderValidator;
		this.rentalService = rentalService;
		this.itemAttachmentService = itemAttachmentService;
		this.userService = userService;
	}
	
	@GetMapping("/detail/{id}")
	public String orderDetail(
			@PathVariable("id") Long id,
			@AuthenticationPrincipal PrincipalUserDetails principal,
			Model model
	) {
		Item item = itemService.detail(id);
		List<ItemAttachment> attachments = itemAttachmentService.findByItemId(id);
		ItemAttachment attachment = !attachments.isEmpty() ? attachments.get(0) : null;
		
		model.addAttribute("item", item);
		model.addAttribute("attachment", attachment);
		
		User nowUser = userService.findById(principal.getUser().getId());
		model.addAttribute("user", nowUser);
		
		return "order/detail";
	}
	
	
	@GetMapping("/complete/{id}")
	public String orderComplete(@PathVariable Long id, Model model) {
		Item item = itemService.detail(id);
		model.addAttribute("item", item);
		return "order/complete";
	}
	
	@PostMapping("/complete/{id}")
	public String completeOrder(
			@PathVariable Long id,
			@ModelAttribute("user") User user,
			BindingResult result,
			@AuthenticationPrincipal PrincipalUserDetails principal,
			Model model,
			RedirectAttributes redirectAttributes
	) {
		// 사용자 정보 가져오기
		User loginUser = principal.getUser();
		
		// 대여 가능 조건 검사
		if (!"ACTIVE".equals(loginUser.getStatusAccount())) {
			redirectAttributes.addFlashAttribute("inactiveError", "계정 비활성화 상태입니다.");
			return "redirect:/order/detail/" + id;
		}
		if (loginUser.getPlanId() == null) {
			redirectAttributes.addFlashAttribute("planNullError", "요금제를 구독해 주세요.");
			return "redirect:/order/detail/" + id;
			
		}
		
		orderValidator.validate(user, result);
		if (result.hasErrors()) {
			if (result.hasFieldErrors("phoneNum")) {
				model.addAttribute("error_phoneNum", result.getFieldError("phoneNum").getDefaultMessage());
			}
			if (result.hasFieldErrors("address")) {
				model.addAttribute("error_address", result.getFieldError("address").getDefaultMessage());
			}
			
			Item item = itemService.detail(id);
			model.addAttribute("item", item);
			
			List<ItemAttachment> attachments = itemAttachmentService.findByItemId(item.getId());
			ItemAttachment attachment = !attachments.isEmpty() ? attachments.get(0) : null;
			model.addAttribute("attachment", attachment);
			
			return "order/detail";
		}
		
		Item item = itemService.detail(id);
		
		// 주소 업데이트
		User existing = userService.findById(loginUser.getId());
		existing.setZipcode(user.getZipcode());
		existing.setAddress(user.getAddress());
		existing.setAddressDetail(user.getAddressDetail());
		userService.updateUserAddress(existing);
		
		// 연락처 업데이트
		existing.setPhoneNum(user.getPhoneNum());
		userService.updateUserPhoneNum(existing);
		
		// Rental 생성
		Rental rental = new Rental();
		rental.setUser(principal.getUser());
		rental.setItem(item);
		rental.setStatus("RENTED");
		
		// 저장 + available_count -1 처리
		rentalService.rentItem(rental);
		itemService.markAsUnavailable(item.getId());
		
		return "redirect:/order/complete/" + id;
	}
	
}
