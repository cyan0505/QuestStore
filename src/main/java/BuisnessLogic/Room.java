package BuisnessLogic;

import Model.Codecooler;
import Model.Mentor;

import java.util.ArrayList;
import java.util.List;

public class Room {

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCodecoolerList(List<Codecooler> codecoolerList) {
        this.codecoolerList = codecoolerList;
    }

    public void setMentorsList(List<Mentor> mentorsList) {
        this.mentorsList = mentorsList;
    }
}
