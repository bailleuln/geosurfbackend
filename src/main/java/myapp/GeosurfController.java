/**
 * Created by ONBA7293 on 23/11/2016.
 */

package myapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/geosurf")

final class GeosurfController {

    private final SurferService surferService;
    private final SpotService spotService;
    private final CredentialService credentialService;
    private final ReportService reportService;
    private final ImageService imageService;

    @Autowired
    GeosurfController(SurferService surferService, SpotService spotService, CredentialService credentialService, ReportService reportService, ImageService imageService) {
        this.surferService = surferService;
        this.spotService = spotService;
        this.credentialService = credentialService;
        this.reportService = reportService;
        this.imageService = imageService;
    }

    // resource Surfer
    @RequestMapping(value="/surfers",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Surfer createSurfer(@RequestHeader(value="{Content-type: application/json}") @RequestBody @Valid Surfer surferEntry) {
        return surferService.create(surferEntry);
    }

    @RequestMapping(value = "/surfers/{id}", method = RequestMethod.DELETE)
    Surfer deleteSurfer(@PathVariable("id") String id) {
        return surferService.delete(id);
    }

    @RequestMapping(path = "/surfers/{id}", method = RequestMethod.GET)
    Surfer findSurferById(@PathVariable("id") String id) {
        return surferService.findById(id);
    }

    @RequestMapping(value = "/surfers/{id}/positions", method = RequestMethod.PUT)
    Surfer updateSurferPosition(@RequestHeader(value="{Content-type: application/json}") @RequestBody @Valid Surfer surferEntry) {
        Spot spotToBeUpdated;
        // in that case, dont take into account the input values of Surfer, apart from Location
        Surfer toBeUpdated = surferService.findById(surferEntry.getId());
        toBeUpdated.setLocation(surferEntry.getLocation());
        Surfer updated = surferService.update(toBeUpdated);
        LatLng lastLocation = updated.getLocation();
        LatLng newLocation = surferEntry.getLocation();

        Distance distance = new Distance(25, Metrics.KILOMETERS); // search margin

          // manage update on last known spot
        if (lastLocation!=null) {
            spotToBeUpdated = spotService.findNearestSpot(lastLocation, distance);
            if (spotToBeUpdated!=null) // surfer was attached to a spot
            {
                spotToBeUpdated.deltaSurfer(-1);
                spotService.update(spotToBeUpdated);
            }
        }// end manage update on last known spot

         // manage update new spot
        if (newLocation!=null) {
            spotToBeUpdated = spotService.findNearestSpot(newLocation, distance);
            if (spotToBeUpdated != null) // surfer is attached to a new spot
            {
                spotToBeUpdated.deltaSurfer(1);
                spotService.update(spotToBeUpdated);
            }
        }
        // end manage update on spot repository

        return updated;
    }

    @RequestMapping(value = "/surfers/{id}", method = RequestMethod.PUT)
    Surfer updateSurfer(@RequestHeader(value="{Content-type: application/json}") @RequestBody @Valid Surfer surferEntry) {
        Surfer updated = surferService.update(surferEntry);
        return updated;
    }

    @RequestMapping(path="/surfers",method = RequestMethod.GET)
    List<Surfer> findSurferByFilter(@RequestParam(value="name", required=false) String name,
                                    @RequestParam(value="latitude", required=false) Double latitude,
                                    @RequestParam(value="longitude", required=false) Double longitude) {
        return surferService.findSurferByFilter(name, latitude, longitude);
    }

    // resource spot
    @RequestMapping(value="/spots",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Spot createSpot(@RequestHeader(value="{Content-type: application/json}") @RequestBody @Valid Spot spotEntry) {
        return spotService.create(spotEntry);
    }

    @RequestMapping(value = "/spots/{id}", method = RequestMethod.DELETE)
    Spot deleteSpot(@PathVariable("id") String id) {
        return spotService.delete(id);
    }

    @RequestMapping(path="/spots",method = RequestMethod.GET)
    List<Spot> findAllSpots() {
        return spotService.findAll();
    }

    @RequestMapping(path = "/spots/{id}", method = RequestMethod.GET)
    Spot findSpotById(@PathVariable("id") String id) {
        return spotService.findById(id);
    }

    @RequestMapping(value = "/spots/{id}", method = RequestMethod.PUT)
    Spot updateSpot(@RequestHeader(value="{Content-type: application/json}") @RequestBody @Valid Spot spotEntry) {
        return spotService.update(spotEntry);
    }

    // login operation
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Surfer loginOrRegister(@RequestHeader(value="{Content-type: application/json}") @RequestBody @Valid Credential credentialEntry,
                           @RequestParam(value="latitude", required=true) Double latitude,
                           @RequestParam(value="longitude", required=true) Double longitude) {
        // check credentials
        Credential credentialResult = credentialService.loginOrRegister(credentialEntry);
        // return surfer w creation/update if needed
        Surfer surferResult = surferService.newOrUpdate(credentialResult.getName(), new LatLng(latitude, longitude));
        return surferResult;
    }

    // resource credential
    @RequestMapping(value = "/credentials/{name}", method = RequestMethod.DELETE)
    Credential deleteCredential(@PathVariable("name") String name) {
        return credentialService.delete(name);
    }

    @RequestMapping(path = "/credentials/{id}", method = RequestMethod.GET)
    Credential findCredentialById(@PathVariable("id") String id) {
        return credentialService.findById(id);
    }

    @RequestMapping(path = "/credentials", method = RequestMethod.GET)
    List<Credential> findAllCredentials() {
        return credentialService.findAll();
    }

    @RequestMapping(value = "/credentials/{name}", method = RequestMethod.PUT)
    Credential updateCredential(@RequestHeader(value="{Content-type: application/json}") @RequestBody @Valid Credential credentialEntry, @PathVariable("name") String name) {
        if (name.equals(credentialEntry.getName()))
            return credentialService.update(credentialEntry);
        else
            return new Credential("updateCredentialKO", "name mismatch");
    }

    // resource report
    @RequestMapping(value="/reports",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Report createReport(@RequestHeader(value="{Content-type: application/json}") @RequestBody @Valid Report reportEntry) {
        return reportService.create(reportEntry);
    }

    @RequestMapping(value = "/reports/{id}", method = RequestMethod.DELETE)
    Report deleteReport(@PathVariable("id") String id) {
        return reportService.delete(id);
    }

    @RequestMapping(path="/reports",method = RequestMethod.GET)
    List<Report> findAllReports() {
        return reportService.findAll();
    }

    @RequestMapping(path = "/reports/{id}", method = RequestMethod.GET)
    Report findReportById(@PathVariable("id") String id) {
        return reportService.findById(id);
    }

    // resource image
    @RequestMapping(value="/images",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Image createImage(@RequestHeader(value="{Content-type: application/json}") @RequestBody @Valid Image imageEntry) {
        return imageService.create(imageEntry);
    }
    @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
    Image deleteImage(@PathVariable("id") String id) {
        return imageService.delete(id);
    }

    @RequestMapping(path = "/images/{id}", method = RequestMethod.GET)
    Image findImageById(@PathVariable("id") String id) {
        return imageService.findImageById(id);
    }

    @RequestMapping(path="/images",method = RequestMethod.GET)
    List<Image> findImageByLocation(@RequestParam(value="latitude", required=true) Double latitude,
                                    @RequestParam(value="longitude", required=true) Double longitude) {
        return imageService.findByPosition(new LatLng(latitude, longitude));
    }



    // exceptions
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleObjectNotFound(ObjectNotFoundException ex) {
    }
 }