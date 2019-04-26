package com.mt.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Entity
@Table(name="mt_order_item")
@Data
@ToString(exclude= {"foodId","userId"})
@NoArgsConstructor
public class OrderItem implements Serializable{
	@Column(name="order_item_id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer orderItemId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userId;
	
	@Column(name="order_no")
	private Long orderNo;
	
	@ManyToOne
	@JoinColumn(name="food_id")
	private Food foodId;
	
	@Column(name="food_num")
	private Integer foodNum;
	
	@Column(name="total_price")
	private BigDecimal totalPrice;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;	
}
