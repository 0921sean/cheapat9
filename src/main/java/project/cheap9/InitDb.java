package project.cheap9;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.cheap9.domain.Item;
import project.cheap9.domain.Order;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        //initService.dbInit1();
        //initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {
            Item item = createItem("햇반", 200, 100, 200,
                    LocalDateTime.of(2023, 1, 1, 0, 0),
                    LocalDateTime.of(2023, 1, 31, 0, 0));
            em.persist(item);

            Order order1 = Order.createOrder(item, 150, 1, "userA", "0101", "152", "서울특별시", "3010", "999", passwordEncoder);
            em.persist(order1);

        }

        public void dbInit2() {
            Item item = createItem("생수", 250, 200, 300,
                    LocalDateTime.of(2023, 1, 1, 0, 0),
                    LocalDateTime.of(2023, 1, 31, 0, 0));
            em.persist(item);

            Order order2 = Order.createOrder(item, 200, 2, "userB", "0102", "182", "경기도", "3011", "9999", passwordEncoder);
            em.persist(order2);

        }

        private Item createItem(String name, int originalPrice, int price, int stockQuantity, LocalDateTime startDate, LocalDateTime endDate) {
            Item item = new Item();
            item.setName(name);
            item.setOriginalPrice(originalPrice);
            item.setPrice(price);
            item.setDiscountRate((originalPrice - price) * 100 / originalPrice);
            item.setStockQuantity(stockQuantity);
            item.setStartDate(startDate);
            item.setEndDate(endDate);
            return item;
        }


    }

}
