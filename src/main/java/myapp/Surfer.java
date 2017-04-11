package myapp;

/**
 * Created by ONBA7293 on 28/11/2016.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.util.HashSet;
import java.util.List;


public class Surfer {

    @Id
    private String id;
    private String name;
    private String email;
    @GeoSpatialIndexed
    private LatLng location;
    private List<Friend> friends;
    private int connected;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() { return email;}

    public LatLng getLocation() {
        return location;
    }

    public List<Friend> getFriends() {return friends; }

    public int getConnected() {return connected;}

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) { this.email = email; }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setFriends(List<Friend> friends) {this.friends = friends; }

    public void setConnected(int connected) {
        this.connected = connected;
    }

    public void update(String name, String email, LatLng location, List<Friend> friends, int connected) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.friends = friends;
        this.connected = connected;
    }

    public Surfer() {}

    public Surfer(String name, String email, LatLng location, List<Friend> friends, int connected) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.friends = friends;
        this.connected = connected;
    }

    @Override
    public String toString() {
        return String.format(
                "Surfer[id='%s', name='%s', email = '%s', location='%s', friends='%s', connected='%d']",
                id, name, email, location, friends, connected);
    }
}