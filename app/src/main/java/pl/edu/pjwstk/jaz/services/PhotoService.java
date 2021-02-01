package pl.edu.pjwstk.jaz.services;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class PhotoService {
    private final EntityManager entityManager;

    public PhotoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
