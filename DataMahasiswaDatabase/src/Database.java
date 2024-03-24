import java.sql.*;

public class Database {
    private Connection connection;

    private Statement statement;
    //constructors
    public Database(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/db_mahasiswa", "root", "");
            statement = connection.createStatement();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //digunakan untuk SELECT
    public ResultSet selectQuery(String sql){
        try{
            statement.executeQuery(sql);
            return statement.getResultSet();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //digunakan untuk INSERT, UPDATE, dan DELETE
    public int insertUpdateDeleteQuery(String sql){
        try{
            return statement.executeUpdate(sql);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //getter
    public Statement getStatement(){
        return statement;
    }
}