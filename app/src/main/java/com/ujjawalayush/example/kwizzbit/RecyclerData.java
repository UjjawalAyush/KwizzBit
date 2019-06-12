package com.ujjawalayush.example.kwizzbit;

public class RecyclerData implements Comparable<RecyclerData>{
    float score;
    String username;

    public String getUsername() {
        return username;
    }

    public float getScore() {
        return score;
    }
    public void ScoreCard(float score,String username){
        this.score=score;
        this.username=username;
    }

    @Override
    public int compareTo(RecyclerData myList) {
        return (this.getScore() < myList.getScore() ? -1 :
                (this.getScore() == myList.getScore() ? 0 : 1));
    }
}
