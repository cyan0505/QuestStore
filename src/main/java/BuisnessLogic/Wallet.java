package BuisnessLogic;

import java.util.ArrayList;
import java.util.List;

public class Wallet {

    private List<Artifact> artifactList;
    private Integer coins;

    public Wallet(){
        this.artifactList = new ArrayList<Artifact>();
        this.coins = 0;
    }

    public Integer getCoins() {
        return coins;
    }

    public List<Artifact> getArtifactList() {
        return artifactList;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public void addArtifact(Artifact artifact){
        this.artifactList.add(artifact);
    }

    public void addCoins(Integer reward){
        this.coins += reward;
    }
}
