package pl.edu.pjwstk.jaz.services;

import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.entities.SectionEntity;
import pl.edu.pjwstk.jaz.requests.SectionRequest;

import javax.persistence.EntityManager;

@Repository
public class SectionService {
    private final EntityManager entityManager;

    public SectionService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public void createSection(SectionRequest sectionRequest){
        var sectionEntity = new SectionEntity();
        sectionEntity.setName(sectionRequest.getName());
        entityManager.persist(sectionEntity);
    }
}
