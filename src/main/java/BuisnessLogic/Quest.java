package BuisnessLogic;

public class Quest {

    private String name;
    private String description;
    private Integer reward;
    private Boolean isDone;
    private Boolean isExtra;

    public Quest(String name, String description, Integer reward, Boolean isExtra){
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.isDone = false;
        this.isExtra = isExtra;
    }

    public String getName() {
        return name;
    }

    public Boolean getDone() {
        return isDone;
    }

    public Boolean getExtra() {
        return isExtra;
    }

    public Integer getReward() {
        return reward;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public void setExtra(Boolean extra) {
        isExtra = extra;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }
}
