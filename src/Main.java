import java.sql.Date;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        char again = 'y';
        while (again == 'y' || again == 'Y') {
            principalMenu();
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            switch (choice) {
                case 1 -> gestionClients();
                case 2 -> gestionFactures();
                case 3 -> gestionPaiements();
                case 4 -> gestionPrestataires();
                case 5 -> Historique();
                default -> System.out.println("Invalid choice!");
            }
            System.out.println("Retour au menu principal ? y/n");
            again = sc.next().charAt(0);
        }
    }
    private static void Historique() {
        System.out.println("       ===== Historique =====");
        System.out.println("\tTotal payments: " + PaymentDAO.getTotalMontantPaye());
        System.out.println("\tTotal commission: " + PaymentDAO.getTotalCommission());
        System.out.println("\tTotal facture payee: " + FactureDAO.getTotalFacturesPayee());
        System.out.println("\tTotal facture non-payee: " + FactureDAO.getTotalFacturesNonPayee());
    }
    public static void gestionClients() {
        System.out.println("===== Gestion des Clients =====");
        System.out.println("1. Ajouter Client");
        System.out.println("2. Afficher Clients");
        System.out.println("3. Mettre à jour Client");
        System.out.println("4. Supprimer Client");
        System.out.println("5. Rechercher Client par ID");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> addClient();
            case 2 -> displayClients();
            case 3 -> updateClient();
            case 4 -> deleteClient();
            case 5 -> searchClientById();
        }
    }
    public static void principalMenu() {
        System.out.println("===== Menu Principal FinPay =====");
        System.out.println("1. Gestion des Clients");
        System.out.println("2. Gestion des Factures");
        System.out.println("3. Gestion des Paiements");
        System.out.println("4. Gestion des Prestataires");
        System.out.println("5. Affichage de l'Historique");
    }
    public static void gestionFactures() {
        System.out.println("===== Gestion des Factures =====");
        System.out.println("1. Ajouter Facture");
        System.out.println("2. Afficher Factures");
        System.out.println("3. Mettre à jour Statut Facture");
        System.out.println("4. Supprimer Facture");
        System.out.println("5. Rechercher Factures par Statut");

        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> addFacture();
            case 2 -> displayFactures();
            case 3 -> updateFacture();
            case 4 -> deleteFacture();
            case 5 -> searchFacturesByStatut();
        }
    }
    private static void searchFacturesByStatut() {
        System.out.println("Enter statut (NON_PAYEE, PARTIELLE, PAYEE):");
        String statut = sc.nextLine();
        FactureDAO.searchFacturesByStatut(statut);
    }
    public static void gestionPaiements() {
        System.out.println("===== Gestion des Paiements =====");
        System.out.println("1. Ajouter Paiement");
        System.out.println("2. Afficher Paiements");
        System.out.println("3. Mettre à jour Paiement");


        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> addPayment();
            case 2 -> displayPayments();
            case 3 -> updatePayment();

        }
    }



    public static void gestionPrestataires() {
        System.out.println("===== Gestion des Prestataires =====");
        System.out.println("1. Ajouter Prestataire");
        System.out.println("2. Afficher Prestataires");
        System.out.println("3. Mettre à jour Prestataire");
        System.out.println("4. Supprimer Prestataire");
        System.out.println("5. Rechercher Prestataire");
        System.out.println("6. Exporter Factures (EXCEL)");

        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> addPrestataire();
            case 2 -> displayPrestataires();
            case 3 -> updatePrestataire();
            case 4 -> deletePrestataire();
            case 5 -> rechercherPrestataire();
            case 6 -> extraireExcelPrestataire();

        }
    }
    public static void addClient(){
           System.out.println("Enter client name:");
           String name = sc.nextLine();
           ClientDAO.addClient(name);
       }
    public static void displayClients(){
           ClientDAO.getAllClients();
       }
    public static void updateClient() {
           System.out.println("Enter client ID:");
           int id = sc.nextInt();
           sc.nextLine();
           System.out.println("Enter new name:");
           String name = sc.nextLine();
           ClientDAO.updateClient(id, name);
       }
    public static void deleteClient(){
           System.out.println("Enter client ID:");
           int id = sc.nextInt();
           ClientDAO.deleteClient(id);
       }
    public static void searchClientById(){
           System.out.println("Enter client ID:");
           int id = sc.nextInt();
           ClientDAO.findClientById(id);
       }
    public static void addFacture() {
        System.out.println("Enter client ID:");
        int idClient = sc.nextInt();
        System.out.println("Enter prestataire ID:");
        int idPrestataire = sc.nextInt();
        System.out.println("Enter montant:");
        double montant = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter facture date (yyyy-mm-dd):");
        String dateStr = sc.nextLine();
        Date dateFacture = Date.valueOf(dateStr);
        FactureDAO.addFacture(idClient, idPrestataire, montant, Statut.NON_PAYEE, dateFacture);
    }
    public static void displayFactures() {
        FactureDAO.getAllFactures();
    }
    public static void updateFacture() {
        System.out.println("Enter facture ID:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new statut (NON_PAYEE, PARTIELLE, PAYEE):");
        String statut = sc.nextLine();
        FactureDAO.updateFactureStatut(id, Statut.valueOf(statut));
    }
    public static void deleteFacture() {
        System.out.println("Enter facture ID:");
        int id = sc.nextInt();
        FactureDAO.deleteFacture(id);
    }
    public static void addPayment() {
        System.out.println("Enter facture ID:");
        int idFacture = sc.nextInt();
        System.out.println("Enter montant payé:");
        double montant = sc.nextDouble();
        double commission = montant * 0.02;
        PaymentDAO.addPayment(idFacture, montant, commission);
    }
    public static void displayPayments() {
        PaymentDAO.getAllPayments();
    }
    public static void updatePayment() {
        System.out.println("Enter payment ID:");
        int id = sc.nextInt();
        System.out.println("Enter new montant:");
        double montant = sc.nextDouble();
        System.out.println("Enter new commission:");
        double commission = sc.nextDouble();
        PaymentDAO.updatePayment(id, montant, commission);
    }
    public static void addPrestataire() {
        System.out.println("Enter prestataire name:");
        String name = sc.nextLine();
        PrestataireDAO.save(name);
    }
    public static void displayPrestataires() {
        PrestataireDAO.findAll();
    }
    public static void updatePrestataire() {
        System.out.println("Enter prestataire ID:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new name:");
        String name = sc.nextLine();
        PrestataireDAO.update(id, name);
    }
    public static void deletePrestataire() {
        System.out.println("Enter prestataire ID:");
        int id = sc.nextInt();
        PrestataireDAO.delete(id);
    }
    private static void rechercherPrestataire() {
        System.out.println("Enter prestataire ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        PrestataireDAO.findById(id);
    }
    public static void extraireExcelPrestataire() {
        System.out.println("entrez l'id du Prestataire pour l'export:");
        int id = sc.nextInt();
        PrestataireDAO.genererExcelPrestataire(id);
    }

}
