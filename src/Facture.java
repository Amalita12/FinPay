import java.util.Date;

public class Facture {
    public static enum status{
        NON_PAYEE, PARTIELLE, PAYEE
    }
    private int ID ;
    private Date date ;
    private float montant ;
//    private String type ;
    private status type;
    private Prestataire prestataire;
    private Client client;

    public Facture(int ID, Date date, float montant, status type, Prestataire prestataire, Client client) {
        this.ID = ID;
        this.date = date;
        this.montant = montant;
        this.type = type;
        this.prestataire = prestataire;
        this.client = client;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public status getType() {
        return type;
    }

    public void setType(status type) {
        this.type = type;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "ID=" + ID +
                ", date=" + date +
                ", montant=" + montant +
                ", type='" + type + '\'' +
                ", prestataire=" + prestataire +
                ", client=" + client +
                '}';
    }
}
