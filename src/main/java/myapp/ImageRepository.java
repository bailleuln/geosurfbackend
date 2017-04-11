package myapp;

import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by onba7293 on 17/01/2017.
 */
interface ImageRepository extends MongoRepository<Image, Long> {
    List<Image> findByLocationWithin(Circle c);
    Image findById(String Id);
}
