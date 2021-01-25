package pl.edu.pjwstk.jaz.entities;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.CascadeType.MERGE;

@Entity
@Table(name="auction_photo")
public class AuctionPhotoEntity {

   /* @EmbeddedId
    private AuctionPhotoId id = new AuctionPhotoId();

    @ManyToOne
    @MapsId("auction_id")
    @JoinColumn(name = "auction_id")
    private AuctionEntity auctionEntity;
*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auction_id;
    private int position;
    private String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(Long auction_id) {
        this.auction_id = auction_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}