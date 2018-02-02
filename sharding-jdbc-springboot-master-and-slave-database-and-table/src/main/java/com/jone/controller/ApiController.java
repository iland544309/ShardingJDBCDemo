package com.jone.controller;

import com.jone.async.DbAsyncTask;
import com.jone.dao.OrderDao;
import com.jone.entity.Order;
import com.jone.service.OrderDaoService;
import com.jone.utils.date.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by iland on 2018/2/1.
 */

@RestController
@RequestMapping("/api")
public class ApiController {


    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderDaoService orderDaoService;

    @Autowired
    private DbAsyncTask<Order> asyncTask;


    @RequestMapping(value = "/order/add", produces = "application/json; charset=utf-8")
    public String addOrder(){
        long startTime = System.currentTimeMillis();
        for(int i=0; i<30; i++){
            Order order = new Order();
            int rand = new Random().nextInt(31);
            int rUser = new Random().nextInt(2);
            Timestamp t = new Timestamp(System.currentTimeMillis());
            if(i % 3 == 1){
                t = new Timestamp(System.currentTimeMillis() + 2592000000L);
            }
            else if(i % 3 == 2){
                t = new Timestamp(System.currentTimeMillis() + 5184000000L);
            }

            order.setAddTime(t);

            order.setStatus("1");
            order.setUserId(10+rUser);
            Date d = new Date(t.getTime());

            int date = DateFormat.changeDate2Int(d);
            System.out.println("date -> :" + date);
            order.setCurrentDate(date);
            asyncTask.doInsertDbTask(orderDaoService, order);
        }
        long endTime = System.currentTimeMillis();
        long diffTime = (endTime - startTime) / 1000;
        return String.valueOf(diffTime) ;
    }

    @RequestMapping(value = "/order/list", produces = "application/json; charset=utf-8")
    public List<Order> listOrder(){
        return orderDao.findAll();
    }

    @RequestMapping(value = "/order/search/{user_id}", produces = "application/json; charset=utf-8")
    public List<Order> searchOrder(@PathVariable("user_id") Integer user_id){
        final int userid = user_id;
        List<Order> list = orderDao.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Integer> userPath = root.get("userId");
                criteriaQuery.where(criteriaBuilder.equal(userPath, userid));
                return null;
            }
        });

        return list;
    }

}
