package com.itwillbs.test3_mybatis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductVO {
	private int product_id;
	private String product_name;
	private int product_price;
	private int product_qty;
	private String product_img;
}
