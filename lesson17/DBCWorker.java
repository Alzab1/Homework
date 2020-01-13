import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCWorker {
    public User getUserInfo(int userId) {
        String url = "jdbc:mysql://localhost:3306/user?" +
                "useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC";
        String userDB = "root";
        String passwordDB = "pvtpvt2019";

        String query = String.format("SELECT * FROM userdata WHERE id = %s", userId);
        try (Connection con = DriverManager.getConnection(url, userDB, passwordDB);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setDomain(rs.getString("domain"));
                user.setMail1(rs.getString("mail1"));
                user.setMail2(rs.getString("mail2"));
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("user was not found");
        return null;
    }
}