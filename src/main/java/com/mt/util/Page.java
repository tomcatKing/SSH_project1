package com.mt.util;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.ToString;

/***
 * 分页使用的工具类
 * @author admin
 *
 */
@Data
@ToString
public class Page implements Serializable{

	private Integer currentPage; //当前页

	private Integer pageRows; //每页数据量
	
	private Integer totalRows; //总数据量

	private Integer totalPages; //总页数
	
	private String hql;

	private List<?> list=Lists.newArrayList(); //返回的数据集合
	
	public void setTotalPages() {
		this.totalPages=this.totalRows/pageRows==0 ? 1 : this.totalRows/pageRows+1;
	}
	
	public static Page initPage(Page p,List<?> list,int count) {
		p.setTotalRows(count);
		p.setHql(null);
		p.setList(list);
		p.setTotalPages();
		return p;
	}
}
