package pl.edu.pjwstk.jaz.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.entities.CategoryEntity;
import pl.edu.pjwstk.jaz.entities.SectionEntity;
import pl.edu.pjwstk.jaz.requests.CategoryRequest;
import pl.edu.pjwstk.jaz.requests.SectionRequest;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

@Repository
public class CategoryService {
    private final EntityManager entityManager;

    public CategoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createCategory(CategoryRequest categoryRequest, HttpServletResponse response){
        var categoryEntity = new CategoryEntity();
        if(doesSectionExist(categoryRequest.getSection_id())) {
            categoryEntity.setName(categoryRequest.getName());
            categoryEntity.setSection_id(categoryRequest.getSection_id());
            entityManager.persist(categoryEntity);
            response.setStatus(HttpStatus.OK.value());
        }else{
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }
    public void editCategory(Long id, CategoryRequest categoryRequest, HttpServletResponse response){
        CategoryEntity categoryFromDB;
        try {
            categoryFromDB = getCategory(id);
            categoryFromDB.setName(categoryRequest.getName());
            entityManager.merge(categoryFromDB);
            response.setStatus(HttpStatus.OK.value());
        }catch(NoResultException e){
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
    public CategoryEntity getCategory(Long id){
        return entityManager.createQuery ("SELECT category FROM CategoryEntity category WHERE category.id= :id", CategoryEntity.class)
                .setParameter ("id", id)
                .getSingleResult ();
    }
    public boolean doesCategoryExist(String name) {
        if(entityManager.createQuery("SELECT ce FROM CategoryEntity ce WHERE ce.name= :name", CategoryEntity.class)
                .setParameter("name", name)
                .getResultList().isEmpty()) return false;
        else return true;
    }
    public boolean doesSectionExist(Long id) {
        if(entityManager.createQuery("SELECT section FROM SectionEntity section WHERE section.id= :id", SectionEntity.class)
                .setParameter("id", id)
                .getResultList().isEmpty()) return false;
        else return true;
    }
    public CategoryEntity findByName(String name){
        return entityManager.createQuery ("SELECT ce FROM CategoryEntity ce WHERE ce.name= :name", CategoryEntity.class)
                .setParameter ("name", name)
                .getSingleResult ();
    }

    public void createNewCategoryFromString(String name) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);
        entityManager.persist(categoryEntity);
    }
}
