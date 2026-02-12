import java.sql.Date;
import java.sql.Timestamp;

public class Facture {
    private int idFacture;
    private Client client;
    private Prestataire prestataire;
    private double montantTotal;
    private Statut statut;
    private Date dateFacture;
    private Timestamp dateCreation;

    public Facture(int idFacture, Client client, Prestataire prestataire,
                   double montantTotal, Statut statut, Date dateFacture, Timestamp dateCreation) {
        this.idFacture = idFacture;
        this.client = client;
        this.prestataire = prestataire;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.dateFacture = dateFacture;
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Facture{id=" + idFacture + ", client=" + client +
                ", prestataire=" + prestataire + ", montant=" + montantTotal +
                ", statut=" + statut + ", dateFacture=" + dateFacture +
                ", dateCreation=" + dateCreation + "}";
    }
}