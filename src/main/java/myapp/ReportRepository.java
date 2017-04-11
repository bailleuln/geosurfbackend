package myapp;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by ONBA7293 on 11/01/2017.
 */
interface ReportRepository extends MongoRepository<Report, Long> {

    Report findById(String Id);

    List<Report> findByDate(Date date);
}
