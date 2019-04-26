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

@Entity
@Table(name="mt_payinfo")
@Data
@ToString(exclude={"userId"})
@NoArgsConstructor
public class PayInfo implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pay_id")
	private Integer payId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userId;
	
	@Column(name="order_no")
	private Long orderNo;
	
	@Column(name="pay_platform")
	private Integer payPlatform;
	
	@Column(name="platform_number")
	private String platformNumber;
	
	@Column(name="platform_status")
	private String platformStatus;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
}
