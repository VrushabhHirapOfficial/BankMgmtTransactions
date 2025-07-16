package PaiseSendKarokaPKG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static PaiseSendKarokaPKG.LoginPage.checkpass;
import static PaiseSendKarokaPKG.MainMenu.*;

public class MMFunction3 {
    public static void payToMobileNumber(Connection connection, Scanner sc,String contactno) {
        String payingmobileno;
        do{
            System.out.println("Enter Contact No to pay:");
            payingmobileno = sc.next();

            if(payingmobileno.length()==10){
                break;
            } else{
                System.out.println("Invalid Number Reenter number !");
            }
        }while(true);

        try {
//            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(minusmoney);
            PreparedStatement preparedStatement1 = connection.prepareStatement(plusmoney);


            boolean i = mobileExists(connection,payingmobileno);
            if(!i){
                System.out.println("Invalid Mobile number :");
                System.out.println("1) Reenter \n2) Cancel\nSelect your choice :");
                int choice = sc.nextInt();
                do {
                    if (choice == 1) {
                        payToMobileNumber(connection, sc, contactno);
                        break;
                    } else if (choice == 2) {
                        menuFunction(connection, sc, contactno);
                        break;
                    } else {
                        System.out.println("Invalid choice !!");
                    }
                }while (true);
            } else {

                String pass = checkpass(sc);

                boolean u = checkpassiscorrectornot(connection,pass,contactno);
                if(!u){
                    System.out.println("Invalid Password !!");
                    System.out.println("1) Reenter \n2) cancel \nselect your choice :");
                    int choice1 = sc.nextInt();
                    do{
                        if(choice1 == 1){
                            payToMobileNumber(connection, sc, contactno);
                            break;
                        } else if (choice1 == 2) {
                            menuFunction(connection, sc, contactno);
                            break;
                        } else {
                            System.out.println("Invalid Choice !!!");
                        }
                    }while (true);
                }else {
                    System.out.println("Enter Money to transfer :");
                    int money = sc.nextInt();

                    boolean q = checkwallethavemoney(connection,money,contactno);
                    if(!q){
                        System.out.println("*********************************************");
                        System.out.println("Insufficient Amount !!!");
                        System.out.println("1)Reenter \n2) cancel\nSelect your choice :");
                        int choice3 = sc.nextInt();
                        do{
                            if(choice3 == 1){
                                payToMobileNumber(connection, sc, contactno);
                                break;
                            } else if(choice3 == 2){
                                menuFunction(connection, sc, contactno);
                                break;
                            } else {
                                System.out.println("Invalid choice !!!!");
                            }
                        }while (true);

                    } else {
                        connection.setAutoCommit(false);
                        preparedStatement.setInt(1, money);
                        preparedStatement.setString(2, contactno);

                        preparedStatement1.setInt(1, money);
                        preparedStatement1.setString(2, payingmobileno);

                        int affectedwidraw = preparedStatement.executeUpdate();
                        int affectedcredit = preparedStatement1.executeUpdate();
                        if (affectedcredit > 0 && affectedwidraw > 0) {
                            connection.commit();
                            System.out.println("Transaction SuccessFul!!!😊");
                            connection.setAutoCommit(true);
                            menuFunction(connection, sc, contactno);

                        } else {
                            connection.rollback();
                            System.out.println("Transaction Failed😭");
                            connection.setAutoCommit(true);
                            payToMobileNumber(connection, sc, contactno);


                        }
                    }
                }

            }

        } catch (Exception t){
            System.out.println(t.getMessage());
        }

    }
    private static boolean checkwallethavemoney(Connection connection, int money,String contactno) {
        try {
            int availablewalletbal = 0;
            String query = "SELECT WalletBalance FROM WalletAccount WHERE ContactNo = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, contactno);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                availablewalletbal = resultSet.getInt("WalletBalance");
            }
            return availablewalletbal >= money;
        } catch (SQLException e) {
            System.out.println("Error checking wallet balance: " + e.getMessage());

        }
        return false;
    }


    private static boolean checkpassiscorrectornot(Connection connection, String pass, String contactno) {
        try{
            String query = "SELECT ContactNo FROM WalletAccount where ContactNo = ? And Password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,contactno);
            preparedStatement.setString(2,pass);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            System.out.println("Error checking mobile existence : "+e.getMessage());
            return false;
        }
    }

    public static boolean mobileExists(Connection connection, String payingMobileNo) {
        try {
            String query = "SELECT ContactNo FROM WalletAccount WHERE ContactNo = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, payingMobileNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error checking mobile existence: " + e.getMessage());
            return false;
        }
    }

}
