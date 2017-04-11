package myapp;

/**
 * Created by onba7293 on 29/11/2016.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
final class SpotService {

    private final SpotRepository spotRepository;

    @Autowired
    SpotService(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    public Spot create(Spot spot) {
        Spot persisted = spotRepository.save(spot);
        return persisted;
    }

   public Spot delete(String id) {
        Spot deleted = findSpotById(id);
        spotRepository.delete(deleted);
        return deleted;
    }

    public List<Spot> findAll() {
        List<Spot> spotEntries = spotRepository.findAll();
        return spotEntries;
    }

    public Spot findById(String id) {
        Spot result = spotRepository.findById(id);
        return result;
    }

   public Spot update(Spot spot) {
        Spot updated = findSpotById(spot.getId());
        updated.update(spot.getName(), spot.getLocation(), spot.getMswId(), spot.getYdsId(), spot.getWguId(), spot.getShomId(), spot.getOccupation(), spot.getOccupationFlag());
        updated = spotRepository.save(updated);
        return updated;
    }

    private Spot findSpotById(String id) {
        Spot result = spotRepository.findById(id);
        return result;
    }

    public void deleteAll() {
        spotRepository.deleteAll();
    }

    public Spot findByName(String name) {
        Spot result = spotRepository.findByName(name);
        return result;
    }

    public Spot findByLocation(LatLng location) {
        Spot result = spotRepository.findByLocation(location);
        return result;
    }

    public Spot findByMswId(String mswId) {
        Spot result = spotRepository.findByMswId(mswId);
        return result;
    }

    // manage occupation
    public Spot findNearestSpot(LatLng location, Distance distance)
    {
        Circle circle = new Circle(new Point(location.getLatitude(),location.getLongitude()), distance);

        List<Spot> listResult = spotRepository.findByLocationWithin(circle);
        if (!listResult.isEmpty())
            return listResult.get(0); // return first spot found
        else
            return null;
    }
}
