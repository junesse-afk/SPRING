package com.itwillbs.test3_mybatis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.test3_mybatis.service.ProductService;
import com.itwillbs.test3_mybatis.vo.ProductVO;

@Controller
public class ProductController {

	@Autowired
	ProductService service;
	
	@GetMapping("registProduct")
	public String registProduct() {
		return "product/product_regist_form";
	}
	
	@PostMapping("registProduct")
	public String registProduct(ProductVO product) {
		System.out.println("!@#!@#");
		System.out.println(product);
		
		int insertCnt = service.registProduct(product);
		System.out.println("insert 결과: " + insertCnt);
		
		return "redirect:/productList";
	}
	
	@GetMapping("productList")
	public String productList(@RequestParam Map<String, String> param, Model model) {
		System.out.println("!@#!@#");
		System.out.println(param);
		
		List<ProductVO> productList = service.getProductList();
		model.addAttribute("productList", productList);
		return "product/product_list";
	}
	
	@GetMapping("productInfo")
	public String productInfo(String product_id,Model model) {
		
		ProductVO product = service.getProductList(product_id);
		model.addAttribute("product", product);
		
		return "product/product_info";
	}
	
	@GetMapping("productDelete")
	public String productDelete(String product_id) {
		int deleteCnt = service.deleteProduct(product_id);
		return "redirect:/productList";
	}
	
	@GetMapping("productModify")
	public String productModify(String product_id, Model model) {
		ProductVO product = service.getProductList(product_id);
		model.addAttribute("product", product);
		return "product/product_modify_form";
	}
	
	@PostMapping("productModify")
	public String productModify(ProductVO product, Model model) {

		int updateCnt = service.updateProduct(product);
		model.addAttribute("product_id", product.getProduct_id());
		
		return "redirect:/productInfo";
	}
	
	
	
}
