package com.mt.common;


public class Const {
	public static final String CURRENT_USER="current_user";

	//美食类型
	public enum FoodTypeEnum{
    	HUNCAI(1,"荤菜"),
		SUCAI(2,"素菜"),
		TIANDIAN(3,"甜点"),
		XIAOCHI(4,"小吃"),
		LINNIAO(5,"饮料"),
		GAOTAN(6,"高汤");
		FoodTypeEnum(int code,String value){
    		this.code=code;
    		this.value=value;
    	}
    	private String value;
    	private int code;
		public String getValue() {
			return value;
		}
		public int getCode() {
			return code;
		}	
		
	    public static FoodTypeEnum codeOf(int code) {
	    	for(FoodTypeEnum foodTypeEnum:values()) {
	    		//如果遍历到了这个枚举,就返回
	    		if(foodTypeEnum.getCode()==code) {
	    			return foodTypeEnum;
	    		}
	    	}
	    	throw new RuntimeException("没有找到对应的枚举");
	    }
    }
	
	//美食状态
	public enum FoodStatusEnum{
    	ZAISHOU(1,"在售中"),
		XIAJIA(2,"已下架"),
		SHANCHU(3,"已删除");
		FoodStatusEnum(int code,String value){
    		this.code=code;
    		this.value=value;
    	}
    	private String value;
    	private int code;
		public String getValue() {
			return value;
		}
		public int getCode() {
			return code;
		}	
		
	    public static FoodStatusEnum codeOf(int code) {
	    	for(FoodStatusEnum foodTypeEnum:values()) {
	    		//如果遍历到了这个枚举,就返回
	    		if(foodTypeEnum.getCode()==code) {
	    			return foodTypeEnum;
	    		}
	    	}
	    	throw new RuntimeException("没有找到对应的枚举");
	    }
    }
	
	//购物车选择状态
	public enum CartCheckedEnum{
		NOCHECKED(0,"未勾选"),
		INCHECKED(1,"已勾选");
		CartCheckedEnum(int code,String val){
			this.code=code;
			this.val=val;
		}
		private int code;
		private String val;
		public int getCode() {
			return code;
		}
		public String getVal() {
			return val;
		}
		
		public static CartCheckedEnum codeOf(int code) {
	    	for(CartCheckedEnum foodTypeEnum:values()) {
	    		//如果遍历到了这个枚举,就返回
	    		if(foodTypeEnum.getCode()==code) {
	    			return foodTypeEnum;
	    		}
	    	}
	    	throw new RuntimeException("没有找到对应的枚举");
	    }
	}
	
	//订单支付类型
	public enum PaymentTypeEnum{
		ZHIFUBAO(1,"支付宝支付");
		PaymentTypeEnum(int code,String val){
			this.code=code;
			this.val=val;
		}
		private int code;
		private String val;
		public int getCode() {
			return code;
		}
		public String getVal() {
			return val;
		}
		
		public static PaymentTypeEnum codeOf(int code) {
	    	for(PaymentTypeEnum foodTypeEnum:values()) {
	    		//如果遍历到了这个枚举,就返回
	    		if(foodTypeEnum.getCode()==code) {
	    			return foodTypeEnum;
	    		}
	    	}
	    	throw new RuntimeException("没有找到对应的枚举");
	    }
	}
	
	//订单支付状态
	public enum OrderStatusEnum{
		CANCEL(0,"已取消"),
		NOPAY(10,"未付款"),
		ISPAY(20,"已付款"),
		SUCCESS(30,"交易完成");
		OrderStatusEnum(int code,String val){
			this.code=code;
			this.val=val;
		}
		private int code;
		private String val;
		public int getCode() {
			return code;
		}
		public String getVal() {
			return val;
		}
		
		public static OrderStatusEnum codeOf(int code) {
	    	for(OrderStatusEnum foodTypeEnum:values()) {
	    		//如果遍历到了这个枚举,就返回
	    		if(foodTypeEnum.getCode()==code) {
	    			return foodTypeEnum;
	    		}
	    	}
	    	throw new RuntimeException("没有找到对应的枚举");
	    }
	}
	
	
	//房间类型
	public enum RoomTypeEnum{
		BASIC(0,"普通房间"),
		VIP(10,"vip房间");
		RoomTypeEnum(int code,String val){
			this.code=code;
			this.val=val;
		}
		private int code;
		private String val;
		public int getCode() {
			return code;
		}
		public String getVal() {
			return val;
		}
		
		public static RoomTypeEnum codeOf(int code) {
	    	for(RoomTypeEnum foodTypeEnum:values()) {
	    		//如果遍历到了这个枚举,就返回
	    		if(foodTypeEnum.getCode()==code) {
	    			return foodTypeEnum;
	    		}
	    	}
	    	throw new RuntimeException("没有找到对应的枚举");
	    }
	}
	
	//订单支付状态
	public enum RoomStatusEnum{
		KESHIYON(0,"可使用"),
		YISHIYON(1,"已使用"),
		NOSHIYON(2,"无法使用");
		RoomStatusEnum(int code,String val){
			this.code=code;
			this.val=val;
		}
		private int code;
		private String val;
		public int getCode() {
			return code;
		}
		public String getVal() {
			return val;
		}
		
		public static RoomStatusEnum codeOf(int code) {
	    	for(RoomStatusEnum foodTypeEnum:values()) {
	    		//如果遍历到了这个枚举,就返回
	    		if(foodTypeEnum.getCode()==code) {
	    			return foodTypeEnum;
	    		}
	    	}
	    	throw new RuntimeException("没有找到对应的枚举");
	    }
	}
}
