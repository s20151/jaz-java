package pl.edu.pjwstk.jaz.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import pl.edu.pjwstk.jaz.entities.*;
import pl.edu.pjwstk.jaz.requests.AuctionRequest;
import pl.edu.pjwstk.jaz.requests.EditRequest;
import pl.edu.pjwstk.jaz.requests.ParameterRequest;
import pl.edu.pjwstk.jaz.requests.PhotoRequest;
import pl.edu.pjwstk.jaz.user.User;
import pl.edu.pjwstk.jaz.user.UserEntity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class AuctionService {

    private final EntityManager entityManager;
    private final CategoryService categoryService;

    public AuctionService(EntityManager entityManager, CategoryService categoryService) {
        this.entityManager = entityManager;
        this.categoryService = categoryService;
    }

    public AuctionView viewAuction(Long id, HttpServletResponse response){
            AuctionEntity auction = entityManager.find(AuctionEntity.class, id);
            if(auction==null){
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }else {
                String miniatureUrl;
                try {
                    miniatureUrl = getAuctionMiniature(auction).getLink();
                }catch(NoResultException e) {
                    miniatureUrl = "No photo";
                }
                HashMap<String, String> auctionParameters = new HashMap<>();
                try {
                    auctionParameters = getAuctionParametersAsHashMap(auction);
                }catch(NoResultException e) {
                    e.printStackTrace();
                }
                response.setStatus(HttpStatus.OK.value());
                return new AuctionView(auction,miniatureUrl,auctionParameters);
            }
    }

    public void createAuction(@Valid @RequestBody AuctionRequest auctionRequest, HttpServletResponse response) {
        AuctionEntity auction = new AuctionEntity();
        auction.setVersion(1);
        if(auctionRequest.getTitle()==null
        ||auctionRequest.getDescription()==null
        ||auctionRequest.getCategoryName()==null
        ||auctionRequest.getPrice()<1){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }else {
            auction.setTitle(auctionRequest.getTitle());
            auction.setDescription(auctionRequest.getDescription());
            auction.setPrice(auctionRequest.getPrice());
            UserEntity currentUser = findCurrentUser();
            auction.setUserEntity(currentUser);
            CategoryEntity category;
            if(!categoryService.findByName(auctionRequest.getCategoryName()).isEmpty()) {
                category = categoryService.findByName(auctionRequest.getCategoryName()).get(0);
                auction.setCategoryEntity(category);
                entityManager.persist(auction);
                try {
                    setAuctionParameters(auctionRequest.getParameters(), auction);
                }catch(NullPointerException e) {
                    e.printStackTrace();
                }
                try {
                    setAuctionPhotos(auctionRequest.getPhotos(), auction);
                }catch(NullPointerException e) {
                    e.printStackTrace();
                }
                response.setStatus(HttpStatus.CREATED.value());
            }else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            }
        }
    }
    public void editAuction(Long id, EditRequest auctionRequest, HttpServletResponse response) {
        UserEntity currentUser = findCurrentUser();
        AuctionEntity auctionFromDb = entityManager.find(AuctionEntity.class, id);
        if(auctionFromDb==null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }else {
            if(currentUser.getId()==auctionFromDb.getUserEntity().getId() || currentUser.getAuthorities().contains("admin")) {
                if(auctionFromDb.getVersion() != auctionRequest.getVersion()) { //TODO
                    response.setStatus(HttpStatus.CONFLICT.value());
                }else {
                    if(auctionRequest.getCategoryName() != null) {
                        CategoryEntity category;
                        if(!categoryService.findByName(auctionRequest.getCategoryName()).isEmpty()) {
                            category = categoryService.findByName(auctionRequest.getCategoryName()).get(0);
                            auctionFromDb.setCategoryEntity(category);
                        }
                    }
                    if(auctionRequest.getTitle() != null) {
                        auctionFromDb.setTitle(auctionRequest.getTitle());
                    }
                    if(auctionRequest.getDescription() != null) {
                        auctionFromDb.setDescription(auctionRequest.getDescription());
                    }
                    if(auctionRequest.getPrice() > 0) {
                        auctionFromDb.setPrice(auctionRequest.getPrice());
                    }
                    if(auctionRequest.getParameters() != null && !auctionRequest.getParameters().isEmpty()) {
                        List<ParameterRequest> parametersFromRequest = auctionRequest.getParameters();
                        List<AuctionParameterEntity> allAuctionParameters = getAllAuctionParameters(auctionFromDb);
                        for(ParameterRequest parameterRequest : parametersFromRequest) {
                            ParameterEntity parameter;
                            if(doesParameterExist(parameterRequest)) {
                                parameter = getExistingParameter(parameterRequest);
                                boolean auctionParameterEntityPresentInDB = false;
                                for(AuctionParameterEntity entity : allAuctionParameters) {
                                    if(entity.getId().getParameter_id() == parameter.getId()) {
                                        entity.setValue(parameterRequest.getParameterValue());
                                        entityManager.merge(entity);
                                        auctionParameterEntityPresentInDB = true;
                                    }
                                }
                                if(!auctionParameterEntityPresentInDB) {
                                    createAuctionParameter(auctionFromDb, parameterRequest, parameter);
                                }
                            }else {
                                parameter = new ParameterEntity();
                                parameter.setName(parameterRequest.getName());
                                entityManager.persist(parameter);
                                createAuctionParameter(auctionFromDb, parameterRequest, parameter);
                            }
                        }
                    }
                    if(auctionRequest.getPhotos() != null && !auctionRequest.getPhotos().isEmpty()) {
                        setAuctionPhotos(auctionRequest.getPhotos(), auctionFromDb);
                    }
                    auctionFromDb.setVersion(auctionFromDb.getVersion()+1);
                    entityManager.merge(auctionFromDb);
                    response.setStatus(HttpStatus.OK.value());
                }
            }else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }

    private void createAuctionParameter(AuctionEntity auctionFromDb, ParameterRequest parameterRequest, ParameterEntity parameter) {
        var auctionParameter = new AuctionParameterEntity();
        AuctionParameterId auctionParameterID = new AuctionParameterId(auctionFromDb.getId(), parameter.getId());
        auctionParameter.setId(auctionParameterID);
        auctionParameter.setAuctionEntity(auctionFromDb);
        auctionParameter.setParameterEntity(parameter);
        auctionParameter.setValue(parameterRequest.getParameterValue());
        entityManager.persist(auctionParameter);
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User) {
            String username = ((User) principal).getUsername();
            return username;
        }else {
            return principal.toString();
        }
    }

    public void setAuctionParameters(List<ParameterRequest> parameters, AuctionEntity auction){
        for(ParameterRequest parameterRequest : parameters){
            ParameterEntity parameter;
            if(doesParameterExist(parameterRequest)){
                parameter = getExistingParameter(parameterRequest);
            }else {
                parameter = new ParameterEntity();
                parameter.setName(parameterRequest.getName());
                entityManager.persist(parameter);
            }
            createAuctionParameter(auction, parameterRequest, parameter);
        }
    }

    public void setAuctionPhotos(List<PhotoRequest> photos, AuctionEntity auction){
        int position = getAllAuctionPhotos(auction).size();
        for(PhotoRequest photoRequest : photos){
            var photo = new AuctionPhotoEntity();
            photo.setAuctionEntity(auction);
            photo.setLink(photoRequest.getLink());
            photo.setPosition(position);
            position++;
            entityManager.persist(photo);
        }
    }

    public List<AuctionView> viewAllAuctions(HttpServletResponse response){
        List<AuctionEntity> list = entityManager.createQuery ("SELECT auction FROM AuctionEntity auction ", AuctionEntity.class)
                .getResultList();
        List<AuctionView> auctionViewList = new ArrayList<>();
        for(AuctionEntity auction : list){
            AuctionView auctionView = viewAuction(auction.getId(),response);
            auctionViewList.add(auctionView);
        }
        if(auctionViewList.isEmpty()){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }else {
            response.setStatus(HttpStatus.OK.value());
            return auctionViewList;
        }
    }
    public AuctionPhotoEntity getAuctionMiniature(AuctionEntity auction){
        return entityManager.createQuery ("SELECT photo FROM AuctionPhotoEntity photo WHERE photo.auctionEntity.id= :id AND photo.position=0", AuctionPhotoEntity.class)
                .setParameter ("id", auction.getId())
                .getSingleResult ();
    }
    public List<AuctionPhotoEntity> getAllAuctionPhotos(AuctionEntity auction){
        return entityManager.createQuery ("SELECT photo FROM AuctionPhotoEntity photo WHERE photo.auctionEntity.id= :id", AuctionPhotoEntity.class)
                .setParameter ("id", auction.getId())
                .getResultList ();
    }
    public boolean doesParameterExist (ParameterRequest parameterRequest){
        if (entityManager.createQuery ("SELECT pe FROM ParameterEntity pe WHERE pe.name= :name", ParameterEntity.class)
                .setParameter ("name", parameterRequest.getName())
                .getResultList ().isEmpty ()) return false;
        else return true;
    }

    public List<AuctionParameterEntity> getAllAuctionParameters(AuctionEntity auction){
        return entityManager.createQuery("SELECT ap FROM AuctionParameterEntity ap WHERE ap.auctionEntity.id= :id", AuctionParameterEntity.class)
                .setParameter("id", auction.getId())
                .getResultList();
    }
    public ParameterEntity getExistingParameter(ParameterRequest parameterRequest){
        return entityManager.createQuery ("SELECT pe FROM ParameterEntity pe WHERE pe.name= :name", ParameterEntity.class)
                .setParameter ("name", parameterRequest.getName())
                .getSingleResult ();
    }

    public UserEntity findCurrentUser(){
        return entityManager.createQuery ("SELECT ue FROM UserEntity ue WHERE ue.username= :username", UserEntity.class)
                .setParameter ("username", getCurrentUsername())
                .getSingleResult ();
    }
    public HashMap<String,String> getAuctionParametersAsHashMap(AuctionEntity auction){
        HashMap<String,String> parametersHashMap = new HashMap<>();
        List<AuctionParameterEntity> auctionParametersValues = entityManager.createQuery("SELECT parameters FROM AuctionParameterEntity parameters WHERE parameters.auctionEntity.id = :id", AuctionParameterEntity.class)
                .setParameter("id", auction.getId())
                .getResultList();
        for(AuctionParameterEntity auctionParameter : auctionParametersValues){
            ParameterEntity parameterName = entityManager.createQuery("SELECT parameters FROM ParameterEntity parameters WHERE parameters.id = :id ", ParameterEntity.class)
                    .setParameter("id", auctionParameter.getId().getParameter_id())
                    .getSingleResult();
            parametersHashMap.put(parameterName.getName(),auctionParameter.getValue());
        }
        return parametersHashMap;
    }
}
