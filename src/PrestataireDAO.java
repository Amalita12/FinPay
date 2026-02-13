import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class PrestataireDAO {

        // ajouter prestataire
        public static void save(String name) {
            String sql = "INSERT INTO prestataires (nom) VALUES (?)";
            try(Connection conn  = databaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setString(1,name);
                ps.executeUpdate();
                System.out.println("Prestataire ajouté avec succès!");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        // lister tous les prestataires
        public static List<Prestataire> findAll() {

            List <Prestataire> prestataires = new ArrayList<>();
            String sql= "SELECT * FROM prestataires";
            try (Connection conn = databaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery())
            {
                while (rs.next()){
                    Prestataire p = new Prestataire(rs.getInt("id_prestataire"), rs.getString("nom"));
                    prestataires.add(p);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return  prestataires;
        }

        // rechercher prestataire par id

        public static Prestataire findById(int id) {
            String sql = "SELECT * FROM prestataires WHERE id_prestataire=?";
            try (Connection conn = databaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1,id);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){
                        return new Prestataire(
                                rs.getInt("id_prestataire"),
                                rs.getString("nom"));
                    }

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        // mettre à jour un prestataire
        public static void update(int id, String name) {
            String sql = "UPDATE prestataires SET nom = ? WHERE id_prestataire =?";
            try(Connection conn = databaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1,name);
                ps.setInt(2,id);
                ps.executeUpdate();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        // supprimer un prestataire
        public static void delete(int id) {

            String sql = "DELETE FROM prestataires WHERE id_prestataire=?";
            try (Connection conn = databaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1,id);
                ps.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

