package com.mt.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mt.result.JsonResult;
import com.mt.service.IFoodService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Scope("prototype")
@Log4j
public class FoodController extends SuperAction{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IFoodService iFoodService;
	
	@Getter //用户返回给前端
	private JsonResult jsonResult;
	
	@Setter
	private int pageNum=1;
	
	@Setter
	private int pageSize=10;
	
	@Setter /**1,2,3*/
	private String foodTypes;
	
	@Setter
	private String orderBy;
	
	@Setter
	private String keyword;
	
	@Setter
	private Integer foodId; //美食id,获取详情时使用
	
	@Getter
	private JsonResult errorResult=JsonResult.errorMsg("请求出错了");
	
	/**获取所有美食的方法,允许传入排序字段和关键子*/
	public String list() {
		if(keyword==null || keyword.trim()=="") {
			keyword=null;
		}
		if(orderBy==null || orderBy.trim()=="") {
			orderBy=null;
		}
		if(foodTypes==null || foodTypes.trim()=="") {
			foodTypes=null;
		}
		log.info("获取美食列表的action启动了");
		jsonResult=iFoodService.list(pageNum, pageSize, orderBy, keyword,foodTypes);
		return SUCCESS;
	}
	
	/**通过美食id获取美食详情*/
	public String detail() {
		log.info("获取订单详情的action启动了,传入参数:foodId->"+foodId);
		if(foodId==null || foodId<=0) {
			jsonResult=JsonResult.errorMsg();
		}else {
			jsonResult=iFoodService.detail(foodId);
		}	
		return SUCCESS;
	}
	
	
}
