package cn.itheima.service;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itheima.dao.BaseDao;
/**
 * 所有service层的父类
 */
public class BaseService<T> {
	@Autowired
	public BaseDao<T> baseDao;
	
	public void save() {
		System.out.println("数据正在保存!");
		System.out.println(baseDao);
	}

}
