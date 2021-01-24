package pl.edu.pjwstk.jaz.services;

import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.jaz.entities.CategoryEntity;
import pl.edu.pjwstk.jaz.requests.CategoryRequest;


import javax.persistence.EntityManager;

@Repository
public class CategoryService {
    private final EntityManager entityManager;

    public CategoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createCategory(CategoryRequest categoryRequest){
        var categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryRequest.getName());
        categoryEntity.setSection_id(categoryRequest.getSection_id());
        entityManager.persist(categoryEntity);
    }

    public boolean doesCategoryExist(String name) {
        if(entityManager.createQuery("SELECT ce FROM CategoryEntity ce WHERE ce.name= :name", CategoryEntity.class)
                .setParameter("name", name)
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
