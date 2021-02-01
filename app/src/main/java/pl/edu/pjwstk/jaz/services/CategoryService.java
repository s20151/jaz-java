package pl.edu.pjwstk.jaz.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.entities.CategoryEntity;
import pl.edu.pjwstk.jaz.entities.SectionEntity;
import pl.edu.pjwstk.jaz.requests.CategoryRequest;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Repository
public class CategoryService {
    private final EntityManager entityManager;
    private final SectionService sectionService;

    public CategoryService(EntityManager entityManager, SectionService sectionService) {
        this.entityManager = entityManager;
        this.sectionService = sectionService;
    }

    public void createCategory(CategoryRequest categoryRequest, HttpServletResponse response){
        var categoryEntity = new CategoryEntity();
        if(entityManager.find(SectionEntity.class, categoryRequest.getSection_id())!=null) {
            if(findByName(categoryRequest.getName()).isEmpty()) {
                categoryEntity.setName(categoryRequest.getName());
                categoryEntity.setSectionEntity(entityManager.find(SectionEntity.class, categoryRequest.getSection_id()));
                entityManager.persist(categoryEntity);
                response.setStatus(HttpStatus.CREATED.value());
            }else{
                response.setStatus(HttpStatus.CONFLICT.value());
            }
        }else{
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }
    public void editCategory(Long id, CategoryRequest categoryRequest, HttpServletResponse response){
            CategoryEntity categoryFromDB= entityManager.find(CategoryEntity.class, id);
            if(categoryFromDB!=null) {
                categoryFromDB.setName(categoryRequest.getName());
                entityManager.merge(categoryFromDB);
                response.setStatus(HttpStatus.OK.value());
            }else response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    public List<CategoryEntity> findByName(String name){
        return entityManager.createQuery ("SELECT ce FROM CategoryEntity ce WHERE ce.name= :name", CategoryEntity.class)
                .setParameter ("name", name)
                .getResultList();
    }

}
