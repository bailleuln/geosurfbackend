package myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by onba7293 on 17/01/2017.
 */
@Service
final class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image create(Image image) {
        Image persisted = imageRepository.save(image);
        return persisted;
    }

    public Image delete(String id) {
        Image deleted = findImageById(id);
        imageRepository.delete(deleted);
        return deleted;
    }

    public List<Image> findAll() {
        List<Image> imageEntries = imageRepository.findAll();
        return imageEntries;
    }

    public Image findImageById(String id) {
        Image result = imageRepository.findById(id);
        return result;
    }

    public Image update(Image image) {
        Image updated = findImageById(image.getId());
        updated.update(image.getTimestamp(), image.getImgurUrl(), image.getLocation());
        updated = imageRepository.save(updated);
        return updated;
    }

    public void deleteAll() {
        imageRepository.deleteAll();
    }

    // manage occupation
    public Image findNearestImage(LatLng location, Distance distance)
    {
        Circle circle = new Circle(new Point(location.getLatitude(),location.getLongitude()), distance);

        List<Image> listResult = imageRepository.findByLocationWithin(circle);
        if (!listResult.isEmpty())
            return listResult.get(0); // return first spot found
        else
            return null;
    }

    public List<Image> findByPosition(LatLng location) {
        // return x images sorted by distance from a location
        Point center = new Point(location.getLatitude(),location.getLongitude());
        List<Image> resultFull = imageRepository.findByLocationWithin(new Circle(center, new Distance(500, Metrics.KILOMETERS)));
        List<Image> result = resultFull.subList(0, Math.min(100,resultFull.size()));
        return result;
    }
}

