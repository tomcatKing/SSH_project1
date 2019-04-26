package com.mt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mt.dao.IFoodDao;
import com.mt.result.JsonResult;
import com.mt.service.IFoodService;
import com.mt.util.Page;
import com.mt.vo.FoodVo;

@Service
@Transactional
public class FoodServiceImpl implements IFoodService {
	
	@Autowired
	private IFoodDao iFoodDao;
	
	@Override
	@Transactional(readOnly=true)
	/**获取所有美食的方法,允许传入排序字段和关键字段  提醒以下:如果参数不存在,则直接传入null就可以了*/
	public JsonResult list(int pageNum, int pageSize, String orderBy, String keyword,String foodTypes) {
		Page p=new Page();
		p.setCurrentPage(pageNum);
		p.setPageRows(pageSize);
		
		StringBuilder sb=new StringBuilder("select new Food(foodId,foodName,foodImg,foodPrice) from Food ");
		Integer[] types=null;
		System.out.println(foodTypes);
		if(foodTypes!=null && foodTypes.trim()!="") {
			if(!foodTypes.contains(",")) {
				types=new Integer[1];
				types[0]=Integer.parseInt(foodTypes.trim());
			}else {
				/**1,2*/
				String[] temps=foodTypes.split(",");
				types=new Integer[temps.length];
				for(int i=0;i<types.length;i++) {
					types[i]=Integer.parseInt(temps[i]);
				}
			}
		}
		if(types!=null) {
			sb.append("where foodType in (");
			for(int i=0;i<types.length;i++) {
				if(i==types.length-1) {
					sb.append(types[i]);
				}else {
					sb.append(types[i]+",");
				}
			}
			sb.append(") ");
		}
		//这里只能sql注入
		if(keyword!=null) {
			if(types!=null) {
				sb.append("and foodName like :keyword ");
			}else {
				sb.append("where foodName like :keyword ");
			}	
		}
		if(orderBy!=null) {
			sb.append("order by :orderBy ");	
		}
		//添加参数
		Map<String,String> params=new HashMap<>();
		params.put(":orderBy",orderBy);
		params.put(":keyword", "%"+keyword+"%");
		
		
		p.setHql(sb.toString());
		
		Page result=new Page();
		result=iFoodDao.pageResult(p,params);
		return JsonResult.ok(result);
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult detail(Integer foodId) {
		FoodVo foodVo=iFoodDao.detail(foodId);
		return JsonResult.ok(foodVo);
	}

}
