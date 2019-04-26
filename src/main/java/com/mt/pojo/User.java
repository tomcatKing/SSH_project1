package com.mt.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="mt_user")
@Data
@ToString(exclude= {"payInfoList","cartList","orderList","orderItemList"})
@NoArgsConstructor
public class User implements Serializable{
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(name="user_name",unique=true)
	private String userName;
	
	@Column(name="user_email",unique=true)
	private String userEmail;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="room_id")
	private Room roomId;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="userId")
	private Set<PayInfo> payInfoList=new HashSet<PayInfo>();
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="userId")
	private Set<Cart> cartList=new HashSet<Cart>();
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="userId")
	private Set<Order> orderList=new HashSet<>();
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="userId")
	private Set<OrderItem> orderItemList=new HashSet<>();
}
