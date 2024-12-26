package com.itwillbs.test3_mybatis2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.itwillbs.test3_mybatis2.service.ProductService;
import com.itwillbs.test3_mybatis2.vo.ProductVO;

@Controller
public class productController {

@Autowired
ProductService service;
	
	@GetMapping("registProduct")
	public String registProductform() {
		return "product/product_regist_form";
	}

	@PostMapping("registProduct")
	public String registProduct(ProductVO product) {
		int registCount = service.registProduct(product);
		return "redirect:/productList";
	}

	@GetMapping("productList")
	public String productList(Model model) {
		List<ProductVO> productList = service.getproductList();
		model.addAttribute("productList", productList);
		return "product/product_list";
	}

	@GetMapping("productInfo")
	public String productInfo(Integer product_id, Model model) {
		ProductVO product = service.getproductInfo(product_id);
		model.addAttribute("product", product);
		
		return "product/product_info";

	}
	
	@GetMapping("productModify")
	public String productModify(Integer product_id, Model model) {
		ProductVO product2 = service.getproductInfo(product_id);
		model.addAttribute("product", product2);
		return "product/product_modify_form";
	}
	
	@PostMapping("productModify")
	public String productModifyForm(ProductVO product, Model model) {
		int modify = service.getproductModifyForm(product);
		model.addAttribute("product_id", product.getProduct_id());
		return "redirect:/productInfo";
	}
	
	@GetMapping("productDelete")
	public String productdelete(Integer product_id, Model model) {
		int product2 = service.getproductdelete(product_id);
		model.addAttribute("product", product2);
		return "redirect:/productList";
		
	}
}
