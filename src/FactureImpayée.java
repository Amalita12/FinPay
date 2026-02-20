import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FactureImpayée {


    public static void execute(List<Facture> factures) {

        System.out.println("Nombre total de factures reçues : " + factures.size());

        for (Facture f : factures) {
            System.out.println("ID: " + f.getIdFacture() +
                    " | Statut en objet: " + f.getStatut());
        }

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

                long diffMillis = System.currentTimeMillis() - f.getDateFacture().getTime();
                long joursRetard = diffMillis / (1000 * 60 * 60 * 24);
                joursRetard = Math.max(joursRetard, 0);

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

        // Try-with-resources (fermeture automatique)
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
}
