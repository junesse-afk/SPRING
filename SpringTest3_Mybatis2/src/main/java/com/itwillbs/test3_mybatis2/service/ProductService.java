package com.itwillbs.test3_mybatis2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.test3_mybatis2.mapper.ProductMapper;
import com.itwillbs.test3_mybatis2.vo.ProductVO;


	@Service
	public class ProductService {

		@Autowired
		ProductMapper mapper;
		
		public int registProduct(ProductVO product) {				
			return mapper.registProduct(product);		

		}
		
		public ProductVO getproductInfo(Integer product_id) {
			return mapper.getproductInfo(product_id);
		}

		public List<ProductVO> getproductList() {
			return mapper.getproductList();
		}
		
		
		public int getproductModifyForm(ProductVO product) {
			return mapper.getproductModifyForm(product);
		}
		
		public int getproductdelete(Integer product_id) {
			return mapper.getproductdelete(product_id);
		}

}
