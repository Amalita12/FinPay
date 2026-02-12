import java.util.Date;

public class Payment {

  private int id;
   private Date date;
  private double montanpaye ;
    private String  paymenttype;
    private  double commission;
    private Facture facture;

    public Payment(int paymentID, Date date, double montanpaye, String paymenttype, double commission) {
        this.id = paymentID;
        this.date = date;
        this.montanpaye = montanpaye;
        this.paymenttype = paymenttype;
        this.commission = commission;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMontanpaye() {
        return montanpaye;
    }

    public void setMontanpaye(double montanpaye) {
        this.montanpaye = montanpaye;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }




    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + id +
                ", date=" + date +
                ", montanpaye=" + montanpaye +
                ", paymenttype='" + paymenttype + '\'' +
                ", commission=" + commission +
                '}';
    }
}
