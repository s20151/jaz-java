package pl.edu.pjwstk.jaz.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "auction_parameter")
public class AuctionParameterEntity implements Serializable {
    @EmbeddedId
    private AuctionParameterId id = new AuctionParameterId();

    @ManyToOne
    @MapsId("auction_id")
    @JoinColumn(name = "auction_id")
    private AuctionEntity auctionEntity;

    @ManyToOne
    @MapsId("parameter_id")
    @JoinColumn(name = "parameter_id")
    private ParameterEntity parameterEntity;

    private String value;

    public AuctionParameterId getId() {
        return id;
    }

    public void setId(AuctionParameterId id) {
        this.id = id;
    }

    public AuctionEntity getAuctionEntity() {
        return auctionEntity;
    }

    public void setAuctionEntity(AuctionEntity auctionEntity) {
        this.auctionEntity = auctionEntity;
    }

    public ParameterEntity getParameterEntity() {
        return parameterEntity;
    }

    public void setParameterEntity(ParameterEntity parameterEntity) {
        this.parameterEntity = parameterEntity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
