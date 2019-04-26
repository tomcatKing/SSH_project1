package com.mt.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude= {"userId","foodId"})
@Entity
@Table(name="mt_cart")
@NoArgsConstructor
public class Cart implements Serializable{
	@Id
	@Column(name="cart_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer cartId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userId;
	
	@ManyToOne
	@JoinColumn(name="food_id")
	private Food foodId;
	
	@Column(name="food_num")
	private Integer foodNum;
	
	@Column(name="checked")
	private Integer checked;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;

	public Cart(Integer cartId, Food foodId, Integer foodNum) {
		super();
		this.cartId = cartId;
		this.foodId = foodId;
		this.foodNum = foodNum;
	}

	public Cart(Integer cartId,Integer foodNum) {
		this.cartId = cartId;
		this.foodNum = foodNum;
	}
	
	
}
