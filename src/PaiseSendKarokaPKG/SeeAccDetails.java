package PaiseSendKarokaPKG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static PaiseSendKarokaPKG.LoginPage.*;
import static PaiseSendKarokaPKG.MainMenu.*;

public class SeeAccDetails {
    private static void seeAccountBank(Connection connection, Scanner sc,String contactno,String sqlseeAccBank ) {
        do{
            System.out.println("1) See Bank Account \n2) Update Bank Account \nSelect Your choice :");
            int choice = sc.nextInt();

            if(choice == 1){
                try {

                    PreparedStatement preparedStatement = connection.prepareStatement(sqlseeAccBank);
                    preparedStatement.setString(1, contactno);
                    ResultSet resultSet = preparedStatement.executeQuery();


                    while(resultSet.next()){
                        int Bankid = resultSet.getInt("BankAccountID");
                        String FullName = resultSet.getString("FullName");
                        String GMail = resultSet.getString("GMail");
                        String Gender = resultSet.getString("Gender");
                        String Address = resultSet.getString("Address");


                        System.out.println();
                        System.out.println("|--------------------------|-------------------------------------|");
                        System.out.println("| Bank Account ID          | " + Bankid);
                        System.out.println("|--------------------------|-------------------------------------|");
                        System.out.println("| Full Name                | " + FullName );
                        System.out.println("|--------------------------|-------------------------------------|");
                        System.out.println("| GMail                    | " + GMail );
                        System.out.println("|--------------------------|-------------------------------------|");
                        System.out.println("| Gender                   | " + Gender );
                        System.out.println("|--------------------------|-------------------------------------|");
                        System.out.println("| Address                  | " + Address );
                        System.out.println("|--------------------------|-------------------------------------|");



                    }
                    menuFunction(connection,sc,contactno);

                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }
                return;
            } else if (choice == 2 ){
                updateBankAccount(connection,sc,contactno);
                System.out.println("Working..****************...");
                return;
            } else {
                System.out.println("Invalid choice !!!");
            }
        }while (true);

    }

    private static void updateBankAccount(Connection connection, Scanner sc, String contactno) {
        System.out.println("1) Name \n2) Contact No \n3) E-mail\n4) Gender\n5) Address\n6) Password\nSelect option to Update :");
        int ch = sc.nextInt();
        do{
            if(ch == 1){
                System.out.println("Enter Full Name :");
                String fullname = sc.next()+" "+sc.next();

                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(sqsqlupdateBankaccNAME);
                    preparedStatement.setString(1, fullname);
                    preparedStatement.setString(2,contactno);

                    int affected = preparedStatement.executeUpdate();
                    if(affected>0){
                        System.out.println("Name is updated!!");
                        seeAccountBank(connection,sc,contactno,sqlseeAccBank);
                    } else {
                        System.out.println("updateation is failed!!");
                        updateWalletAccount(connection,sc,contactno);
                    }
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            } else if (ch == 2) {
                String passbank = "";
                String contactnum ;
                do{
                    System.out.println("Enter Contact new that you want to update  :");
                    contactnum = sc.next();

                    if(contactnum.length()==10){
                        passbank = checkpass(sc);
                        break;
                    } else{
                        System.out.println("Invalid Number Reenter number !");
                    }
                    

                }while(true);
                System.out.println("This action may lead to updation of wallet account no also !\nEnter 1 for yes and no for 2");
                int choice1 = sc.nextInt();
                if(choice1 == 1){
                    try{
                        PreparedStatement preparedStatement = connection.prepareStatement((sqsqlupdatewalletaccCONTACTNO));
                        PreparedStatement preparedStatement1 = connection.prepareStatement(sqsqlupdateBankaccCONTACTNO);
                      //  String pass = checkpass(sc);
                        int choice;

                        boolean i = loginExistsinBank(contactno,passbank,preparedStatement1);
                        if(!i){
                            System.out.println("Account not found :");
                            System.out.println("1)Reenter \n2)Main menu \nChoose your choice :");
                            choice = sc.nextInt();
                            if(choice ==1){
                                updateWalletAccount(connection,sc,contactno);
                            } else if(choice ==2 ){
                                menuFunction(connection,sc,contactno);
                            } else {
                                System.out.println("Invalid Choice !!");
                            }
                        } else {
                            preparedStatement.setString(1, contactnum);
                            preparedStatement.setString(2,contactno);

                            preparedStatement1.setString(1,contactnum);
                            preparedStatement1.setString(2,contactno);

                            int affectedrows = preparedStatement.executeUpdate();
                            int affect = preparedStatement1.executeUpdate();

                            if(affectedrows > 0&& affect >0){
                                System.out.println("contact no is updated !!");
                                seeAccountBank(connection,sc,contactnum,sqlseeAccBank);
                                //menuFunction(connection,sc,contactnum);
                            } else {
                                System.out.println("updation of contact no is failed !!!");
                                menuFunction(connection,sc,contactno);
                            }

                        }
                    } catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                } else if (choice1 == 2) {
                    System.out.println("ok you are back to the update method ");
                    updateBankAccount(connection,sc,contactno);
                }else {
                    System.out.println("Invalid Choice !!!");
                    updateBankAccount(connection,sc,contactno);

                }

            } else if (ch == 3) {
                System.out.println("Enter E-Mail :");
                String email = sc.next();

                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlupdateBankEmail);
                    preparedStatement.setString(1,email);
                    preparedStatement.setString(2,contactno);

                    int affect = preparedStatement.executeUpdate();
                    if(affect>0){
                        System.out.println("Your Email id id updated successfully !!");
                    } else{
                        System.out.println("email id updation failed !!");
                    }
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
                seeAccountBank( connection, sc, contactno, sqlseeAccBank);

            } else if (ch == 4) {
                String gender;
                do {
                    System.out.println("Select Gender :\n1) Male\n2) Female");
                    int choice = sc.nextInt();

                    if (choice == 1) {
                        gender = "Male";
                        break;
                    } else if (choice == 2) {
                        gender = "Female";
                        break;
                    } else {
                        System.out.println("Invalid choice !!");
                    }
                } while (true);

                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlupdateBankaccGender);
                    preparedStatement.setString(1,gender);
                    preparedStatement.setString(2,contactno);

                    int affect = preparedStatement.executeUpdate();
                    if(affect>0){
                        System.out.println("Gender updated !!");
                    }else{
                        System.out.println("updation is failed!!");
                    }

                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }
                seeAccountBank( connection, sc, contactno, sqlseeAccBank);

            } else if (ch == 5) {
                System.out.println("Enter Your full address and end with 'End' keyword :");
                StringBuilder address = new StringBuilder();

                String input;
                while (!(input = sc.next()).equalsIgnoreCase("END")) {
                    address.append(input).append(" ");
                }
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlupdateBankaccAddress);
                    preparedStatement.setString(1,address.toString());
                    preparedStatement.setString(2,contactno);

                    int affect = preparedStatement.executeUpdate();
                    if(affect>0){
                        System.out.println("Address updated !!");
                        seeAccountBank( connection, sc, contactno, sqlseeAccBank);
                    }else{
                        System.out.println("updation is failed!!");
                        updateWalletAccount(connection,sc,contactno);
                    }

                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }


            } else if (ch == 6) {

                try{
                    int choice;
                    String pass;
                    pass = checkpass(sc);
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlupdateBankaccPassword);
                    boolean i = loginExistsinBank(contactno,pass,preparedStatement);
                    if(!i){
                        System.out.println("Account not found :");
                        System.out.println("1)Reenter \n2)Main menu \nChoose your choice :");
                        choice = sc.nextInt();
                        if(choice ==1){
                            updateWalletAccount(connection,sc,contactno);
                        } else if(choice ==2 ){
                            menuFunction(connection,sc,contactno);
                        } else {
                            System.out.println("Invalid Choice !!");
                        }
                    } else {

                        System.out.println("Enter new password :");
                        String passwordbank1 = checkpass(sc);

                        preparedStatement.setString(1, passwordbank1);
                        preparedStatement.setString(2,contactno);


                        int affectedrows = preparedStatement.executeUpdate();


                        if(affectedrows > 0){
                            System.out.println("Password  is updated !!");
                            seeAccountBank( connection, sc, contactno, sqlseeAccBank);
                            menuFunction(connection,sc,contactno);
                        } else {
                            System.out.println("updation of Password is failed !!!");
                            menuFunction(connection,sc,contactno);
                        }

                    }
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }

            } else {
                System.out.println("Invalid Choice !!");
                menuFunction(connection,sc,contactno);
            }
        }while(true);
    }

    private static void seeAccountWallet(Connection connection, Scanner sc,String contactno,String sqlseeAccWallet) {
        do{
            System.out.println();
            System.out.println("*************************************************");
//            System.out.println();
            System.out.print("1) See Wallet Account \t\t2) Update Wallet Account \n*************************************************\nSelect Your choice : ");
            int choice = sc.nextInt();

            if(choice == 1){
                try {

                    PreparedStatement preparedStatement = connection.prepareStatement(sqlseeAccWallet);
                   // PreparedStatement preparedStatement1 = connection.prepareStatement(sqsqlupdatewalletaccNAME)
                    preparedStatement.setString(1, contactno);
                    ResultSet resultSet = preparedStatement.executeQuery();


                    while(resultSet.next()){
                        int WalletID = resultSet.getInt("WalletID");
                        String FullName = resultSet.getString("FullName");
                        String GMail = resultSet.getString("GMail");
                        String Gender = resultSet.getString("Gender");
                        String Address = resultSet.getString("Address");

                        System.out.println();
                        System.out.println("|--------------------------|-------------------------------------|");
                        System.out.println("| Wallet ID                | " + WalletID);
                        System.out.println("|--------------------------|-------------------------------------|");
                        System.out.println("| Full Name                | " + FullName );
                        System.out.println("|--------------------------|-------------------------------------|");
                        System.out.println("| GMail                    | " + GMail );
                        System.out.println("|--------------------------|-------------------------------------|");
                        System.out.println("| Gender                   | " + Gender );
                        System.out.println("|--------------------------|-------------------------------------|");
                        System.out.println("| Address                  | " + Address );
                        System.out.println("|--------------------------|-------------------------------------|");






                    }
                    menuFunction(connection,sc,contactno);
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }
                return;
            } else if (choice ==2 ){
                updateWalletAccount(connection,sc,contactno);
                System.out.println("working!!!");
                return;
            } else {
                System.out.println("Invalid choice !!!");
            }
        }while (true);

    }

    private static void updateWalletAccount(Connection connection, Scanner sc, String contactno) {
        System.out.println("1) Name \n2) Contact No \n3) E-mail\n4) Gender\n5) Address\n6) Password\nSelect option to Update :");
        int ch = sc.nextInt();
        try {

            do {
                if (ch == 1) {
                    System.out.println("Enter Full Name :");
                    String fullname = sc.next() + " " + sc.next();

                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(sqsqlupdatewalletaccNAME);

                        preparedStatement.setString(1, fullname);
                        preparedStatement.setString(2,contactno);
                        int affected = preparedStatement.executeUpdate();
                        if (affected > 0) {
                            System.out.println("Name is updated!!");
                            seeAccountWallet(connection, sc, contactno, sqlseeAccWallet);
                            updateWalletAccount(connection, sc, contactno);

                        } else {
                            System.out.println("updateation is failed!!");
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }

                    return;

                } else if (ch == 2) {
                    String contactnum;
                    do {
                        System.out.println("Enter Contact No :");
                        contactnum = sc.next();

                        if (contactnum.length() == 10) {
                            break;
                        } else {
                            System.out.println("Invalid Number Reenter number !");
                        }
                    } while (true);
                    System.out.println("This action may lead to updation of bank account no also !\nEnter 1 for yes and no for 2");
                    int choice1 = sc.nextInt();
                    if (choice1 == 1) {
                        try {
                            PreparedStatement preparedStatement = connection.prepareStatement((sqsqlupdatewalletaccCONTACTNO));
                            PreparedStatement preparedStatement1 = connection.prepareStatement(sqsqlupdateBankaccCONTACTNO);
                            String pass = checkpass(sc);
                            int choice;

                            boolean i = loginExistsinBank(contactno, pass, preparedStatement);
                            if (!i) {
                                System.out.println("Account not found :");
                                System.out.println("1)Reenter \n2)Main menu \nChoose your choice :");
                                choice = sc.nextInt();
                                if (choice == 1) {
                                    updateWalletAccount(connection, sc, contactno);
                                } else if (choice == 2) {
                                    menuFunction(connection, sc, contactno);
                                } else {
                                    System.out.println("Invalid Choice !!");
                                }
                            } else {
                                preparedStatement.setString(1, contactnum);
                                preparedStatement.setString(2,contactno);

                                preparedStatement1.setString(1, contactnum);
                                preparedStatement1.setString(2,contactno);

                                int affectedrows = preparedStatement.executeUpdate();
                                int affect = preparedStatement1.executeUpdate();

                                if (affectedrows > 0 && affect > 0) {
                                    System.out.println("contact no is updated !!");
                                    seeAccountWallet(connection, sc, contactnum, sqlseeAccWallet);
                                    menuFunction(connection, sc, contactnum);
                                } else {
                                    System.out.println("updation of contact no is failed !!!");
                                    menuFunction(connection, sc, contactno);
                                }

                            }
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (choice1 == 2) {
                        System.out.println("ok you are back to the update method ");
                        updateWalletAccount(connection, sc, contactno);
                    } else {
                        System.out.println("Invalid Choice !!!");
                        updateWalletAccount(connection, sc, contactno);

                    }
                    return;

                } else if (ch == 3) {
                    System.out.println("Enter E-Mail :");
                    String email = sc.next();

                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(sqsqlupdateBankaccEMAIL);
                        preparedStatement.setString(1, email);
                        preparedStatement.setString(2, contactno);
                        int affect = preparedStatement.executeUpdate();
                        if (affect > 0) {
                            System.out.println("Your Email id id updated successfully !!");
                        } else {
                            System.out.println("email id updation failed !!");
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    seeAccountWallet(connection, sc, contactno, sqlseeAccWallet);
                    return;
                } else if (ch == 4) {
                    String gender;
                    do {
                        System.out.println("Select Gender :\n1) Male\n2) Female");
                        int choice = sc.nextInt();

                        if (choice == 1) {
                            gender = "Male";
                            break;
                        } else if (choice == 2) {
                            gender = "Female";
                            break;
                        } else {
                            System.out.println("Invalid choice !!");
                        }
                    } while (true);

                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(sqsqlupdateBankaccGender);
                        preparedStatement.setString(1, gender);
                        preparedStatement.setString(2,contactno);

                        int affect = preparedStatement.executeUpdate();
                        if (affect > 0) {
                            System.out.println("Gender updated !!");
                        } else {
                            System.out.println("updation is failed!!");
                        }

                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    seeAccountWallet(connection, sc, contactno, sqlseeAccWallet);
                    return;

                } else if (ch == 5) {
                    System.out.println("Enter Your full address and end with 'End' keyword :");
                    StringBuilder address = new StringBuilder();

                    String input;
                    while (!(input = sc.next()).equalsIgnoreCase("END")) {
                        address.append(input).append(" ");
                    }
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(sqsqlupdateBankaccAddress);
                        preparedStatement.setString(1, address.toString());
                        preparedStatement.setString(2,contactno);

                        int affect = preparedStatement.executeUpdate();
                        if (affect > 0) {
                            System.out.println("Address updated !!");
                        } else {
                            System.out.println("updation is failed!!");
                        }

                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    seeAccountWallet(connection, sc, contactno, sqlseeAccWallet);
                    return;

                } else if (ch == 6) {
                    try {
                        int choice;
                        String pass;
                        pass = checkpass(sc);
                        PreparedStatement preparedStatement = connection.prepareStatement(sqsqlupdateBankaccPassword);
                        boolean i = loginExists(contactno, pass, preparedStatement);
                        if (!i) {
                            System.out.println("Account not found :");
                            System.out.println("1)Reenter \n2)Main menu \nChoose your choice :");
                            choice = sc.nextInt();
                            if (choice == 1) {
                                updateWalletAccount(connection, sc, contactno);
                            } else if (choice == 2) {
                                menuFunction(connection, sc, contactno);
                            } else {
                                System.out.println("Invalid Choice !!");
                            }
                        } else {
                            System.out.println("Enter New Password :");
                            String password = checkpass(sc);
                            preparedStatement.setString(1, password);
                            preparedStatement.setString(2,contactno);


                            int affectedrows = preparedStatement.executeUpdate();


                            if (affectedrows > 0) {
                                System.out.println("Password  is updated !!");
                                seeAccountWallet(connection, sc, contactno, sqlseeAccWallet);
                                menuFunction(connection, sc, contactno);
                            } else {
                                System.out.println("updation of Password is failed !!!");
                                menuFunction(connection, sc, contactno);
                            }

                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    return;

                } else {
                    System.out.println("Invalid Choice !!");
                    menuFunction(connection, sc, contactno);
                }
            } while (true);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void seeAccountDetails(Connection connection, Scanner sc,String contactno,String sqlseeAccWallet,String sqlseeAccBank) {
        do{
            System.out.println();
            System.out.println("******************************************");
//            System.out.println();
            System.out.print("1) Wallet Account \t\t2) Bank Account \n******************************************\nSelect your choice : ");
            int choice = sc.nextInt();
            if(choice ==1 ){
                seeAccountWallet(connection,sc,contactno,sqlseeAccWallet);
                break;
            } else if(choice == 2){
                seeAccountBank(connection,sc,contactno,sqlseeAccBank);
                break;
            } else{
                System.out.println("Invalid Choice !!");
            }
        }while (true);

    }



}
