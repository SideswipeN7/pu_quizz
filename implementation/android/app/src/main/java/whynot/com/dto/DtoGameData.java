package whynot.com.dto;

public class DtoGameData {
    private String name;
    private int value;

    public DtoGameData() {
        this.name = "";
        this.value = 0;
    }

    public DtoGameData(String nick, int points) {
        name = nick;
        value = points;
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
}// class DtoGameData
