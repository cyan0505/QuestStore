package BuisnessLogic;

public class Artifact {

    private String name;
    private String description;
    private Integer price;
    private Boolean isUsed;

    public Artifact(String name, String description, Integer price, Boolean isUsed) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isUsed = isUsed;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public Integer getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }
}
