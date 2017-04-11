package myapp;

/**
 * Created by onba7293 on 29/11/2016.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
final class SurferService {

    private final SurferRepository surferRepository;

    @Autowired
    SurferService(SurferRepository surferRepository) {
        this.surferRepository = surferRepository;
    }

    public Surfer create(Surfer surfer) {
        Surfer persisted = surferRepository.save(new Surfer(surfer.getName(), surfer.getEmail(), surfer.getLocation(), surfer.getFriends(), surfer.getConnected()));// to avoid externally set ids
        return persisted;
    }

    public Surfer delete(String id) {
        Surfer deleted = findSurferById(id);
        surferRepository.delete(deleted);
        return deleted;
    }

     public List<Surfer> findAll() {
        List<Surfer> surferEntries = surferRepository.findAll();
        return surferEntries;
    }

    public Surfer findById(String id) {
        Surfer found = findSurferById(id);
        return found;
    }

    public Surfer update(Surfer surfer) {
        Surfer updated = findSurferById(surfer.getId());
        updated.update(surfer.getName(), surfer.getEmail(), surfer.getLocation(), surfer.getFriends(), surfer.getConnected());
        updated = surferRepository.save(updated);
        return updated;
    }

    private Surfer findSurferById(String id) {
        Surfer result = surferRepository.findById(id);
        return result;

    }

    public void deleteAll() {
        surferRepository.deleteAll();
    }

    public List<Surfer> findByName(String name) {
        List<Surfer> result = surferRepository.findByName(name);
        return result;
    }

    public List<Surfer> findByPosition(LatLng location) {
        // return x surfers sorted by distance from a location
        Point center = new Point(location.getLatitude(),location.getLongitude());
        List<Surfer> resultFull = surferRepository.findByLocationWithin(new Circle(center, new Distance(500, Metrics.KILOMETERS)));
        List<Surfer> result = resultFull.subList(0, Math.min(100,resultFull.size()));
        return result;
    }

    List<Surfer> findSurferByFilter(String name,
                                    Double latitude,
                                    Double longitude) {
        System.out.print("filter: " + name + " " + latitude + " " + longitude + "\n");
        if (name != null) {
            System.out.print("findSurferByName " + name + "\n");
            return surferRepository.findByName(name);
        } else if ((latitude != null) & (longitude != null)) {
            System.out.print("findSurferByLocation " + latitude + " " + longitude + "\n");
            //return surferRepository.findByLocation(new LatLng(latitude, longitude));
            return findByPosition(new LatLng(latitude, longitude));
        } else {
            System.out.print("findAllSurfers\n");
            return surferRepository.findAll();
        }
    }

    Surfer newOrUpdate(String pseudo, LatLng location) {
        if (!pseudo.equals("loginKO"))
        {
            Surfer currentSurfer;
            Surfer resultSurfer;
            if (!findByName(pseudo).isEmpty()) // existing surfer, update
            {
                currentSurfer = findByName(pseudo).get(0);
                currentSurfer.setLocation(location);
                currentSurfer.setConnected(1);
                resultSurfer = update(currentSurfer);
            }
            else // register new surfer
            {
                currentSurfer = new Surfer(pseudo, null, location, null, 1);
                resultSurfer = create(currentSurfer);
            }
            return resultSurfer;
        }
        else // login error
        {
            return new Surfer("visitor", null, location, null, 0);
        }

    }
}
