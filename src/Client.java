//public class Client {
//    private int id ;
//    private String nom;
//
//    public Client(int id, String nom) {
//        this.id = id;
//        this.nom = nom;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getNom() {
//        return nom;
//    }
//
//    public void setNom(String nom) {
//        this.nom = nom;
//    }
//}
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