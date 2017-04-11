package myapp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.io.Serializable;

/**
 * Created by onba7293 on 17/01/2017.
 */
public class Image implements Serializable {
    @Id
    private String id;
    private int timestamp;
    private String imgurUrl;
    @GeoSpatialIndexed
    private LatLng location;

    public String getId() {
        return id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getImgurUrl() {
        return imgurUrl;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setImgurUrl(String imgurUrl) {
        this.imgurUrl = imgurUrl;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Image() {}
    public Image(int timestamp, String imgurUrl, LatLng location)
    {
        this.timestamp = timestamp;
        this.imgurUrl = imgurUrl;
        this.location = location;
    }
    public void update(int timestamp, String imgurUrl, LatLng location)
    {
        this.timestamp = timestamp;
        this.imgurUrl = imgurUrl;
        this.location = location;
    }
    @Override
    public String toString() {
        return String.format("Image[timestamp='%d', imgurUrl='%s', location='%s']\"," +
                timestamp,
                imgurUrl,
                location);
    }
}
