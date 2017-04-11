package myapp;

/**
 * Created by onba7293 on 10/01/2017.
 */
public class Friend {
    private String pseudo;

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Friend() {}

    public Friend(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public String toString() {
        return String.format(
                "Friend[pseudo='%s']",
                pseudo);
    }
}
