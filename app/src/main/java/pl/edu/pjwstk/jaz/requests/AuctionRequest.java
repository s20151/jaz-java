package pl.edu.pjwstk.jaz.requests;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AuctionRequest {
    @NotNull
    private  String title;
    @NotNull
    private  String description;
    @NotNull
    private  int price;
    @NotNull
    private  String categoryName;

    private List<ParameterRequest> parameters;
    private List<PhotoRequest> photos;

    public List<PhotoRequest> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoRequest> photos) {
        this.photos = photos;
    }

    public List<ParameterRequest> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterRequest> parameters) {
        this.parameters = parameters;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
