package project.cheap9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.cheap9.domain.Item;
import project.cheap9.domain.Order;
import project.cheap9.domain.OrderStatus;
import project.cheap9.repository.ItemRepository;
import project.cheap9.repository.OrderRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;

    /**
     * 주문
     */
    @Transactional
    public Long saveOrder(Item item, Order order) {
        itemService.update(item.getId(), item.getName(), item.getOriginalPrice(), item.getPrice(), item.getStockQuantity(), item.getStartDate().toString(), item.getEndDate().toString());
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 검색
     */
    public List<Order> findOrders() {
        return orderRepository.findAll();
    }
    public List<Order> findOrdersByNumber(String number) {
        return orderRepository.findAllByNumber(number);
    }

    public Order findOne(Long id) {
        return orderRepository.findOne(id);
    }

    /**
     * 주문 수정
     */
    @Transactional
    public void update(Long id, OrderStatus status) {
        Order order = orderRepository.findOne(id);
        order.setStatus(status);
    }

}
