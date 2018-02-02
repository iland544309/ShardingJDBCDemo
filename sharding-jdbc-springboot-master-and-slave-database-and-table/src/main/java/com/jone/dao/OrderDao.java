package com.jone.dao;

import com.jone.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by iland on 2018/1/30.
 */


public interface OrderDao extends
        JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order>,
        PagingAndSortingRepository<Order, Long>,
        Serializable {
}