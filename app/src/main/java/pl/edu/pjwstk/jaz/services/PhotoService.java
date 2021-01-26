package pl.edu.pjwstk.jaz.services;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
public class PhotoService {
    private final EntityManager entityManager;

    public PhotoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
