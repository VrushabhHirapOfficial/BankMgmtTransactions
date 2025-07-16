package PaiseSendKarokaPKG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static PaiseSendKarokaPKG.LoginPage.*;
import static PaiseSendKarokaPKG.MainMenu.*;

public class MMFunction2 {
    public static void checkBalance(Connection connection, Scanner sc,String contactno){
        do {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("1) check Wallet Balance \n2) Check Bank Balance \nChoose your choice :");
            int choice = sc.nextInt();
            if (choice == 1) {
                checkWalletBalance(connection, sc,contactno);
                return;
            } else if (choice == 2) {
                checkBankBalance(connection, sc,contactno);
                return;
            } else {
                System.out.println();
                System.out.println("invalid choide !!!\nChoose again !!!");
                System.out.println();
            }
        }while (true);

    }

    private static void checkBankBalance(Connection connection, Scanner sc,String contactno) {
        try {
            int choice;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlseeBankBalance);
            String password = checkpass(sc);
            boolean i = loginExistsinBank(contactno,password,preparedStatement);
            if(!i){
                System.out.println();
                System.out.println("Account not found :");
                System.out.println("*********************************************");
                System.out.println("1)Reenter \n2)Main menu \nChoose your choice :");
                choice = sc.nextInt();
                if(choice ==1){
                    checkBankBalance(connection, sc,contactno);
                } else if(choice ==2 ){
                    menuFunction(connection,sc,contactno);
                } else {
                    System.out.println("Invalid Choice !!");
                }
            } else {
                preparedStatement.setString(1,contactno);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int bankbal = resultSet.getInt("BankBalance");
                    System.out.println("*****************************************");
                    System.out.println("Your Bank Balance is :"+bankbal);
                    System.out.println("*****************************************");
                }
                menuFunction(connection,sc,contactno);


            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void checkWalletBalance(Connection connection, Scanner sc, String contactno) {
        try {
            int choice;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlseeWalletBalance);
            String password = checkpass(sc);
            boolean i = loginExists(contactno,password,preparedStatement);
            if(!i){
                System.out.println();
                System.out.println("Account not found :");
                System.out.println("----------------------------------------------");
                System.out.println("1)Reenter \n2)Main menu \nChoose your choice :");
                choice = sc.nextInt();
                if(choice ==1){
                    checkWalletBalance(connection, sc,contactno);
                } else if(choice ==2 ){
                    menuFunction(connection,sc,contactno);
                } else {
                    System.out.println("Invalid Choice !!");
                }
            } else {
                preparedStatement.setString(1,contactno);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    int walletbal = resultSet.getInt("WalletBalance");
                    System.out.println("***************************************");
                    System.out.println("Your Wallet Balance is :"+walletbal);
                    System.out.println("***************************************");
                }
                menuFunction(connection,sc,contactno);


            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
