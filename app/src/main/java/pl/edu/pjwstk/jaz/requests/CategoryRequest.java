package pl.edu.pjwstk.jaz.requests;

public class CategoryRequest {
    private String name;
    private Long section_id;

    public Long getSection_id() {
        return section_id;
    }

    public void setSection_id(Long section_id) {
        this.section_id = section_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
