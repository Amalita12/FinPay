import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    public static void addClient(String nom) {
        String sql = "INSERT INTO clients (nom) VALUES (?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.executeUpdate();
            System.out.println("Client added successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT FROM clients";
        try (Connection conn = databaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new Client(rs.getInt("id_client"), rs.getString("nom")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return clients;
    }

    public static void updateClient(int id, String newName) {
        String sql = "UPDATE clients SET nom=? WHERE id_client=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Client updated successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void deleteClient(int id) {
        String sql = "DELETE FROM clients WHERE id_client=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Client deleted successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}