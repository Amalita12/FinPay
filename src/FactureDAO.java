import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureDAO {
    public static void addFacture(int idClient, int idPrestataire, double montant, Statut statut, Date dateFacture) {
        String sql = "INSERT INTO factures (id_client, id_prestataire, montant_total, statut, date_facture) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idClient);
            ps.setInt(2, idPrestataire);
            ps.setDouble(3, montant);
            ps.setString(4, statut.name());
            ps.setDate(5, dateFacture);

            ps.executeUpdate();
            System.out.println("Facture added successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static List<Facture> getAllFactures() {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures";
        try (Connection conn = databaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                factures.add(new Facture(
                        rs.getInt("id_facture"),
                        null, null,
                        rs.getDouble("montant_total"),
                        Statut.valueOf(rs.getString("statut")),
                        rs.getDate("date_facture"),
                        rs.getTimestamp("date_creation")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return factures;
    }

    public static void updateFactureStatut(int id, Statut newStatut) {
        String sql = "UPDATE factures SET statut=? WHERE id_facture=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatut.name());
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Facture statut updated successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void deleteFacture(int id) {
        String sql = "DELETE FROM factures WHERE id_facture=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Facture deleted successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}