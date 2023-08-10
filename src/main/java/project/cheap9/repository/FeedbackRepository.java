package project.cheap9.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.cheap9.domain.Feedback;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedbackRepository {

    private final EntityManager em;

    public void save(Feedback feedback) {
        em.persist(feedback);
    }

    public List<Feedback> findAll() {
        return em.createQuery("select f from Feedback f", Feedback.class)
                .getResultList();
    }
}
