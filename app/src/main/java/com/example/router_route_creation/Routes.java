package com.example.router_route_creation;
// this class initialize the basic variables for a routes to be displayed and changed.
public class Routes {
    String user, type, start, end, time;

    public String getUser(){return user;}
    public String getType() {
        return type;
    }
    public String getStart() {
        return start;
    }
    public String getEnd() {
        return end;
    }
    public String getTime(){
        return time;
    }


    public void setUser(String user){this.user = user; }

    public void setType(String type){
        this.type = type;
    }
    public void setStart(String start){
        this.start = start;
    }
    public void setEnd(String end){
        this.end = end;
    }
    public void setTime(String time){
        this.time = time;
    }

}
