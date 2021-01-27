package pl.edu.pjwstk.jaz.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.entities.SectionEntity;
import pl.edu.pjwstk.jaz.requests.SectionRequest;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Repository
public class SectionService {
    private final EntityManager entityManager;

    public SectionService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createSection(SectionRequest sectionRequest, HttpServletResponse response){
        var sectionEntity = new SectionEntity();
        if(findByName(sectionRequest.getName()).isEmpty()) {
            sectionEntity.setName(sectionRequest.getName());
            entityManager.persist(sectionEntity);
            response.setStatus(HttpStatus.OK.value());
        }else{
            response.setStatus(HttpStatus.CONFLICT.value());
        }
    }
    public void editSection(Long id, SectionRequest sectionRequest, HttpServletResponse response){
        SectionEntity sectionFromDB = entityManager.find(SectionEntity.class, id);
        if(sectionFromDB!=null){
            sectionFromDB.setName(sectionRequest.getName());
            entityManager.merge(sectionFromDB);
            response.setStatus(HttpStatus.OK.value());
        }else{
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    public List<SectionEntity> findByName(String name){
        return entityManager.createQuery ("SELECT section FROM SectionEntity section WHERE section.name= :name", SectionEntity.class)
                .setParameter ("name",name)
                .getResultList();
    }
}
