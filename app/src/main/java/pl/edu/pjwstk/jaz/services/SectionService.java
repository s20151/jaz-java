package pl.edu.pjwstk.jaz.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.entities.SectionEntity;
import pl.edu.pjwstk.jaz.requests.SectionRequest;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

@Repository
public class SectionService {
    private final EntityManager entityManager;

    public SectionService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createSection(SectionRequest sectionRequest, HttpServletResponse response){
        var sectionEntity = new SectionEntity();
        sectionEntity.setName(sectionRequest.getName());
        entityManager.persist(sectionEntity);
        response.setStatus(HttpStatus.OK.value());
    }
    public void editSection(Long id, SectionRequest sectionRequest, HttpServletResponse response){
        SectionEntity sectionFromDB;
        try {
            sectionFromDB = getSection(id);
            sectionFromDB.setName(sectionRequest.getName());
            entityManager.merge(sectionFromDB);
            response.setStatus(HttpStatus.OK.value());
        }catch(NoResultException e){
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    public SectionEntity getSection(Long id){
        return entityManager.createQuery ("SELECT section FROM SectionEntity section WHERE section.id= :id", SectionEntity.class)
                .setParameter ("id", id)
                .getSingleResult ();
    }
}
