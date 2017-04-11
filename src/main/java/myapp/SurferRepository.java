package myapp;

/**
 * Created by ONBA7293 on 28/11/2016.
 */

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

interface SurferRepository extends MongoRepository<Surfer, Long> {

    //void delete(Surfer deleted);

    //List<Surfer> findAll();

    //Optional<Surfer> findOne(String id);

    //Surfer save(Surfer saved);

    //void deleteAll();

    Surfer findById(String Id);

    List<Surfer> findByName(String name);

    List<Surfer> findByLocation(LatLng location);

    List<Surfer> findByLocationNear(Point center);

    List<Surfer> findByLocationWithin(Circle c);
}