package com.mt.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.mt.common.Const;
import com.mt.dao.ICartDao;
import com.mt.dao.IFoodDao;
import com.mt.pojo.Cart;
import com.mt.pojo.Food;
import com.mt.pojo.User;
import com.mt.result.JsonResult;
import com.mt.util.Page;
import com.mt.vo.CartVo;

import lombok.extern.log4j.Log4j;

@Repository
@Log4j
public class CartDaoImpl implements ICartDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private IFoodDao iFoodDao;
	
	@Override
	public Page list(Page page, Map<String, Integer> params) {
		log.info("开始执行查询用户的购物车的方法,hql语句为:"+page.getHql());
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery(page.getHql()).setCacheable(true);
		query.setInteger("userId", params.get(":userId"));
		
		int count=query.list().size();
		
		//设置查询开始于结束的位置
		query.setFirstResult((page.getCurrentPage()-1)*page.getPageRows());
		query.setMaxResults(page.getPageRows());
		
		List<Object[]> objectList=query.list();
		List<Cart> cartList=Lists.newArrayList();
		for (Object[] objects : objectList) {
			System.out.println(objects);
			cartList.add(new Cart((Integer)objects[0],(Food)objects[1],(Integer)objects[2]));
		}
		List<CartVo> cartVoList=Lists.newArrayList();
		for(Cart cart:cartList) {
			cartVoList.add(CartVo.accessCartVo(cart));
		}
		page=Page.initPage(page,cartVoList, count);
		
		return page;
	}
	
	@Override
	/**添加商品到购物车*/
	public JsonResult add(Integer userId, Integer foodId, Integer foodNum) {
		Session session=sessionFactory.getCurrentSession();
		//如果用户没有房间
		User user=(User)session.get(User.class, userId);
//		if(user.getRoomId()==null) {
//			return JsonResult.errorMsg("当前用户未选择房间!!");
//		}
		
		Food food=iFoodDao.getFoodById(foodId);
		//如果食物不存在
		if(food==null) {
			return JsonResult.errorMsg("该美食不存在");
		}
		//如果菜不是在售状态
		if(food.getFoodStatus()!=Const.FoodStatusEnum.ZAISHOU.getCode()) {
			return JsonResult.errorMsg("这道菜已过期");
		}
		
		//这里好需要做一个判断,如果cart已经存在
		Cart cart=getCartByFoodId(userId, foodId);
		if(cart==null) {
			//表示购物车不存在,判断用户的所有购物车的长度是否超过10条
			int cartSize=this.getUserCartSize(userId);
			if(cartSize>9) {
				return JsonResult.errorMsg("您的购物车已经满了");
			}
			cart=new Cart();
			cart.setUserId(user);
			cart.setFoodId(food);
			cart.setFoodNum(foodNum);
			//默认购物车状态为未选择
			cart.setChecked(Const.CartCheckedEnum.NOCHECKED.getCode());
			cart.setCreateTime(new Date(System.currentTimeMillis()));
			cart.setUpdateTime(new Date(System.currentTimeMillis()));
			session.persist(cart);
			if(cart.getCartId()>0) {
				return JsonResult.ok();
			}
			return JsonResult.errorMsg("添加到购物车失败!!");
		}else {
			//此时购物车已经存在
			cart.setFoodNum(cart.getFoodNum()+foodNum);
			cart.setUpdateTime(new Date());
			session.update(cart);
			return JsonResult.ok();
		}
	}

	@Override
	public JsonResult remove(Integer userId, Integer cartId) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Cart c where c.cartId=:cartId and c.userId.userId=:userId");
		query.setInteger("cartId", cartId);
		query.setInteger("userId", userId);
		Object count=query.uniqueResult();
		if(count==null) {
			return JsonResult.errorMsg("用户没有这个购物车!!");
		}
		Cart cart=(Cart)count;
		session.delete(cart);
		return JsonResult.ok();
	}

	@Override
	public JsonResult update(Integer userId, Integer cartId, Integer foodNum) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Cart c where c.cartId=:cartId and c.userId.userId=:userId");
		query.setInteger("cartId", cartId);
		query.setInteger("userId", userId);
		Object count=query.uniqueResult();
		if(count==null) {
			return JsonResult.errorMsg("用户没有这个购物车!!");
		}
		Cart cart=(Cart)count;
		//设置购物车被选中,修改购物车数量
		cart.setChecked(Const.CartCheckedEnum.INCHECKED.getCode());
		cart.setUpdateTime(new Date());
		cart.setFoodNum(foodNum);
		
		session.update(cart);
		return JsonResult.ok();
	}

	@Override
	public List<Cart> getCartByChecked(Integer userId) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Cart where userId.userId=:userId and checked=:checked");
		query.setInteger("userId", userId);
		query.setInteger("checked", Const.CartCheckedEnum.INCHECKED.getCode());
		List<Cart> cartList=query.list();
		return cartList;
	}

	@Override
	public Cart getCartByFoodId(Integer userId,Integer foodId) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Cart c where c.userId.userId=:userId and foodId=:foodId");
		query.setInteger("userId", userId);
		query.setInteger("foodId", foodId);
		Cart cart=(Cart)query.uniqueResult();
		return cart;
	}

	@Override
	public int getUserCartSize(Integer userId) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("select count(1) from Cart c where c.userId.userId=:userId");
		query.setInteger("userId", userId);
		Long count=(Long)query.uniqueResult();
		return count.intValue();
	}

	

}
