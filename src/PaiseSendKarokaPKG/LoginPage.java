package PaiseSendKarokaPKG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class LoginPage {
    public static void login(Connection connection, Scanner sc, PreparedStatement preparedStatement,String sql,String sqlBank,PreparedStatement preparedStatement1) throws SQLException {
        System.out.println();
        System.out.print("Enter a Phone Number : ");
        String phoneno = sc.next();
        System.out.println();
        System.out.print("Enter a Password : ");
        String password = sc.next();
        System.out.println();

        int choice ;

        boolean i = loginExists(phoneno,password,preparedStatement);
        if(!i){
            System.out.println("Account not found !!!!!");
            System.out.println("****************************************");
            System.out.print("1) Reenter \t\t2) Create Account \n****************************************\nChoose your choice :");
            choice = sc.nextInt();
            if(choice ==1){
                login(connection,sc,preparedStatement,sql,sqlBank,preparedStatement1);
            } else if(choice ==2 ){
                createAcc(connection,sc, sql,preparedStatement,sqlBank,preparedStatement1);
            } else {
                System.out.println("Invalid Choice !!");
            }
        } else {
//            System.out.println("Login successful !!");
            MainMenu.menuFunction(connection,sc,phoneno);

        }

    }
    public static boolean loginExists(String phoneno, String password, PreparedStatement preparedStatement){
        try{
            String query = "SELECT ContactNo, Password FROM WalletAccount WHERE ContactNo  = " + phoneno + " AND Password = '" + password + "';";
            ResultSet resultSet = preparedStatement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean loginExistsinBank(String phoneno, String password, PreparedStatement preparedStatement){
        try{
            String query = "SELECT ContactNo, Password FROM BankAccount WHERE ContactNo  = " + phoneno + " AND Password = '" + password + "';";
            ResultSet resultSet = preparedStatement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String checkpass (Scanner sc){

        int uCase = 0, lCase = 0, sCase = 0;
        char[] ch = new char[16];
        do {
            System.out.println("Enter your password : ");
            String pass = sc.next();
            if (pass.length() <= 16 && pass.length() >= 5) {

                for (int i = 0; i < pass.length(); i++) {
                    ch[i] = pass.charAt(i);
                }

                for (int i = 0; i < pass.length(); i++) {
                    if (ch[i] >= 65 && ch[i] <= 90) {
                        uCase = 1;
                    }

                    if (ch[i] >= 97 && ch[i] <= 122) {
                        lCase = 1;
                    }

                    if (ch[i] >= 33 && ch[i] <= 47 || ch[i] == 64) {
                        sCase = 1;
                    }

                    if (i == pass.length() - 1) {
                        if (uCase == 1 && lCase == 1 && sCase == 1) {
                            return pass;


                        } else {
                            if (uCase != 1) {
                                System.out.println(
                                        "Password should contain at least one uppercase charecter");
                            }
                            if (lCase != 1) {
                                System.out.println(
                                        "Password should contain at least one lowercase charecter");
                            }
                            if (sCase != 1) {
                                System.out.println(
                                        "Password should contain at least one special  charecter");
                            }
                        }
                    }

                }

            } else {
                if (pass.length() >= 16) {
                    System.out.println("Your password is too large !!");
                } else {
                    System.out.println("Your password is too small !!");
                }

            }

        } while (true);
    }
    public static void createWalletAcc(Connection connection, Scanner sc, String sql, String sqlBank,PreparedStatement preparedStatement) throws SQLException {
        System.out.println();
        System.out.println("***************** Create Wallet Account *****************");
        System.out.println();
        System.out.print("Enter Full Name : ");
        String fullname = sc.next()+" "+sc.next();

        String contactno;
        do{
            System.out.println();
            System.out.print("Enter Contact No : ");
            contactno = sc.next();

            if(contactno.length()==10){
                break;
            } else{
                System.out.println();
                System.out.println("Invalid Number Reenter number !");
            }
        }while(true);

        System.out.println();
        System.out.print("Enter E-Mail : ");
        String email = sc.next();



        String gender;
        do {
            System.out.print("\n1) Male\n2) Female\nSelect Gender :");
            int choice = sc.nextInt();

            if (choice == 1) {
                gender = "Male";
                break;
            } else if (choice == 2) {
                gender = "Female";
                break;
            } else {
                System.out.println();
                System.out.println("Invalid choice !!");
            }
        } while (true);

        System.out.println();
        System.out.println("Enter Your full address and end with 'End' keyword :");
        StringBuilder address = new StringBuilder();

        String input;
        while (!(input = sc.next()).equalsIgnoreCase("END")) {
            address.append(input).append(" ");
        }
        String pass;

        pass = checkpass(sc);
        int choice;

        int walletbalance = 0;


        boolean i = loginExistsinBank(contactno,pass,preparedStatement);
        if(!i){
            System.out.println("Account not found !!!!!!!!!");
            System.out.print("1) Reenter \t\t2) Create Account \nChoose your choice :");
            choice = sc.nextInt();
            if(choice ==1){
                createWalletAcc(connection,sc, sql, sqlBank,preparedStatement);
            } else if(choice ==2 ){
                createBankAcc(connection,sc, sql, sqlBank);
            } else {
                System.out.println("Invalid Choice !!");
            }
        } else {


            try {

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, fullname);
                preparedStatement.setString(2, contactno);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, gender);
                preparedStatement.setString(5, address.toString());
                preparedStatement.setString(6, pass);
                preparedStatement.setInt(7, walletbalance);
                int affectedrows = preparedStatement.executeUpdate();

                if(affectedrows > 0){
//                    System.out.println("Login successful !!");
                    MainMenu.menuFunction(connection,sc,contactno);
                } else {
                    System.out.println("Login Failed !!!");
                    System.out.println("Login May Fail If It is Some Technical Problem !!!! Try Again!!!!");
                    createWalletAcc(connection,sc, sql, sqlBank,preparedStatement);
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public static void createBankAcc(Connection connection, Scanner sc, String sql,String sqlBank) throws SQLException{

        System.out.println();
        System.out.println("**************************************");
        System.out.println("******** Create Bank Account!!!*******");
        System.out.println("**************************************");
        System.out.println();
        System.out.print("Enter Full Name : ");
        String fullname = sc.next()+" "+sc.next();
        String contactno;
        do{
            System.out.println();
            System.out.print("Enter Contact No : ");
            contactno = sc.next();

            if(contactno.length()==10){
                break;
            } else{
                System.out.println();
                System.out.print("Invalid Number Reenter number !");
            }
        }while(true);
        System.out.println();
        System.out.print("Enter E-Mail : ");
        String email = sc.next();

        String gender;
        do {
            System.out.println();
            System.out.print("1) Male\t\t2) Female\nSelect Gender : ");
            int choice = sc.nextInt();

            if (choice == 1) {
                gender = "Male";
                break;
            } else if (choice == 2) {
                gender = "Female";
                break;
            } else {
                System.out.println();
                System.out.println("Invalid choice !!");
            }
        }while(true);
        System.out.println();

        System.out.println("Enter Your full address and end with 'End' keyword :");
        StringBuilder address = new StringBuilder();

        String input;
        while (!(input = sc.next()).equalsIgnoreCase("END")) {
            address.append(input).append(" ");
        }

        String pass = checkpass(sc);


        int Bankbalance = 1000;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlBank);

            preparedStatement1.setString(1, fullname);
            preparedStatement1.setString(2,contactno);
            preparedStatement1.setString(3,email);
            preparedStatement1.setString(4,gender);
            preparedStatement1.setString(5, address.toString());
            preparedStatement1.setString(6,pass);
            preparedStatement1.setInt(7,Bankbalance);
            int affectedrows = preparedStatement1.executeUpdate();

            if(affectedrows>0){
                System.out.println("***********************************");
                System.out.println("Bank Account Created !! !");
                System.out.println("***********************************");

                createWalletAcc(connection,sc, sql, sqlBank, preparedStatement1);
            } else {
                System.out.println("***********************************");
                System.out.println("Login Failed !!");
                System.out.println("***********************************");

                createBankAcc(connection,sc, sql, sqlBank);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    public static void createAcc(Connection connection, Scanner sc, String sql,PreparedStatement preparedStatement,String sqlBank,PreparedStatement preparedStatement1) throws SQLException {
        System.out.println();
        System.out.println("************************************************");
        System.out.print("Do you have a Bank Account or not (Y/N) :");
        String yesorno = sc.next();
        if(Objects.equals(yesorno, "y") || Objects.equals(yesorno, "Y")){
            createWalletAcc(connection,sc, sql, sqlBank,preparedStatement);

        } else if(Objects.equals(yesorno, "n") || Objects.equals(yesorno, "N")){
            createBankAcc(connection,sc, sql, sqlBank);
        }

    }
    public static void LoginMain(Connection connection, Scanner sc, PreparedStatement preparedStatement,PreparedStatement preparedStatement1, String sql,String sqlBank)throws SQLException{

        int choice;
        do {
            System.out.println("*****************************************");
            System.out.println("1| Login Account \t\t2| Create Account ");
            System.out.println("*****************************************");
            System.out.print("Choose your choice : ");
            choice = sc.nextInt();
            System.out.println();

            if (choice == 1) {
                login(connection, sc, preparedStatement,sql,sqlBank,preparedStatement1);
                break;
            } else if (choice == 2) {
                createAcc(connection, sc, sql,preparedStatement,sqlBank,preparedStatement1);
                break;
            } else {
                System.out.println("Invalid Choice!!");
            }
        } while (true);


    }

}
