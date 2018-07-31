package Model;

import BuisnessLogic.Wallet;

public class Codecooler extends User {

    private String roomName;
    private Integer exp;
    private Wallet inventory;

    public Codecooler(String firstName, String lastName, String login, String password, String email){
        super(firstName, lastName, login, password, email);
        this.exp = 0;
        this.roomName = null;
        this.inventory = new Wallet();
    }

    public String getRoomName() {
        return roomName;
    }

    public Integer getExp() {
        return exp;
    }

    public Wallet getInventory() {
        return inventory;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public void setInventory(Wallet inventory) {
        this.inventory = inventory;
    }
}
