package myapp;

/**
 * Created by ONBA7293 on 28/11/2016.
 */

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

interface SpotRepository extends MongoRepository<Spot, Long> {

    //void delete(Spot deleted);

    //List<Spot> findAll();

    //Optional<Spot> findOne(String id);

    //Spot save(Spot saved);

    //void deleteAll();

    Spot findById(String Id);

    Spot findByName(String name);

    Spot findByLocation(LatLng location);

    Spot findByMswId(String mswid);

    List<Spot> findByLocationWithin(Circle c);
}