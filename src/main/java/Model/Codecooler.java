package Model;

import BuisnessLogic.Artifact;
import BuisnessLogic.Wallet;

import java.util.ArrayList;

public class Codecooler extends User {

    private String roomName;
    private Integer exp;
    private Wallet inventory;

    public Codecooler(String firstName, String lastName, String login, String password, String email, String role, int userId){
        super(firstName, lastName, login, password, email, role, userId);
        this.exp = 0;
        this.roomName = null;
        this.inventory = null;
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

    public void setInventory(ArrayList<Artifact> artifactList, Integer coins){
        this.inventory = new Wallet(artifactList, coins);
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
