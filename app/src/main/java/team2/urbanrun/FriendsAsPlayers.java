package team2.urbanrun;

/**
 * Created by אורן on 06/05/2015.
 */
public class FriendsAsPlayers {
    public String creator;
    public String players[];
    public double Centerlat, Centerlng;
    public int Radius,Time;

    public  FriendsAsPlayers (String creator_, String[] players_,double Centerlat_  ,double Centerlng_ ,int Radius_ ,int Time_){
        players = players_;
        creator = creator_;
        Centerlat = Centerlat_;
        Centerlng = Centerlng_;
        Radius = Radius_;
        Time = Time_;
    }
};

