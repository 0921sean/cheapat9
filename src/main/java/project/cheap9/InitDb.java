package project.cheap9;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.cheap9.domain.Item;
import project.cheap9.domain.Order;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Item item = createItem("햇반", 100, 200);
            em.persist(item);

            Order order1 = Order.createOrder(item, 150, 1, "userA", "0101", "152", "3010", "999");
            em.persist(order1);

        }

        public void dbInit2() {
            Item item = createItem("생수", 200, 300);
            em.persist(item);

            Order order2 = Order.createOrder(item, 200, 2, "userB", "0102", "182", "3011", "9999");
            em.persist(order2);

        }

        private Item createItem(String name, int price, int stockQuantity) {
            Item item = new Item();
            item.setName(name);
            item.setPrice(price);
            item.setStockQuantity(stockQuantity);
            return item;
        }


    }

}
