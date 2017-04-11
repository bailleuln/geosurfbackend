package myapp;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by ONBA7293 on 02/01/2017.
 */
interface CredentialRepository extends MongoRepository<Credential, Long> {

    Credential findById(String Id);

    Credential findByName(String name);

}
