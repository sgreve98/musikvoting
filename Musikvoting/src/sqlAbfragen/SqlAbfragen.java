package sqlAbfragen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sqlDummys.*;

public class SqlAbfragen {

	//private static final String JDBC_URL = "jdbc:mysql://your_database_url:port/Musikvoting";
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/musikvoting?";
    private static final String USERNAME = "Steven";
    private static final String PASSWORD = "Peterpan123";

    private static Connection connection;

    private static Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        }
        return connection;
    }
    
    public List<User> getUser() {
        List<User> userList = new ArrayList<>();

        String query = "SELECT * FROM t_user";
        String driver = "com.mysql.cj.jdbc.Driver";

        try {
        	Class.forName(driver);
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Couldn't print users.");
        }

        return userList;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        String sql = "SELECT * FROM t_user";

        try (
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("username"), resultSet.getBoolean("gastgeber"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userList;
    }

	public void setUser(String username, boolean gastgeber){
		String sql = "INSERT INTO User (username, gastgeber) VALUES (?, ?)";

        try (
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.setBoolean(2, gastgeber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User inserted successfully!");
            } else {
                System.out.println("Failed to insert user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
            	connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}

	public void setTitel(String titel, String interpret, String genre){
		String sql = "INSERT INTO Titel (titel, interpret, genre) VALUES (?, ?, ?)";

        try (
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, titel);
            preparedStatement.setString(2, interpret);
            preparedStatement.setString(3, genre);


            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Titel inserted successfully!");
            } else {
                System.out.println("Failed to insert Titel.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
            	connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}

	public List<Titel> getTitelListe(){
		List<Titel> titelList = new ArrayList<Titel>();
        String sql = "SELECT * FROM Titel";

        try (
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
            	Titel titel = new Titel(resultSet.getString("titel"), resultSet.getString("interpret"), resultSet.getString("genre"));
            	titelList.add(titel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return titelList;
	}

	public void voteTitel(String titelname, String username){

	}

	/*public List<Titel> getPlaylist(){

	}*/
}
