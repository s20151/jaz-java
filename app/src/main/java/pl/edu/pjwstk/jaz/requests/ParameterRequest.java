package pl.edu.pjwstk.jaz.requests;

public class ParameterRequest {
    private String name;
    private String value;

    public ParameterRequest(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getParameterValue() {
        return value;
    }

    public void setParameterValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
