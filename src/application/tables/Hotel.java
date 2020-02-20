package application.tables;

import javafx.scene.control.TextField;

public class Hotel {

    int id;
    String Name;
    String address;
    String zip;
    String city;
    String country;
    String phone;
    Boolean pool;
    Boolean childActivity;
    Boolean eveningEvents;
    Integer distancetoBeach;
    Integer distancetoCenter;

    public Hotel(int id, String name, String address, String zip, String city, String country, String phone, Boolean pool, Boolean childActivity, Boolean eveningEvents, Integer distancetoBeach, Integer distancetoCenter) {
        this.id = id;
        Name = name;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.pool = pool;
        this.childActivity = childActivity;
        this.eveningEvents = eveningEvents;
        this.distancetoBeach = distancetoBeach;
        this.distancetoCenter = distancetoCenter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getPool() {
        return pool;
    }

    public Boolean getChildActivity() {
        return childActivity;
    }

    public Boolean getEveningEvents() {
        return eveningEvents;
    }

    public Integer getDistancetoBeach() {
        return distancetoBeach;
    }

    public Integer getDistancetoCenter() {
        return distancetoCenter;
    }
}
