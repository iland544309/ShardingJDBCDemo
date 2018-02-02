package com.jone.service;

import com.jone.dao.OrderDao;
import com.jone.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by iland on 2017/12/20.
 */

@Service(value = "OrderDaoService")
public class OrderDaoService implements IService<Order> {
    @Autowired
    OrderDao dao;

    @Override
    public void add(Order entity) {
        System.out.println("add service");
        dao.save(entity);
    }

}
