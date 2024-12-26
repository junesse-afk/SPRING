package com.itwillbs.test3_mybatis2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.itwillbs.test3_mybatis2.vo.ProductVO;

@Mapper
public interface ProductMapper {
	
	//상품 정보 등록
	int registProduct (ProductVO product);
	
	//상품 목록 조회
	List<ProductVO> getproductList();
	
	//상품 상세 조회
	ProductVO getproductInfo(Integer product_id);
	
	
	int getproductModifyForm(ProductVO product);
	
	ProductVO getproductdelete(ProductVO product);

	int getproductdelete(Integer product_id);
}
