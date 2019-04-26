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
@Table(name="mt_room")
@ToString(exclude= {"userList","orderList"})
@NoArgsConstructor
public class Room implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="room_id")
	private Integer roomId;
	
	@Column(name="room_type")
	private Integer roomType;
	
	@Column(name="room_name")
	private String roomName;
	
	@Column(name="room_address")
	private String roomAddress;
	
	@Column(name="room_price")
	private BigDecimal roomPrice;
	
	@Column(name="room_status")
	private Integer roomStatus;
	
	@Column(name="create_time")
	private Date createtTime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="roomId")
	private Set<User> userList=new HashSet<User>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="roomId")
	private Set<Order> orderList=new HashSet<Order>();
	
	public Room(Integer roomId,String roomName,String roomAddress,BigDecimal roomPrice,Integer roomType) {
		this.roomId=roomId;
		this.roomName=roomName;
		this.roomAddress=roomAddress;
		this.roomPrice=roomPrice;
		this.roomType=roomType;
	}
}
