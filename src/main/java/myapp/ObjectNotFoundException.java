package myapp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by onba7293 on 29/11/2016.
 */
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String reason, String userId) {
        super("could not find entry for " + reason + " " + userId);
    }
}