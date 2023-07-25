package project.cheap9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.cheap9.domain.Item;
import project.cheap9.domain.Order;
import project.cheap9.repository.ItemRepository;
import project.cheap9.repository.OrderRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    /**
     * 주문
     */
//    @Transactional
//    public void SaveOrder(Long itemId, int count, String name, String number,
//                          String zipcode, String dongho, String pw) {
//        Item item = itemRepository.findOne(itemId);
//        order.setCount(count);
//        order.setName(name);
//        order.setNumber(number);
//        order.setZipcode(zipcode);
//        order.setDongho(dongho);
//        order.setPw(pw);
//        order.setStatus(OrderStatus.WAITING);
//        order.setOrderDate(LocalDateTime.now());
//    }

    @Transactional
    public Long saveOrder(Item item, Order order) {
        order.setItem(item);
        orderRepository.save(order);
        return order.getId();
    }

//    @Transactional
//    public Long saveOrder(Long itemId, int count) {
//
//        //엔티티 조회
//        Item item = itemRepository.findOne(itemId);
//
//        //주문 생성
//        Order order = Order.createOrder(item, item.getPrice(), count);
//
//        //주문 저장
//        orderRepository.save(order);
//        return order.getId();
//    }

    /**
     * 주문 검색
     */
    public List<Order> findOrders(String number, String pw) {
        return orderRepository.findAll(number, pw);
    }

}
