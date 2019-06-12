package com.ujjawalayush.example.kwizzbit;

public class RecyclerData2 {
    String title,trip,friend,flip,name,username;
    public String getQuestion(){
        return title;
    }
    public String getYourAnswer(){return trip;}
    public String getCorrectAnswer(){return friend;}

    public void setQuestion(String title){
        this.title=title;
    }
    public void setYourAnswer(String trip){
        this.trip=trip;
    }


    public void setCorrectAnswer(String friend){
        this.friend=friend;
    }


}

