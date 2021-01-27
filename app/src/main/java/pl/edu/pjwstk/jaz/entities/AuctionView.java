package pl.edu.pjwstk.jaz.entities;

import java.util.HashMap;

public class AuctionView {
    private Long id;
    private String title;
    private String description;
    private String category_name;
    private int price;
    private int version;
    private String miniature;
    private HashMap<String, String> parameters;

    public AuctionView(AuctionEntity auctionEntity, String miniature, HashMap<String, String> parameters) {
        this.id = auctionEntity.getId();
        this.title = auctionEntity.getTitle();
        this.description = auctionEntity.getDescription();
        this.category_name = auctionEntity.getCategoryEntity().getName();
        this.price = auctionEntity.getPrice();
        this.version = auctionEntity.getVersion();
        this.miniature = miniature;
        this.parameters = parameters;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }


    public String getMiniature() {
        return miniature;
    }

    public void setMiniature(String miniature) {
        this.miniature = miniature;
    }
}
