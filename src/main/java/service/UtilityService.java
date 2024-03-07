package service;

import exception.SleepQueryException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import org.postgresql.util.PSQLException;

@ApplicationScoped
@Transactional
public class UtilityService {

    EntityManager em;

    @Inject
    public UtilityService(EntityManager em) {
        this.em = em;
    }

    public void sleep(Long time) {
        String query = "select pg_sleep(:time)";
        long executeTime = 0L;
        Long start = System.currentTimeMillis();
        try {
            em.createNativeQuery(query)
                    .setParameter("time", time)
                    .executeUpdate();
        } catch (RuntimeException e) {
            Long end = System.currentTimeMillis();
            executeTime = end - start;
            throw new SleepQueryException(executeTime);
        }
    }
}
