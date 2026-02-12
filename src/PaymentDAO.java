import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // ✅ CREATE

    public static void AjouterPayment(int idFacture, double montantPaye, double commission) {
        String sql = "INSERT INTO paiements (id_facture, montant_paye, commission_finpay) VALUES (?, ?, ?)";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idFacture);
            pstmt.setDouble(2, montantPaye);
            pstmt.setDouble(3, commission);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Payment added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error adding payment: " + e.getMessage());
        }
    }

    // ✅ READ
    public static void displayPayment() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM paiements";

        try (Connection connection = databaseConnection.getConnection();
             Statement stm = connection.createStatement();
             ResultSet resultSet = stm.executeQuery(sql)) {

            while (resultSet.next()) {
                int id_paiement = resultSet.getInt("id_paiement");
                int id_facture = resultSet.getInt("id_facture");
                double montant_paye = resultSet.getDouble("montant_paye");
                double commission_finpay = resultSet.getDouble("commission_finpay");
                Timestamp date_paiement = resultSet.getTimestamp("date_paiement");

                // Here you could fetch Facture details if needed
                Payment payment = new Payment(id_paiement, null, date_paiement, montant_paye, commission_finpay);
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Error displaying payments: " + e.getMessage());
        }

        for (Payment p : payments) {
            System.out.println(p);
        }
    }

    // ✅ UPDATE
    public static void updatePayment(int idPaiement, double newMontant, double newCommission) {
        String sql = "UPDATE paiements SET montant_paye = ?, commission_finpay = ? WHERE id_paiement = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setDouble(1, newMontant);
            pstmt.setDouble(2, newCommission);
            pstmt.setInt(3, idPaiement);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Payment updated successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error updating payment: " + e.getMessage());
        }
    }

    // ✅ DELETE / Manage Partial Payments
    public static void managePartialPayments(int idPaiement) {
        String sql = "DELETE FROM paiements WHERE id_paiement = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idPaiement);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Payment deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting payment: " + e.getMessage());
        }
    }
}
