import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FactureDAO {

    public void addFacture(Facture f) throws Exception{
        Connection con = DataBase.getConnect();
        String sql = "INSERT INTO Facture VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1,f.getID());
        ps.setDate(2, (Date) f.getDate());
        ps.setFloat(3,f.getMontant());
        ps.setString(4,f.getType().name());
        ps.setInt(5,f.getPrestataire().getId());
        ps.setInt(6,f.getClient().getId());

        ps.executeUpdate();
        con.close();
    }

    public void updateFacture(Facture f) throws Exception{
        Connection con = DataBase.getConnect();
        String sql = "UPDATE Facture SET  date=?, montant=? , type=? , id_prestataire=? , id_Client=? WHERE id=? ";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setDate(1, (Date) f.getDate());
        ps.setFloat(2,f.getMontant());
        ps.setString(3,f.getType().name());
        ps.setInt(4,f.getPrestataire().getId());
        ps.setInt(5,f.getClient().getId());
        ps.setInt(6,f.getID());

        ps.executeUpdate();
        con.close();
    }

    public Facture findFacture(int id) throws Exception{
        Connection con = DataBase.getConnect();
        String sql = "SELECT * FROM Ticket WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        System.out.println(rs);
        Facture facture = null;
        if (rs.next()){
            facture = new Facture(
            rs.getInt("id"),
            rs.getDate("date"),
            rs.getFloat("montant"),
            Facture.status.valueOf(rs.getString("type")),
            PrestataireDAO.RechercherPrestataire(rs.getInt("id_prestataire")),
            ClientDAO.RechercherClient(rs.getInt("id_client")));
//            facture.setID(rs.getInt("id"));
//            facture.setDate(rs.getDate("date"));
//            facture.setMontant(rs.getFloat("montant"));
//            facture.setType(rs.getString("type"));
//            Prestataire prest = PrestataireDAO.RechercherPrestataire(rs.getInt("id_prestataire"));
//            facture.setPrestataire(prest);
//            Client clt = ClientDAO.RechercherClient(rs.getInt("id_client"));
//            facture.setClient(clt);
        }
        rs.close();
        ps.close();
        con.close();
        return facture ;
    }


//    public Facture findFactur(int id) throws Exception {
//        Connection conn = DataBase.getConnect();
//        String sql = "SELECT * FROM Ticket";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//
//        List<Facture> Factures = new ArrayList<>();
//
//        while (rs.next()) {
//            Factures.add(new Facture(
//                    rs.getInt("id"),
//                    rs.getDate("date"),
//                    rs.getFloat("montant"),
//                    rs.getString("type"),
//                    rs.getObject("id-prestataire"),
//                    rs.getObject("id-client")
//            ));
//        }
//
//        Facture fac = Factures.stream().filter(f->id == f.getID())
//                .findFirst()
//                .orElse(null);
//
//        conn.close();
//        return fac;
//    }

    public void SupprimerFacture(int id) throws Exception{
        Connection con = DataBase.getConnect();
        String sql = "DELETE FROM FACTURE WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
         ps.setInt(1,id);
         ps.executeUpdate();
         ps.close();
         con.close();
    }




}
