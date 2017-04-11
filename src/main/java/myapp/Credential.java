package myapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.util.HashSet;

/**
 * Created by ONBA7293 on 02/01/2017.
 */
public class Credential {
    @Id
    private String id;
    private String name;
    private String pwd;

    @JsonIgnore
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPwd() { return pwd;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) { this.pwd = pwd; }

    public void update(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public Credential() {}

    public Credential(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return String.format(
                "Credential[id='%s', name='%s', pwd = '%s']",
                id, name, pwd);
    }

}
