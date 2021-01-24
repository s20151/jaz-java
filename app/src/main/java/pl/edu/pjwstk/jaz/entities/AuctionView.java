package pl.edu.pjwstk.jaz.entities;

import java.util.HashMap;

public class AuctionView {
    private AuctionEntity auctionEntity;
    private String miniature;
    private HashMap<String, String> parameters;

    public AuctionView(AuctionEntity auctionEntity, String miniature, HashMap<String, String> parameters) {
        this.auctionEntity = auctionEntity;
        this.miniature = miniature;
        this.parameters = parameters;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public AuctionEntity getAuctionEntity() {
        return auctionEntity;
    }

    public void setAuctionEntity(AuctionEntity auctionEntity) {
        this.auctionEntity = auctionEntity;
    }

    public String getMiniature() {
        return miniature;
    }

    public void setMiniature(String miniature) {
        this.miniature = miniature;
    }
}
