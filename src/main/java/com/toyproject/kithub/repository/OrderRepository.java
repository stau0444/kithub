package com.toyproject.kithub.repository;

import com.toyproject.kithub.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    public List<Order>  search(OrderSearch orderSearch){

        // 검색 조건에서 상태값이 있을경우 아래의 쿼리를 타지만
        // 상태값이 없을 경우에 다들고 오게 동적 쿼리가 필요하다 .
        return em.createQuery("select o from Order o join o.member m" +
                                " where o.status = :status " +
                                " and m.name like :name ",Order.class)
                .setParameter("status",orderSearch.getOrderStatus())
                .setParameter("name",orderSearch.getMemberName())
                .getResultList();
    }
    public List<Order> findAll(){
        return em.createQuery("select o from Order o",Order.class).getResultList();
    }

}
