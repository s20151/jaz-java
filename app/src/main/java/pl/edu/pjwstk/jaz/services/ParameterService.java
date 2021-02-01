package pl.edu.pjwstk.jaz.services;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.jaz.entities.ParameterEntity;
import pl.edu.pjwstk.jaz.requests.ParameterRequest;
import javax.persistence.EntityManager;

@Service
public class ParameterService {
    private final EntityManager entityManager;

    public ParameterService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public void createParameter(ParameterRequest parameterRequest){
        var parameterEntity = new ParameterEntity();
        parameterEntity.setName(parameterRequest.getName());
        entityManager.persist(parameterEntity);
    }
}
