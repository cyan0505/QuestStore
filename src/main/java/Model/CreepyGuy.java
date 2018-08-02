package Model;

import BuisnessLogic.Room;

import java.util.ArrayList;
import java.util.List;

public class CreepyGuy extends User{


    private List<Room> roomList;
    private List<Mentor> mentorList;

    public CreepyGuy(String firstName, String lastName, String login, String password, String email, String role){
        super(firstName, lastName, login, password, email, role);
        this.roomList = new ArrayList<Room>();
        this.mentorList = new ArrayList<Mentor>();
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public List<Mentor> getMentorList() {
        return mentorList;
    }
}
