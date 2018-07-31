package BuisnessLogic;

import Model.Codecooler;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private Artifact artifactGroupWantsToBuy;
    private List<Codecooler> codecoolerList;

    public Group(Artifact artifactGroupWantsToBuy, Codecooler codecoolerCreatingGroup){
        this.artifactGroupWantsToBuy = artifactGroupWantsToBuy;
        this.codecoolerList = new ArrayList<Codecooler>();
        this.codecoolerList.add(codecoolerCreatingGroup);
    }

}
