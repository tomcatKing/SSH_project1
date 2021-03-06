package com.mt.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mt.common.Const;
import com.mt.pojo.Cart;
import com.mt.pojo.User;
import com.mt.result.JsonResult;
import com.mt.service.ICartService;

import lombok.Getter;
import lombok.Setter;

@Controller
@Scope("prototype")
public class CartController extends SuperAction{

	private static final long serialVersionUID = 1L;

	@Autowired
	private ICartService iCartService;
	
	@Setter
	private Integer pageNum=1;
	
	@Setter
	private Integer pageSize=10;
	
	@Setter
	private Integer foodId;
	
	@Setter
	private Integer foodNum=1;
	
	@Setter
	private Integer cartId;
	
	@Getter
	private JsonResult jsonResult=JsonResult.errorMsg("当前用户未登录");
	
	@Getter
	private JsonResult errorResult=JsonResult.errorMsg("请求出错了");
	
	/**获取指定用户的所有购物车*/
	public String list() {
		Object user=session.getAttribute(Const.CURRENT_USER);
		User cur_user=(User)user;
		//用户id
		Integer userId=cur_user.getUserId();
		jsonResult=iCartService.list(userId,pageNum,pageSize);
		
		return SUCCESS;
	}
	
	/**添加购物车(这里应该不需要用户选择房间)*/
	public String add() {
		Object user=session.getAttribute(Const.CURRENT_USER);
		User cur_user=(User)user;
		Integer userId=cur_user.getUserId();
		jsonResult=iCartService.add(userId,foodId,foodNum);
		return SUCCESS;
	}
	
	/**移除购物车*/
	public String remove() {
		Object user=session.getAttribute(Const.CURRENT_USER);
		User cur_ser=(User)user;
		Integer userId=cur_ser.getUserId();
		jsonResult=iCartService.remove(userId,cartId);
		return SUCCESS;
	}
	
	/**修改购物车中商品的数量,包括选择状态*/
	public String update() {
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		jsonResult=iCartService.update(user.getUserId(),cartId,foodNum);
		return SUCCESS;
	}
	
	
}
