package project.cheap9.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.cheap9.domain.Item;
import project.cheap9.domain.Order;
import project.cheap9.domain.OrderStatus;
import project.cheap9.exception.NotEnoughStockException;
import project.cheap9.repository.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Item item = createItem("생수 2L 6개", 1400, 25);

        Order order = createOrder(item, 2, "천승범", "01049969685",
                "01010", "101-101", "1234");

        //when
        Long orderId = orderService.saveOrder(order);
//        Long orderId = orderService.order(item.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 WAITING", OrderStatus.WAITING, getOrder.getStatus());
        assertEquals("주문 가격은 가격 * 수량이다.", 1400 * 2, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 23, item.getStockQuantity());

    }
    
    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Item item = createItem("생수 2L 6개", 1400, 1);

        int orderCount = 2;
        
        //when
//        orderService.saveOrder(item.getId(), orderCount);
        
        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    private Item createItem(String name, int price, int stockQuantity) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        em.persist(item);
        return item;
    }

    private Order createOrder(Item item, int count, String name, String number,
                             String zipcode, String dongho, String pw) {
        Order order = new Order();
        order.setItem(item);
        order.setCount(count);
        order.setName(name);
        order.setNumber(number);
        order.setZipcode(zipcode);
        order.setDongho(dongho);
        order.setPw(pw);
        em.persist(order);
        return order;
    }

}
