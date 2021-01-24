package pl.edu.pjwstk.jaz.requests;

public class PhotoRequest {
    private String link;

    public PhotoRequest(String link, int position) {
        this.link = link;
    }

    public PhotoRequest() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
