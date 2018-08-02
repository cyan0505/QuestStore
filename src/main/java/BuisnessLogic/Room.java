package BuisnessLogic;

import Model.Codecooler;
import Model.Mentor;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private Integer roomId;
    private String name;
    private List<Mentor> mentorsList;
    private List<Codecooler> codecoolerList;

    public Room(String name){
        this.name = name;
        this.codecoolerList = new ArrayList<Codecooler>();
        this.mentorsList = new ArrayList<Mentor>();
    }

    public String getName() {
        return name;
    }

    public List<Codecooler> getCodecoolerList() {
        return codecoolerList;
    }

    public List<Mentor> getMentorsList() {
        return mentorsList;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCodecoolerToList(Codecooler codecooler) {
        this.codecoolerList.add(codecooler);
    }

    public void addMentorToList(Mentor mentor) {
        this.mentorsList.add(mentor);
    }
}
