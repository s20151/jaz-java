package pl.edu.pjwstk.jaz.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuctionParameterId implements Serializable {
    private Long auction_id;
    private Long parameter_id;

    public AuctionParameterId(Long auction_id, Long parameter_id) {
        this.auction_id = auction_id;
        this.parameter_id = parameter_id;
    }

    public AuctionParameterId() {
    }

    public Long getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(Long auction_id) {
        this.auction_id = auction_id;
    }

    public Long getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(Long parameter_id) {
        this.parameter_id = parameter_id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        AuctionParameterId that = (AuctionParameterId) o;
        return Objects.equals(auction_id, that.auction_id) &&
                Objects.equals(parameter_id, that.parameter_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auction_id, parameter_id);
    }
}
