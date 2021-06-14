package com.toyproject.kithub.repository.order.query;

import com.toyproject.kithub.apicontroller.OrderApiController;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos(){

        List<OrderQueryDto> orders = findOrders();
        orders.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getId());
            o.setOrderItems(orderItems);
        });
        return orders;
    }

    public List<OrderItemQueryDto> findOrderItems(Long orderId){
        return em.createQuery(
                "select new com.toyproject.kithub.repository.order.query.OrderItemQueryDto(oi.order.id,i.name,oi.orderPrice,oi.count) " +
                        " from OrderItem oi " +
                        " join oi.item i " +
                        " where oi.order.id = :orderId"
        ,OrderItemQueryDto.class)
                .setParameter("orderId",orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
           "select new com.toyproject.kithub.repository.order.query.OrderQueryDto(o.id,m.name,o.orderDate,o.status,d.address)" +
                   " from  Order o " +
                   " join o.member m" +
                   " join o.delivery d " ,OrderQueryDto.class)
           .getResultList();
    }


    //Dto를 통한 컬렉션 조회 최적화
    public List<OrderQueryDto> findAllByDto() {
        List<OrderQueryDto> result = findOrders();
        List<Long> orderIds = getOrderIds(result);

        Map<Long, List<OrderItemQueryDto>> orderItemMap = createOrderItemMap(orderIds);

        result.forEach(o->o.setOrderItems(orderItemMap.get(o.getId())));

        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> createOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new com.toyproject.kithub.repository.order.query.OrderItemQueryDto(oi.order.id,i.name,oi.orderPrice,oi.count) " +
                        " from OrderItem oi " +
                        " join oi.item i " +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        //키가 orderId , 벨류는 orderQueryDto인 맵으로 변환시킨다.
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(oi -> oi.getOrderId()));
        return orderItemMap;
    }

    private List<Long> getOrderIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());
        return orderIds;
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery(
                "select new com.toyproject.kithub.repository.order.query.OrderFlatDto(o.id,m.name,o.orderDate,o.status,d.address,i.name,oi.orderPrice,oi.count) " +
                        " from Order o " +
                        " join o.member m" +
                        " join o.delivery d " +
                        " join o.orderItems oi " +
                        " join oi.item i",OrderFlatDto.class)
                .getResultList();
    }
}
