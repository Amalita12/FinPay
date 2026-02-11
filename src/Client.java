import java.util.ArrayList;
import java.util.List;

public class Client {
    public int id;
    public String Name;

    public Client(int id, String name) {
        this.id = id;
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                '}';
    }
}
