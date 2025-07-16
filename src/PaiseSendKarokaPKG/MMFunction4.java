package PaiseSendKarokaPKG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static PaiseSendKarokaPKG.LoginPage.LoginMain;

public class MMFunction4 {
    public static void switchAccount(Connection connection, Scanner sc) throws SQLException {
        String sqlWallet = "INSERT INTO WalletAccount (FullName, ContactNo, GMail, Gender, Address, Password, WalletBalance) " +
                "VALUES (?,?,?,?,?,?,?)";

        String sqlBank = "INSERT INTO BankAccount (FullName, ContactNo, GMail, Gender, Address, Password, BankBalance) " +
                "VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlWallet);
        PreparedStatement preparedStatement1 = connection.prepareStatement(sqlBank);
        LoginMain(connection,sc,preparedStatement,preparedStatement1,sqlWallet,sqlBank);
    }
}
