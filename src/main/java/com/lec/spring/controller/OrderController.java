package com.lec.spring.controller;

import com.lec.spring.config.PrincipalUserDetails;
import com.lec.spring.domain.*;
import com.lec.spring.repository.PlanRepository;
import com.lec.spring.service.ItemAttachmentService;
import com.lec.spring.service.ItemService;
import com.lec.spring.service.RentalService;
import com.lec.spring.service.UserService;
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
	private final PlanRepository planRepository;

	public OrderController(
			ItemService itemService,
			OrderValidator orderValidator,
			RentalService rentalService,
			ItemAttachmentService itemAttachmentService,
			UserService userService,
			PlanRepository planRepository
	) {
		this.itemService = itemService;
		this.orderValidator = orderValidator;
		this.rentalService = rentalService;
		this.itemAttachmentService = itemAttachmentService;
		this.userService = userService;
		this.planRepository = planRepository;
	}

	@GetMapping("/detail/{id}")
	public String orderDetail(
			@PathVariable("id") Long id,
			@AuthenticationPrincipal PrincipalUserDetails principal,
			Model model
	) {
		// 상품 및 대표 이미지
		Item item = itemService.detail(id);
		List<ItemAttachment> attachments = itemAttachmentService.findByItemId(id);
		ItemAttachment attachment = !attachments.isEmpty() ? attachments.get(0) : null;
		model.addAttribute("item", item);
		model.addAttribute("attachment", attachment);
		
		// 로그인 사용자 정보
		User nowUser = userService.findById(principal.getUser().getId());
		model.addAttribute("user", nowUser);
		
		// 남은 대여 횟수 및 총 가능 횟수 계산
		int rentalCnt = nowUser.getRentalCnt() == null ? 0 : nowUser.getRentalCnt();
		Plan plan = planRepository.findByPlanId(nowUser.getPlanId());
		int totalCnt = plan != null ? plan.getCount() : 0;
		int remainCnt = Math.max(totalCnt - rentalCnt, 0);
		
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("remainCnt", remainCnt);

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
		User loginUser = userService.findById(principal.getUser().getId());

		// 계정 상태 검사
		if (!"ACTIVE".equals(loginUser.getStatusAccount())) {
			redirectAttributes.addFlashAttribute("inactiveError", "계정 비활성화 상태입니다.");
			return "redirect:/order/detail/" + id;
		}
		
		// 플랜 구독 여부 검사
		Long planId = loginUser.getPlanId();
		if (planId == null) {
			redirectAttributes.addFlashAttribute("planNullError", "요금제를 구독해 주세요.");
			return "redirect:/order/detail/" + id;

		}
		
		// 대여 가능 횟수 검사
		int rentalCnt = loginUser.getRentalCnt() == null ? 0 : loginUser.getRentalCnt();
		Plan plan = planRepository.findByPlanId(planId);
		int totalCnt = plan != null ? plan.getCount() : 0;
		if (rentalCnt >= totalCnt) {
			redirectAttributes.addFlashAttribute("rentalCntError", "대여 가능 횟수는 모두 소진했습니다.");
			return "redirect:/order/detail/" + id;
		}

		orderValidator.validate(user, result);
		if (result.hasErrors()) {
			if (result.hasFieldErrors("phoneNum")) {
				model.addAttribute("error_phoneNum",
						result.getFieldError("phoneNum").getDefaultMessage());
			}
			if (result.hasFieldErrors("address")) {
				model.addAttribute("error_address",
						result.getFieldError("address").getDefaultMessage());
			}
			if (result.hasFieldErrors("addressDetail")) {
				model.addAttribute("error_addressDetail",
						result.getFieldError("addressDetail").getDefaultMessage());
			}
		
			// 다시 상품 정보 세팅
			Item item = itemService.detail(id);
			model.addAttribute("item", item);

			List<ItemAttachment> attachments = itemAttachmentService.findByItemId(item.getId());
			ItemAttachment attachment = attachments.isEmpty() ? null : attachments.get(0);
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
		rental.setUser(existing);
		rental.setItem(item);
		rental.setStatus("RENTED");

		// 저장 + available_count -1 처리
		rentalService.rentItem(rental);
		itemService.markAsUnavailable(item.getId());

		return "redirect:/order/complete/" + id;
	}

}
