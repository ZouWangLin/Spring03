package cn.itheima.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itheima.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Override
	public void save(Integer parame) {
		System.out.println("数据保存成功了!");
	}

	@Override
	public void update(Integer parame) {
		System.out.println("数据更新成功了!");
	}

}
