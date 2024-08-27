package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.*;
import com.pivotree.appzone.enums.OrderType;
import com.pivotree.appzone.enums.Status;
import com.pivotree.appzone.intemplate.OrderInput;
import com.pivotree.appzone.repository.CustomerRepository;
import com.pivotree.appzone.repository.OrderLineRepository;
import com.pivotree.appzone.repository.OrderManager;
import com.pivotree.appzone.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.pivotree.appzone.intemplate.PaginationParams;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    OrderWithFulfillment orderWithFulfillment;
    @Autowired
    CustomerService customerService;
    @Autowired
    AddressService addressService;
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private EntityManager entityManager;
    /**
     * Creates a new order with the given order details.
     *
     * @param order the order details
     * @return the created order
     * @throws RuntimeException if the customer is not present or the order lines are empty
     */
    public Order createOrder(OrderInput order) throws RuntimeException {
        if(order.getOrderLines().isEmpty()) throw new RuntimeException("Orderlines are empty");
        if(customerRepository.existsById(order.getCustomer().getId())) {
            Customer customer=customerService.findByCustomerID(order.getCustomer().getId());
            Order order1=new Order(order.getOrderReference(),order.getOrderType(),order.getOrderLines(),order.getShippingAddress(),order.getBillingAddress(),customer,order.getDeliveryType());
            List<OrderLine>orderLines=order1.getOrderLines();
            Double orderTotal=0.0;
            for(OrderLine orderLine:orderLines){
                orderLine.setLineTotal((orderLine.getUnitPrice()*orderLine.getQuantity()));
                orderTotal+=orderLine.getLineTotal();
                orderLineRepository.save(orderLine);
            }
            order1.setOrderTotal(orderTotal);
            addressService.save(order1.getBillingAddress());

            addressService.save(order1.getShippingAddress());
            order1.setStatus(Status.valueOf("CREATED"));
            orderRepository.save(order1);
            return orderWithFulfillment.createOrderWithFulfillment(order1);

        }
        else {
            throw new RuntimeException("Customer is not present");
        }

    }

    public Order findOrderByFulfillmentId(Integer fulfillmentId) {

        List<Order> orderList = orderRepository.findAll();

        for(Order order: orderList) {
            for (FulFillment fulFillment: order.getFulfillments()) {
                if(fulFillment.getFulfillmentId() == fulfillmentId) {
                    return order;
                }
            }
        }
        return null;
    }


//    public List<Order> getOrders(String username) {
//        return orderRepository.getOrders(username);
//    }

    public List<Order> getAllOrdersByOrderType(String orderType) {
        OrderType type = OrderType.valueOf(orderType);
        return orderRepository.findAllByOrderType(type);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Page<Order> getLatestOrdersForCustomer(PaginationParams paginationInfo) {


//        Optional<Customer> customer = Optional.ofNullable(customerService.getCustomerByUsername(paginationInfo.getUsername()));
        // Create CriteriaBuilder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Create CriteriaQuery for Order
        CriteriaQuery<Order> orderQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = orderQuery.from(Order.class);

        CriteriaQuery<User> userQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = userQuery.from(User.class);

        CriteriaQuery<Customer> customerQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> customerRoot =customerQuery.from(Customer.class);

        Predicate userPredicate = criteriaBuilder.equal(userRoot.get("username"),paginationInfo.getUsername());
        userQuery.where(userPredicate);
        TypedQuery<User> userTypedQuery=entityManager.createQuery(userQuery);
        List<User> user = userTypedQuery.getResultList();
        if (user.isEmpty()){

        }

        Predicate customerPredicate=criteriaBuilder.equal(customerRoot.get("email"),user.get(0).getEmail());
        customerQuery.where(customerPredicate);
        TypedQuery<Customer> customerTypedQuery= entityManager.createQuery(customerQuery);
        List<Customer> customers=customerTypedQuery.getResultList();

        Predicate orderPredicate=criteriaBuilder.equal(orderRoot.get("customer").get("id"),customers.get(0).getId());
        orderQuery.where(orderPredicate);
        orderQuery.orderBy(criteriaBuilder.desc(orderRoot.get("id")));
        TypedQuery<Order> orderTypedQuery= entityManager.createQuery(orderQuery);
        List<Order> orders = orderTypedQuery.setFirstResult(paginationInfo.getPage()).setMaxResults(paginationInfo.getSize()).getResultList();


        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Order.class)));
        Long totalRecords = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(orders, PageRequest.of(paginationInfo.getPage(), paginationInfo.getSize()), totalRecords);

    }

    public Page<Order> getLatestOrdersByOrderType(String orderType, Pageable pageable) {
        return orderRepository.findByOrderTypeOrderByOrderDateDesc(orderType, pageable);
    }
}
