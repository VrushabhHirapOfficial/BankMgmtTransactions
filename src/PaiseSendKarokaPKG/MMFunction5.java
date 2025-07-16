package PaiseSendKarokaPKG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static PaiseSendKarokaPKG.MainMenu.*;

public class MMFunction5 {
    public static void addAmountToWallet(Connection connection, Scanner sc,String contactno){
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(Bankminus);
            PreparedStatement preparedStatement1 = connection.prepareStatement(walletplus);
            System.out.println("Enter Amount to withdraw from bank :");
            int amount = sc.nextInt();
            boolean q = checkbankmoney(connection,amount,contactno);
            if(!q) {
                System.out.println("Insufficient Bank Balance !!!");
                System.out.println("1)Reenter Money \n2) cancel payment\nSelect your choice :");
                int choice3 = sc.nextInt();
                do {
                    if (choice3 == 1) {
                        addAmountToWallet(connection, sc, contactno);
                        break;
                    } else if (choice3 == 2) {
                        menuFunction(connection, sc, contactno);
                        break;
                    } else {
                        System.out.println("Invalid choice !!!!");
                    }
                } while (true);
            }else{
                System.out.println("Enter password of bank : ");
                String password = sc.next();

                boolean u = checkpassOFBANKiscorrectornot(connection,password,contactno);
                if(!u){
                    System.out.println("Invalid Password !!");
                    System.out.println("1) Reenter \n2) cancel \nselect your choice :");
                    int choice1 = sc.nextInt();
                    do{
                        if(choice1 == 1){
                            addAmountToWallet(connection, sc, contactno);
                            break;
                        } else if (choice1 == 2) {
                            menuFunction(connection, sc, contactno);
                            break;
                        } else {
                            System.out.println("Invalid Choice !!!");
                        }
                    }while (true);
                }else {
                    preparedStatement.setInt(1, amount);
                    preparedStatement.setString(2, contactno);
                    preparedStatement1.setInt(1, amount);
                    preparedStatement1.setString(2, contactno);
                    int affected = preparedStatement.executeUpdate();
                    int affect = preparedStatement1.executeUpdate();
                    if (affect > 0 && affected > 0) {
                        connection.commit();
                        System.out.println("Transaction Successful 😊");
                        System.out.println("Money Transfered to the wallet account ");
                        menuFunction(connection, sc, contactno);
                    } else {
                        connection.rollback();
                        System.out.println("Transaction Failed 😭!!");
                        System.out.println("Try again!!!");
                        menuFunction(connection, sc, contactno);
                    }
                }
            }
            preparedStatement1.close();
            preparedStatement.close();

        }catch (SQLException e){
            System.out.println(e.getMessage());

        }

    }

    private static boolean checkbankmoney(Connection connection, int amount,String contactno) {
        try {
            int availablebankbal = 0 ;
            String query = "SELECT BankBalance FROM BankAccount WHERE ContactNo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, contactno);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                availablebankbal = resultSet.getInt("BankBalance");
            }
            return availablebankbal >= amount;

        } catch (SQLException e) {
            System.out.println("Error checking bank balance: " + e.getMessage());
        }
        return false;
    }
    private  static boolean checkpassOFBANKiscorrectornot(Connection connection, String password, String contactno){
        try{
            String query = "SELECT ContactNo FROM BankAccount where ContactNo = ? And Password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,contactno);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Error checking mobile existence : "+e.getMessage());
            return false;
        }

    }

}
