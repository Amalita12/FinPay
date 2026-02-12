import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static PrestataireDAO prestataireDAO = new PrestataireDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Classe Payment:

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Connexion réussie !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePayment() {
        System.out.println("=============================================");
        System.out.println("                     Update Payment");
        System.out.println("=============================================");


        PaymentDAO.updatePayment();
    }

    public void managePartialPayments() {
        System.out.println("=============================================");
        System.out.println("                     Remove Payment");
        System.out.println("=============================================");


        PaymentDAO.managePartialPayments();
    }


    // classe Prestataire:
    public static void menuPrestataire() {
        int choix;
        do {
            afficherMenu();
            choix = scanner.nextInt();
            traiterChoix(choix);


        } while (choix != 0);


    }

    private static void afficherMenu() {
        System.out.println("\n--- GESTION DES PRESTATAIRES ---");
        System.out.println("1. Ajouter");
        System.out.println("2. Lister");
        System.out.println("3. Rechercher");
        System.out.println("4. Modifier");
        System.out.println("5. Supprimer");
        System.out.println("0. Quitter");
        System.out.print(" votre Choix : ");
    }

    private static void traiterChoix(int choix) {
        switch (choix){
            case 1 -> menuAjouter();
            case 2 -> menuLister();
            case 3 -> menuRechercher();
            case 4 -> menuModifier();
            case 5 -> menuSupprimer();
            case 0 -> System.out.println("Au revoir.");
            default -> System.out.println("Choix invalide! Veuillez réessayer.");
        }
    }

    private static void menuAjouter(){
        System.out.println("Nom du nouveau Prestataire: ");
        String nom = scanner.next();
        prestataireDAO.save(new Prestataire(0,nom));
        System.out.println("Prestataire enregistré.");
    }
    private static void menuLister(){

    }
}



