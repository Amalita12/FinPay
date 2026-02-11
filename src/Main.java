import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Connexion réussie !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePayment(){
        System.out.println("=============================================");
        System.out.println("                     Update Payment");
        System.out.println("=============================================");


        PaymentDAO.updatePayment();
    }
    public void managePartialPayments(){
        System.out.println("=============================================");
        System.out.println("                     Remove Payment");
        System.out.println("=============================================");


        PaymentDAO.managePartialPayments();
    }





}