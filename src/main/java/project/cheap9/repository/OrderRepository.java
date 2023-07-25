package project.cheap9.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.cheap9.domain.Order;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }
    public List<Order> findAll(String number, String pw) {
        return em.createQuery("select o from Order o where o.number = :number and o.pw = :pw", Order.class)
                .setParameter("number", number)
                .setParameter("pw", pw)
                .getResultList();
    }

}
