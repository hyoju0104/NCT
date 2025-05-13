package com.lec.spring.controller;

import com.lec.spring.domain.Brand;
import com.lec.spring.domain.Post;
import com.lec.spring.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/mypage/detail/{id}")
    public String myDetail(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandService.myDetail(id));
        return "brand/mypage/detail";
    }

    @GetMapping("/mypage/update/{id}")
    public String myUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandService.selectById(id));
        return "brand/mypage/update";
    }

    @PostMapping("/mypage/update")
    public String myUpdateOk(
            @RequestParam Map<String, MultipartFile> files,  // 첨부파일들 <name, file>
            @Valid Brand brand,
            BindingResult result,   // Validator 가 유효성 검사를 한 결과가 담긴 객체.
            Model model,    // 매개변수 선언시 BindingResult 보다 Model 을 뒤에 두어야 한다.
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            showErrors(result);

            redirectAttributes.addFlashAttribute("phoneNum", brand.getPhoneNum());
            redirectAttributes.addFlashAttribute("description", brand.getDescription());

            for(FieldError err : result.getFieldErrors()) {
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

            return "redirect:/brand/mypage/update" + brand.getId();
        }
        model.addAttribute("result", brandService.myUpdate(brand));
        return "brand/mypage/updateOk";
    }

    @PostMapping("/mypage/delete/{id}")
    public String myDeleteOk(@PathVariable Long id) {
        brandService.myDelete(id);
        return "redirect:/login";
    }


    // 바인딩 에러 출력 도우미 메소드
    public void showErrors(Errors errors){
        if(errors.hasErrors()){
            System.out.println("💢에러개수: " + errors.getErrorCount());
            // 어떤 field 에 어떤 에러(code) 가 담겨있는지 확인
            System.out.println("\t[field]\t|[code]");
            List<FieldError> errList = errors.getFieldErrors();
            for(FieldError err : errList){
                System.out.println("\t" + err.getField() + "\t|" + err.getCode());
            }
        } else {
            System.out.println("✔에러 없슴");
        }
    } // end showErrors()
}

