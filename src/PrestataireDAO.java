import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrestataireDAO {
    public static Prestataire RechercherPrestataire(int id) throws Exception{
        Connection conn =DataBase.getConnect();
        String sql="SELECT*FROM PRESTATAIRE WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        Prestataire prestataire =null;

        if(rs.next()){
            prestataire=new Prestataire(
                    rs.getInt("id"),
                    rs.getString("Name")
            );
        }
        conn.close();
        return prestataire;
    }
}
