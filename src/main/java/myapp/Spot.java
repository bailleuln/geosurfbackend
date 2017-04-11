package myapp;

/**
 * Created by ONBA7293 on 28/11/2016.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import javax.annotation.Generated;

public class Spot {


    @Id
    private String id;
    private String name;
    @GeoSpatialIndexed
    private LatLng location;
    private String mswId;
    private String ydsId;
    private String wguId;
    private String shomId;
    @JsonIgnore
    private int occupation;
    private String occupationFlag;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getMswId() {
        return mswId;
    }

    public String getYdsId() { return ydsId; }

    public String getWguId() { return wguId; }

    public String getShomId() { return shomId; }

    public int getOccupation() { return occupation; }

    public String getOccupationFlag() {
        return occupationFlag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setMswId(String mswId) {
        this.mswId = mswId;
    }

    public void setYdsId(String ydsId) { this.ydsId = ydsId; }

    public void setWguId(String wguId) { this.wguId = wguId; }

    public void setShomId(String shomId) {this.shomId = shomId; }

    public void setOccupation(int occupation) { this.occupation = occupation;}

    public void setOccupationFlag(String occupationFlag) {
        this.occupationFlag = occupationFlag;
    }

    public void setOccupationFlag(int occupation) {
        if (occupation<3)
            setOccupationFlag("green");
        else if (occupation<10)
            setOccupationFlag("orange");
        else
            setOccupationFlag("red");
    }

    public void update(String name, LatLng location, String mswId, String ydsId, String wguId, String shomId, int occupation, String occupationFlag) {
        this.name = name;
        this.location = location;
        this.mswId = mswId;
        this.ydsId = ydsId;
        this.wguId = wguId;
        this.shomId = shomId;
        this.occupation = occupation;
        this.occupationFlag = occupationFlag;
    }

    public Spot() {
    }

    public Spot(String name, LatLng location, String mswId, String ydsId, String wguId, String shomId, int occupation, String occupationFlag) {
        this.name = name;
        this.location = location;
        this.mswId = mswId;
        this.ydsId = ydsId;
        this.wguId = wguId;
        this.shomId = shomId;
        this.occupation = occupation;
        this.occupationFlag = occupationFlag;
    }

    @Override
    public String toString() {
        return String.format(
                "Spot[id='%s', name='%s', location='%s', mswid='%s', ydsId='%s', wguID='%s', shomId='%s', occupation='%d', occupationFlag='%s']",
                id, name, location, mswId, ydsId, wguId, shomId, occupation, occupationFlag);
    }

    // manage population
    public void deltaSurfer(int people) {
        // business logic
        occupation += people;
        if (occupation<0)
            occupation=0;
        setOccupationFlag(occupation);
    }
}