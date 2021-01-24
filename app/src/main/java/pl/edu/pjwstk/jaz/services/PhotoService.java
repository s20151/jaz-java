package pl.edu.pjwstk.jaz.services;

import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.entities.AuctionPhotoEntity;
import pl.edu.pjwstk.jaz.requests.PhotoRequest;

import javax.persistence.EntityManager;

@Repository
public class PhotoService {
    private final EntityManager entityManager;

    public PhotoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
