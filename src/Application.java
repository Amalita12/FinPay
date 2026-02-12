import java.util.List;
import java.util.Scanner;

public class Application {
    List<Client> clients;
    List<Prestataire> prestataires;
    List<Facture> factures;

    public Application(List<Client> clients, List<Prestataire> prestataires, List<Facture> factures) {
        this.clients = clients;
        this.prestataires = prestataires;
        this.factures = factures;
    }

    public static void addFacture(Scanner add){
        System.out.println("Entre le Id du Prestataire : ");
        int id = add.nextInt();

    }
}
