package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class OrderManager {


        @PersistenceContext
        private EntityManager entityManager;
        public void save(Order order){
            entityManager.persist(order);
        }
}


//package com.pivotree.appzone.repository;
//
//import com.pivotree.appzone.entity.Order;
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
////import javax.transaction.Transactional;
//
//@Component
//public class OrderManager {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Transactional
//    public void save(Order order) {
//        entityManager.persist(order);
//    }
//}
