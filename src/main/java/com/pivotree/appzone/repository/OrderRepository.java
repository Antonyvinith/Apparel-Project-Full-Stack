//package com.pivotree.appzone.repository;
//
//import com.pivotree.appzone.entity.Order;
//import com.pivotree.appzone.enums.OrderType;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface OrderRepository extends JpaRepository<Order,Integer> {
////    @Query("SELECT O FROM Orders WHERE O.customer.user.username =:username")
////    List<Order> getOrders(String username);
//
//
//    @Query("SELECT o FROM Order o WHERE o.orderType = ?1")
//    List<Order> findAllByOrderType(OrderType orderType);
//}



package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Order;
import com.pivotree.appzone.enums.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {


    @Query("SELECT o FROM Order o WHERE o.orderType = ?1")
    List<Order> findAllByOrderType(OrderType orderType);

    @Query(value = "SELECT * FROM customer_order o WHERE o.order_type = ?1 ORDER BY id DESC", nativeQuery = true)
    Page<Order> findByOrderTypeOrderByOrderDateDesc(String orderType, Pageable pageable);


}
