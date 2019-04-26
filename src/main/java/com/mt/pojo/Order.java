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
@Table(name="mt_order")
@Data
@ToString(exclude= {"userId","roomId"})
@NoArgsConstructor
public class Order implements Serializable{
	
	@Column(name="order_id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer orderId;
	
	@Column(name="order_no")
	private Long orderNo;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userId;
	
	@ManyToOne
	@JoinColumn(name="room_id")
	private Room roomId;
	
	@Column(name="payment")
	private BigDecimal payment;
	
	@Column(name="payment_type")
	private Integer paymentType;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="order_desc")
	private String orderDesc;
	
	@Column(name="payment_time")
	private Date paymentTime;
	
	@Column(name="send_time")
	private Date sendTime;
	
	@Column(name="end_time")
	private Date endTime;
	
	@Column(name="close_time")
	private Date closeTime;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	public Order(Integer orderId, Long orderNo) {
		this.orderId = orderId;
		this.orderNo = orderNo;
	}
	
	public Order(Integer orderId, Long orderNo,Integer status) {
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.status=status;
	}
	
	
}
