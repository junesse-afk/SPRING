package com.itwillbs.test3_mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.itwillbs.test3_mybatis.vo.ProductVO;
import com.itwillbs.test3_mybatis.vo.StudentVO;

@Mapper
public interface ProductMapper {

	// 상품정보 등록
	public int registProduct(ProductVO product);
	
	// 상품목록 조회
	public List<ProductVO> getProductList();
	public ProductVO getProductList(String product_id);
	
	// 상품상세 조회
	public ProductVO getProduct(String product_id);
	
	// 상품 삭제
	public int deleteProduct(String product_id);
	
	// 상품 수정
	public int updateProduct(ProductVO product);
	
}
