package pl.edu.pjwstk.jaz.requests;

import javax.validation.constraints.NotNull;
import java.util.List;

public class EditRequest {

    @NotNull
    private  String title;
    @NotNull
    private  String description;
    @NotNull
    private  int price;
    @NotNull
    private  String categoryName;
    private int version;

    private List<ParameterRequest> parameters;
    private List<PhotoRequest> photos;

    public EditRequest(@NotNull String title, @NotNull String description, @NotNull int price, @NotNull String categoryName, List<ParameterRequest> parameters, List<PhotoRequest> photos, int version) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
        this.parameters = parameters;
        this.photos = photos;
        this.version = version;
    }

    public EditRequest() {
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

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
