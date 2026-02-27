import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.util.Scanner;

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

    public static void getAllFactures() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("=========All Factures========= \n");
        for (Facture f : factures) {
            System.out.println("Facture id: " + f.getIdFacture());
            System.out.println("Amount operation: " + f.getMontantTotal());
            System.out.println("Creation date: " + f.getDateCreation());
            System.out.println("Invoice date: " + f.getDateFacture());
            System.out.println("Status Payment: " + f.getStatut());
            System.out.println("-----------------------------------------");
        }
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchFacturesByStatut(String statut) {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures WHERE statut=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, statut);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                factures.add(new Facture(
                        rs.getInt("id_facture"), null, null,
                        rs.getDouble("montant_total"),
                        Statut.valueOf(rs.getString("statut")),
                        rs.getDate("date_facture"),
                        rs.getTimestamp("date_creation") ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("=========Factures========= \n");
        for (Facture f : factures) {
            System.out.println("facture id: " +f.getIdFacture());
            System.out.println("montant total: " +f.getMontantTotal());
            System.out.println("facture status: " +f.getStatut());
            System.out.println("Date creation: " +f.getDateCreation());
            System.out.println("-----------------------------------------");
        }
    }
    public static Facture findFactureById(int id) {
        String sql = "SELECT * FROM factures WHERE id_facture=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Facture(
                        rs.getInt("id_facture"),
                        null,
                        null,
                        rs.getDouble("montant_total"),
                        Statut.valueOf(rs.getString("statut")),
                        rs.getDate("date_facture"),
                        rs.getTimestamp("date_creation")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double getTotalFacturesPayee() {
        String sql = "SELECT SUM(montant_total) AS total FROM factures WHERE statut='PAYEE'";
        try (Connection conn = databaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static double getTotalFacturesNonPayee() {
        String sql = "SELECT SUM(montant_total) AS total FROM factures WHERE statut='NON_PAYEE'";
        try (Connection conn = databaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    public static void facturePDF(Scanner sc) {
        System.out.println("Entre Id du Facture : ");
        int id = sc.nextInt();
        Facture facture = FactureDAO.findFactureById(id);
        System.out.println(facture);
        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float margin = 50;
            float y = 700;
            float leading = 20f;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 22);
            contentStream.newLineAtOffset(margin, y);
            contentStream.showText("----FinPay APP----");

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.setLeading(leading);

            contentStream.newLine();
            contentStream.showText("Facture ID : " + facture.getIdFacture());
            contentStream.newLine();
            contentStream.showText("Date : " + facture.getDateFacture());
            contentStream.newLine();
            contentStream.showText("Client : " + facture.getClient().getNom() + " (ID: " + facture.getClient().getIdClient() + ")");
            contentStream.newLine();
            contentStream.showText("Prestataire : " + facture.getPrestataire().getName() + " (ID: " + facture.getPrestataire().getId() + ")");
            contentStream.newLine();
            contentStream.showText("Montant total : " + facture.getMontantTotal());
            contentStream.newLine();
            contentStream.showText("Commission : " + (facture.getMontantTotal() * 0.02));
            contentStream.newLine();
            contentStream.showText("Status : " + facture.getStatut());
            contentStream.newLine();
            contentStream.showText("Merci de votre confiance !");
            contentStream.endText();

            contentStream.close();
//            document.save(factureName(facture.getIdFacture()));
            document.save("Facture"+ facture.getIdFacture() + ".pdf");
            document.close();

            System.out.println("PDF créé avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Client getClientByFactureId(int factureId) {
        Client client = null;
        String sql = "SELECT c.id_client, c.nom " +
                "FROM factures f " +
                "JOIN clients c ON f.id_client = c.id_client " +
                "WHERE f.id_facture = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, factureId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                client = new Client(
                        rs.getInt("id_client"),
                        rs.getString("nom")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }



    public static List<Facture> getFactureNonPayee(String statut) {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures WHERE statut=?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, statut);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                factures.add(new Facture(
                        rs.getInt("id_facture"),
                        getClientByFactureId( rs.getInt("id_facture")), null,
                        rs.getDouble("montant_total"),
                        Statut.valueOf(rs.getString("statut")),
                        rs.getDate("date_facture"),
                        rs.getTimestamp("date_creation") ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return  factures;
    }
    public static void execute() {
        List<Facture> factures = getFactureNonPayee("NON_PAYEE");

        System.out.println("Nombre total de factures reçues : " + factures.size());

        String chemin = "C:\\Users\\enaa\\Desktop\\facturesimpayees_"
                + LocalDate.now() + ".xls";

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Factures Impayées");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Client", "Date", "Montant", "Jours de Retard"};

        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        int rowIndex = 1;

        for (Facture f : factures) {
            if (f.getStatut() == Statut.NON_PAYEE) {
                Row row = sheet.createRow(rowIndex++);

                long joursRetard = getJoursRetard(f);

                row.createCell(0).setCellValue(f.getIdFacture());
                row.createCell(1).setCellValue(f.getClient().getNom());
                row.createCell(2).setCellValue(f.getDateFacture().toString());
                row.createCell(3).setCellValue(f.getMontantTotal());
                row.createCell(4).setCellValue(joursRetard);
            }
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }


        try (FileOutputStream fos = new FileOutputStream(chemin)) {

            workbook.write(fos);
            System.out.println(" Export réussi : " + chemin);

        } catch (IOException e) {

            System.out.println(" Erreur lors de l'export : " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                System.out.println("Erreur fermeture workbook.");
            }
        }
    }

    private static long getJoursRetard(Facture facture) {
        if (facture.getDateFacture() == null) {
            return 0;
        }

        long diffMillis = System.currentTimeMillis() - facture.getDateFacture().getTime();
        long joursRetard = diffMillis / (1000 * 60 * 60 * 24);

        return Math.max(joursRetard, 0);
    }


}