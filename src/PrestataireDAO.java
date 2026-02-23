import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
                findByName(name);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        public static void findAll() {

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
            System.out.println("=========All Prestataires========= \n");
            for(Prestataire p: prestataires){
                System.out.println("Prestataire id: " + p.getId());
                System.out.println("Prestataire name: " + p.getName());
                System.out.println("-----------------------------------------");
        }
            }

        // rechercher prestataire par id

        public static void findById(int id) {
            String sql = "SELECT * FROM prestataires WHERE id_prestataire=?";
            try (Connection conn = databaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("=============Prestataire=============");
                    System.out.println( "prestataire id: " + rs.getInt("id_prestataire"));
                    System.out.println( "prestataire name: "+ rs.getString("nom"));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static void findByName(String name) {
            String sql = "SELECT * FROM prestataires WHERE nom=?";
            try (Connection conn = databaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("=============Prestataire added =============");
                    System.out.println( "prestataire id: " + rs.getInt("id_prestataire"));
                    System.out.println( "prestataire name: "+ rs.getString("nom"));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
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
                System.out.println("prestataire supprimé.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public static void genererExcelPrestataire(int id) {
            String fileName = "facturesprestatairemois.xlsx";

            String sql = "SELECT f.id_facture, f.date_facture, c.nom AS client_nom, f.montant_total, f.statut " +
                    "FROM factures f " +
                    "JOIN clients c ON f.id_client = c.id_client " +
                    "WHERE f.id_prestataire = ?";

            try (Connection conn = databaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 Workbook workbook = new XSSFWorkbook()) {

                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                Sheet sheet = workbook.createSheet("Mes Factures");


                String[] headers = {"ID", "Date", "Client", "Montant", "Statut"};
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }


                int rowIdx = 1;
                double totalFacture = 0;
                double totalPaye = 0;
                double totalEnAttente = 0;

                while (rs.next()) {
                    Row row = sheet.createRow(rowIdx++);

                    row.createCell(0).setCellValue(rs.getInt("id_facture"));
                    row.createCell(1).setCellValue(rs.getDate("date_facture").toString());
                    row.createCell(2).setCellValue(rs.getString("client_nom"));

                    double montant = rs.getDouble("montant_total");
                    String statut = rs.getString("statut");

                    row.createCell(3).setCellValue(montant);
                    row.createCell(4).setCellValue(statut);


                    totalFacture += montant;
                    if ("PAYEE".equalsIgnoreCase(statut)) {
                        totalPaye += montant;
                    } else {
                        totalEnAttente += montant;
                    }
                }


                rowIdx++;
                Row r1 = sheet.createRow(rowIdx++);
                r1.createCell(2).setCellValue("TOTAL FACTURÉ :");
                r1.createCell(3).setCellValue(totalFacture);

                Row r2 = sheet.createRow(rowIdx++);
                r2.createCell(2).setCellValue("TOTAL PAYÉ :");
                r2.createCell(3).setCellValue(totalPaye);

                Row r3 = sheet.createRow(rowIdx++);
                r3.createCell(2).setCellValue("TOTAL EN ATTENTE :");
                r3.createCell(3).setCellValue(totalEnAttente);


                try (java.io.FileOutputStream fileOut = new java.io.FileOutputStream(fileName)) {
                    workbook.write(fileOut);
                }

                System.out.println(" Le fichier " + fileName + " a été créé à la racine du projet.");

            } catch (SQLException | java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

