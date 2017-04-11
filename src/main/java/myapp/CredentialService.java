package myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ONBA7293 on 02/01/2017.
 */
@Service
final class CredentialService {

    private final CredentialRepository credentialRepository;

    @Autowired
    CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public Credential create(Credential credential) {
        Credential persisted = credentialRepository.save(credential);
        return persisted;
    }

    public Credential loginOrRegister(Credential credential) {
        Credential current = credentialRepository.findByName(credential.getName());
        if (current != null) {
            System.out.print("Found current Credential " + credential.getName() + ", try to login\n");
            if (!credential.getPwd().equals(current.getPwd())) {
                System.out.print("password mismatch, error\n");
                Credential loginKO = new Credential("loginKO", "password mismatch");
                return loginKO;
            }
            else {
                System.out.print("login completed\n");
                return current;
            }
        }
        else {
            System.out.print("Register new Credential " + credential.getName() + " " + credential.getPwd() + "\n");
            Credential persisted = credentialRepository.save(credential);
            return persisted;
        }
    }

    public Credential delete(String name) {
        Credential deleted = findByName(name);
        credentialRepository.delete(deleted);
        return deleted;
    }

    public List<Credential> findAll() {
        List<Credential> credentialEntries = credentialRepository.findAll();
        return credentialEntries;
    }

    public Credential findById(String id) {
        Credential found = findCredentialById(id);
        return found;
    }

    public Credential update(Credential credential) {
        Credential updated = findByName(credential.getName());
        updated.update(credential.getName(), credential.getPwd());
        updated = credentialRepository.save(updated);
        return updated;
    }

    private Credential findCredentialById(String id) {
        Credential result = credentialRepository.findById(id);
        return result;

    }

    public void deleteAll() {
        credentialRepository.deleteAll();
    }

    public Credential findByName(String name) {
        Credential result = credentialRepository.findByName(name);
        return result;
    }

}

