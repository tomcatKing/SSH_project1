package com.mt.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name="mt_food")
@ToString(exclude= {"cartList","orderItemList"})
@NoArgsConstructor
public class Food implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="food_id")
	private Integer foodId;
	
	@Column(name="food_type")
	private Integer foodType;
	
	@Column(name="food_name")
	private String foodName;
	
	@Column(name="food_img")
	private String foodImg;
	
	@Column(name="food_detail")
	private String foodDetail;
	
	@Column(name="food_price")
	private BigDecimal foodPrice;
	
	@Column(name="food_status")
	private Integer foodStatus;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="foodId")
	private Set<Cart> cartList=new HashSet<>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="foodId")
	private Set<OrderItem> orderItemList=new HashSet<>();

	//模糊数据构造器
	public Food(Integer foodId, String foodName, String foodImg, BigDecimal foodPrice) {
		this.foodId = foodId;
		this.foodName = foodName;
		this.foodImg = foodImg;
		this.foodPrice = foodPrice;
	}
	
	
	
}
