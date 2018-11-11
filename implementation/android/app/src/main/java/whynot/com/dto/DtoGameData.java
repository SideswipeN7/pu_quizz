package whynot.com.dto;

public class DtoGameData {
    private String name;
    private int value;

    // to delete
    public DtoGameData(String first, int i) {
        name= first;
        value = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return String.valueOf(value);
    }

    public void setValue(int value) {
        this.value = value;
    }
}
