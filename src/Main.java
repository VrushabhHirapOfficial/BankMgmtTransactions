import java.sql.*;
import java.util.Scanner;
import static PaiseSendKarokaPKG.LoginPage.LoginMain;


public class Main {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/PaiseSendKaro";
    private static final String username = "root";
    private static final String password = "vruhirapsh9981";
    static String sqlWallet = "INSERT INTO WalletAccount (FullName, ContactNo, GMail, Gender, Address, Password, WalletBalance) " +
            "VALUES (?,?,?,?,?,?,?)";

    static String sqlBank = "INSERT INTO BankAccount (FullName, ContactNo, GMail, Gender, Address, Password, BankBalance) " +
            "VALUES (?,?,?,?,?,?,?)";


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//This is the statement of loading the drivers

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlWallet);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlBank);
            LoginMain(connection,sc,preparedStatement,preparedStatement1,sqlWallet,sqlBank);

            connection.close();
            preparedStatement.close();
            preparedStatement1.close();
            sc.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}