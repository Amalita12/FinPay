import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.Timestamp;


public class PaymentDAO {

    public static void addPayment(int idFacture, double montantPaye, double commission) {
        String sql = "INSERT INTO paiements (id_facture, montant_paye, commission_finpay) VALUES (?, ?, ?)";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, idFacture);
            pstmt.setDouble(2, montantPaye);
            pstmt.setDouble(3, commission);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet resultSet = pstmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int idPayment = resultSet.getInt(1);
                        Facture facture = FactureDAO.findFactureById(idFacture);
                        double resteAPayer = facture.getMontantTotal() - montantPaye;
                        Date datePaiement = facture.getDateFacture();
                        System.out.println("Payment added successfully!");
                        GenerationDunRecuDePaiement(idPayment, idFacture, datePaiement, montantPaye, resteAPayer);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding payment: " + e.getMessage());
        }
    }

    private static void GenerationDunRecuDePaiement(int idPayment, int idFacture, Date date, double montanPaye, double resteAPayer) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream("recupaiementID"+ idPayment +".pdf"));
            document.open();
         Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
         Paragraph title = new Paragraph("Reçu de Paiement", titleFont);
         title.setAlignment(Element.ALIGN_CENTER); document.add(title); document.add(new Paragraph(" "));
         Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
         document.add(new Paragraph("\t\tNuméro du paiement : " + idPayment, normalFont));
         document.add(new Paragraph("\t\tNuméro de la facture : " + idFacture, normalFont));
         document.add(new Paragraph("\t\tDate du paiement : " + date.toString(), normalFont));
         document.add(new Paragraph("\t\tMontant payé : " + montanPaye + " MAD", normalFont));
         document.add(new Paragraph("\t\tReste à payer : " + resteAPayer + " MAD", normalFont));
         document.add(new Paragraph(" "));

         document.close();
         System.out.println("Done! recupaiementID_" + idPayment + ".pdf created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


            public static void getAllPayments() {
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
                        Facture facture =  FactureDAO.findFactureById(id_facture);
                        Payment payment = new Payment(id_paiement, facture, date_paiement, montant_paye, commission_finpay);
                        payments.add(payment);
                    }
                } catch (SQLException e) {
                    System.err.println("Error displaying payments: " + e.getMessage());
                }


                System.out.println("=========All Payments========= \n");
                for(Payment p: payments){
                    System.out.println("Payment id: " + p.getId() );
                    System.out.println("Amount operation: " + p.getMontanpaye());
                    System.out.println("Date operation: " + p.getDate());
                    System.out.println("Commission= "+p.getCommission());
                    System.out.println("-----------------------------------------");
                }
            }


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

            public static double getTotalMontantPaye() {
                String sql = "SELECT SUM(montant_paye) AS total FROM paiements";
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

            public static double getTotalCommission() {
                String sql = "SELECT SUM(commission_finpay) AS total FROM paiements";
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

        }

