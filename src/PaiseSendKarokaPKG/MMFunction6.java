package PaiseSendKarokaPKG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static PaiseSendKarokaPKG.MainMenu.*;
import static PaiseSendKarokaPKG.MainMenu.menuFunction;

public class MMFunction6 {
    public static void addAmountToBank(Connection connection, Scanner sc,String contactno){
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(walletminus);
            PreparedStatement preparedStatement1 = connection.prepareStatement(Bankplus);
            System.out.println("Enter Amount to Credit money in Bank :");
            int amount = sc.nextInt();



            boolean q = checkWalletmoney(connection,amount,contactno);
            if(!q) {
                System.out.println("Insufficient Wallet Balance to Transfer !!!");
                System.out.println("1)Reenter Money \n2) cancel payment\nSelect your choice :");
                int choice3 = sc.nextInt();
                do {
                    if (choice3 == 1) {
                        addAmountToBank(connection,sc,contactno);
                        break;
                    } else if (choice3 == 2) {
                        menuFunction(connection, sc, contactno);
                        break;
                    } else {
                        System.out.println("Invalid choice !!!!");
                    }
                } while (true);
            }else {

                System.out.println("Enter password of Wallet : ");
                String password = sc.next();

                boolean u = checkpassOFWALLETiscorrectornot(connection, password, contactno);
                if (!u) {
                    System.out.println("Invalid Password !!");
                    System.out.println("1) Reenter \n2) cancel \nselect your choice :");
                    int choice1 = sc.nextInt();
                    do {
                        if (choice1 == 1) {
                            addAmountToBank(connection, sc, contactno);
                            break;
                        } else if (choice1 == 2) {
                            menuFunction(connection, sc, contactno);
                            break;
                        } else {
                            System.out.println("Invalid Choice !!!");
                        }
                    } while (true);
                }else{
                    preparedStatement.setInt(1, amount);
                    preparedStatement.setString(2, contactno);

                    preparedStatement1.setInt(1, amount);
                    preparedStatement1.setString(2, contactno);
                    int affected = preparedStatement.executeUpdate();
                    int affect = preparedStatement1.executeUpdate();
                    if (affect > 0 && affected > 0) {
                        connection.commit();
                        System.out.println("Transaction Successful 😊");
                        System.out.println("Money Transferred to the Bank account ");
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
    private static boolean checkWalletmoney(Connection connection, int amount,String contactno) {
        try {
            int availablewalletBal = 0 ;
            String query = "SELECT WalletBalance FROM WalletAccount WHERE ContactNo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, contactno);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                availablewalletBal = resultSet.getInt("WalletBalance");
            }
            if(availablewalletBal >= amount){
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error checking wallet balance: " + e.getMessage());
        }
        return false;
    }
    private  static boolean checkpassOFWALLETiscorrectornot(Connection connection, String password, String contactno){
        try{
            String query = "SELECT ContactNo FROM WalletAccount where ContactNo = ? And Password = ?;";
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
