package com.ujjawalayush.example.kwizzbit;

public class RecyclerData3 {
    String title,trip,friend,title1;
    public String getGroup(){
        return title;
    }
    public String getTrip(){return trip;}
    public String getFriend(){return friend;}
    public String getPic(){return title1;}

    public void setGroup(String title){
        this.title=title;
    }
    public void setPic(String title1){
        this.title1=title1;
    }

    public void setTrip(String trip){
        this.trip=trip;
    }
    public void setFriend(String friend){
        this.friend=friend;
    }


}

