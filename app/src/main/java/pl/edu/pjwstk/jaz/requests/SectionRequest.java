package pl.edu.pjwstk.jaz.requests;

public class SectionRequest {
    private String name;

    public SectionRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
