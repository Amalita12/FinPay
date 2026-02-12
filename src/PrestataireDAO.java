import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class PrestataireDAO {

        // ajouter prestataire
        public void save(Prestataire prestataire) {
            String sql = "INSERT INTO prestataires (nom) VALUES (?)";
            try(Connection conn  = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setString(1,prestataire.getName());
                ps.executeUpdate();
                System.out.println("Prestataire ajouté avec succès!");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        // lister tous les prestataires
        public List<Prestataire> findAll() {

            List <Prestataire> prestataires = new ArrayList<>();
            String sql= "SELECT * FROM prestataires";
            try (Connection conn = DatabaseConnection.getConnection();
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

        public Prestataire findById(int id) {
            String sql = "SELECT * FROM prestataires WHERE id_prestataire=?";
            try (Connection conn = DatabaseConnection.getConnection();
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
        public void update(Prestataire prestataire) {
            String sql = "UPDATE prestataires SET nom = ? WHERE id_prestataire =?";
            try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, prestataire.getName());
                ps.setInt(2,prestataire.getId());
                ps.executeUpdate();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        // supprimer un prestataire
        public void delete(int id) {

            String sql = "DELETE FROM prestataires WHERE id_prestataire=?";
            try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1,id);
                ps.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

