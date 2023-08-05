package project.cheap9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.cheap9.domain.Order;
import project.cheap9.repository.OrderRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * 주문
     */
    @Transactional
    public Long saveOrder(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 검색
     */
    public List<Order> findOrders(String number, String pw) {
        return orderRepository.findAll(number, pw);
    }

}
