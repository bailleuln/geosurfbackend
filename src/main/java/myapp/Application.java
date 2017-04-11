/**
 * Created by ONBA7293 on 23/11/2016.
 */
package myapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(SurferRepository surferRepository,
                           SpotRepository spotRepository,
                           CredentialRepository credentialRepository,
                           ReportRepository reportRepository,
                           ImageRepository imageRepository
                           ) {
        return evt -> {

            // clean
            surferRepository.deleteAll();
            spotRepository.deleteAll();
            credentialRepository.deleteAll();
            reportRepository.deleteAll();
            imageRepository.deleteAll();

            // init with Surfers
            ArrayList<Friend> friends = new ArrayList<Friend>();
            friends.add(new Friend("nico"));
            surferRepository.save(new Surfer( "johnjohn", "johnjohn@orange.fr", new LatLng(44.518076, -1.254502), friends, 0));
            friends.clear();
            friends.add(new Friend("parko"));
            friends.add(new Friend("johnjohn"));
            surferRepository.save(new Surfer( "kelly", "kelly@orange.fr", new LatLng(43.521218, -1.526905), friends, 0));
            friends.clear();
            friends.add(new Friend("johnjohn"));
            friends.add(new Friend("medina"));
            friends.add(new Friend("nico"));
            surferRepository.save(new Surfer( "parko", "parko@orange.fr", new LatLng(43.521218, -1.526905), friends, 0));
            friends.clear();
            friends.add(new Friend("johnjohn"));
            surferRepository.save(new Surfer( "medina", "medina@orange.fr", new LatLng(43.3636659,-1.797279), friends, 0));
            friends.clear();
            friends.add(new Friend("nemo"));
            friends.add(new Friend("johnjohn"));
            surferRepository.save(new Surfer( "josh", "josh@orange.fr", new LatLng(43.3636659,-1.797279), friends, 0));
            friends.clear();
            friends.add(new Friend("johnjohn"));
            surferRepository.save(new Surfer( "nemo", "nemo@orange.fr", new LatLng(44.518076, -1.254502), friends, 1));

            // init with spots
            spotRepository.save(new Spot( "cavaliers", new LatLng(43.521218, -1.526905),
                    "http://fr.magicseaweed.com/Les-Cavaliers-Surf-Guide/892/",
                    "http://www.yadusurf.com/METEO-SURF-REPORT/Les-Cavaliers/1297",
                    "https://www.windguru.cz/48572",
                    "http://maree.info/140",
                    0,
                    "green"));
            spotRepository.save(new Spot( "miramar", new LatLng(43.488552, -1.555237),
                    "http://fr.magicseaweed.com/Biarritz-Grande-Plage-Surf-Guide/62/",
                    "http://www.yadusurf.com/METEO-SURF-REPORT/La-Grande-Plage/1305",
                    "https://www.windguru.cz/15",
                    "http://maree.info/140",
                    0,
                    "green"));
            spotRepository.save(new Spot( "hendaye", new LatLng(43.3636659,-1.797279),
                    "http://fr.magicseaweed.com/Hendaye-Plage-Surf-Guide/1581/",
                    "http://www.yadusurf.com/METEO-SURF-REPORT/Hendaye/1320",
                    "https://www.windguru.cz/48576",
                    "http://maree.info/141",
                    0,
                    "green"));
            spotRepository.save(new Spot( "penon", new LatLng(43.709718, -1.435677),
                    "http://fr.magicseaweed.com/Les-Estagnots-Surf-Guide/890/",
                    "http://www.yadusurf.com/METEO-SURF-REPORT/Le-Penon/1280",
                    "https://www.windguru.cz/1152",
                    "http://maree.info/140",
                    0,
                    "green"));
            spotRepository.save(new Spot( "viviers", new LatLng(44.458922, -1.255098),
                    "http://fr.magicseaweed.com/Biscarrosse-Plage-Surf-Guide/65/",
                    "http://www.yadusurf.com/METEO-SURF-REPORT/Biscarrosse/1270",
                    "https://www.windguru.cz/231",
                    "http://maree.info/137",
                    0,
                    "green"));
            spotRepository.save(new Spot( "la salie", new LatLng(44.518076, -1.254502),
                    "http://fr.magicseaweed.com/La-Salie-Surf-Guide/4403/",
                    "http://www.yadusurf.com/METEO-SURF-REPORT/La-Salie/1268",
                    "https://www.windguru.cz/231",
                    "http://maree.info/136",
                    0,
                    "green"));

            // init with Credentials
            credentialRepository.save(new Credential("johnjohn", "florence"));
            credentialRepository.save(new Credential("medina", "gabriel"));

            // init with Visitors
            List<Spot> spotEntries = spotRepository.findAll();
            for (int i=0; i<3000; i++)
            {
                int randomIndex = ThreadLocalRandom.current().nextInt(0, spotEntries.size());
                Surfer aSurfer = new Surfer("visitor"+ Integer.toString(i),null,null,null, (randomIndex % 2));
                Spot randomSpot = spotEntries.get(randomIndex);
                LatLng randomPosition = randomSpot.getLocation();
                // add a random shift to that position
                Double randomLatitude = randomPosition.getLatitude()+ThreadLocalRandom.current().nextDouble(-0.05, 0.05);
                Double randomLongitude = randomPosition.getLongitude()+ThreadLocalRandom.current().nextDouble(-0.05, 0.05);
                aSurfer.setLocation(new LatLng(randomLatitude, randomLongitude));
                surferRepository.save(aSurfer);
                // set spot occupation
                Point center = new Point(randomLatitude, randomLongitude);
                Distance distance = new Distance(25, Metrics.KILOMETERS);
                Circle circle = new Circle(center, distance);
                List<Spot> listResult = spotRepository.findByLocationWithin(circle);
                if (!listResult.isEmpty()) {
                    listResult.get(0).deltaSurfer(1);
                    spotRepository.save(listResult.get(0));
                }
            }

            // init with Images
            imageRepository.save(new Image(0, "http://i.imgur.com/5MM61yk.jpg", new LatLng(44.518077, -1.254501)));

        };
    }
}

