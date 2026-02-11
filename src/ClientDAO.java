import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClientDAO {

    private int id;

    //Ajouter
    public void AjouterClient(Client C) throws Exception{
        Connection conn = DBConnection.getConnection();
        String sql = "Insert into client values(?,?)";
        PreparedStatement ps= conn.prepareStatement(sql);

        ps.setInt(1,C.getId());
        ps.setString(2,C.getName());

        ps.executeUpdate();
        conn.close();

    }
    //Afdicher
    public ArrayList<Client>AffiherClient(int id)throws Exception{
        Connection conn=DBConnection.getConnection();
        String sql= "SELECT*FROM Client";
        PreparedStatement ps= conn.prepareStatement(sql);
        ResultSet rs= ps.executeQuery();

        ArrayList<Client> Clients=new ArrayList<>();

        while (rs.next()){
            Clients.add(new Client(
                    rs.getInt("id"),
                    rs.getString("Name")
            ));
        }
        Clients.stream().filter(c-> id==c.id)
                        .forEach(System.out::println);
//        Clients.forEach(System.out::println);
        conn.close();
        return Clients;
    }

    //Rechercher
    public Client RechercherClient(int id) throws Exception{
        Connection conn =DBConnection.getConnection();
        String sql="SELECT*FROM CLIENT WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        Client client=null;

        if(rs.next()){
            client=new Client(
                    rs.getInt("id"),
                    rs.getString("Name")
            );

        }
        conn.close();
        return client;
    }

//Supprimer
    public void SupprimerClient(int id)throws Exception{
        Connection conn=DBConnection.getConnection();
        String sql="DELET FROM CLIENT WHERE id=?";
        PreparedStatement ps= conn.prepareStatement(sql);

        ps.setInt(1,id);
        ps.executeUpdate();
        conn.close();
    }

}
