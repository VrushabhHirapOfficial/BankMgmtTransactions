package PaiseSendKarokaPKG;

import java.sql.Connection;
import java.util.Scanner;

import static PaiseSendKarokaPKG.SeeAccDetails.*;
import static PaiseSendKarokaPKG.MMFunction2.*;
import static PaiseSendKarokaPKG.MMFunction3.*;
import static PaiseSendKarokaPKG.MMFunction4.*;
import static PaiseSendKarokaPKG.MMFunction5.*;
import static PaiseSendKarokaPKG.MMFunction6.*;
import static PaiseSendKarokaPKG.MMFunction7.*;


public class MainMenu {

    static String sqlseeAccWallet = "SELECT * FROM WalletAccount WHERE ContactNo = ?";
    static String sqlseeAccBank = "SELECT * FROM BankAccount WHERE ContactNo = ?";


    static String  sqsqlupdatewalletaccNAME = "UPDATE WalletAccount SET FullName = ? WHERE ContactNo = ?;";
    static String  sqsqlupdateBankaccNAME = "UPDATE BankAccount SET FullName = ? WHERE ContactNo = ?;";



    static String  sqsqlupdatewalletaccCONTACTNO = "UPDATE WalletAccount SET ContactNo = ? WHERE ContactNo = ?;";
    static String  sqsqlupdateBankaccCONTACTNO = "UPDATE BankAccount SET ContactNo = ? WHERE ContactNo = ?;";



    static String  sqsqlupdateBankaccEMAIL = "UPDATE WalletAccount SET GMail = ? WHERE ContactNo = ?;";
    static String  sqlupdateBankEmail = "UPDATE BankAccount SET GMail = ? WHERE ContactNo = ?;";




    static String  sqsqlupdateBankaccGender = "UPDATE WalletAccount SET Gender = ? WHERE ContactNo = ?;";
    static String  sqlupdateBankaccGender = "UPDATE BankAccount SET Gender = ? WHERE ContactNo = ?;";





    static String  sqsqlupdateBankaccAddress = "UPDATE WalletAccount SET Address = ? WHERE ContactNo = ?;";

    static String  sqlupdateBankaccAddress = "UPDATE BankAccount SET Address = ? WHERE ContactNo = ?;";





    static String  sqsqlupdateBankaccPassword = "UPDATE WalletAccount SET Password = ? WHERE ContactNo = ?;";
    static String  sqlupdateBankaccPassword = "UPDATE BankAccount SET Password = ? WHERE ContactNo = ?;";














    //Funcition 2 queries;
    static String sqlseeBankBalance = "Select BankBalance from BankAccount where ContactNo = ? ";
    static String sqlseeWalletBalance = "Select WalletBalance from WalletAccount where ContactNo = ? ";


    //Funtion 3 queries
    static String minusmoney = "UPDATE WalletAccount SET WalletBalance = WalletBalance - ? WHERE ContactNo = ?;";
    static String plusmoney = "UPDATE WalletAccount SET WalletBalance = WalletBalance + ? WHERE ContactNo = ?;";


    //Funtion 5 queries

    static String Bankminus = "UPDATE BankAccount SET BankBalance = BankBalance - ? WHERE ContactNo = ?;";

    static String walletplus = "UPDATE WalletAccount SET WalletBalance = WalletBalance + ? WHERE ContactNo = ?;";


    //Funtion 6 quriees

    static String Bankplus = "UPDATE BankAccount SET BankBalance = BankBalance + ? WHERE ContactNo = ?;";

    static String walletminus = "UPDATE WalletAccount SET WalletBalance = WalletBalance - ? WHERE ContactNo = ?;";










    public static void menuFunction(Connection connection, Scanner sc,String contactno){
        System.out.println();
        System.out.println("*************** Hello To The Main Menu ***************");
        try{
            do{
                System.out.println();
                System.out.print("1) See Account Details \t\t    2) Check Balance \n3) Pay to Mobile Number \t\t4) Switch Account \n5) Add amount to wallet \t\t6) Add amount to Bank \n7) Exit \n\n*************** Hello To The Main Menu ***************\n\nSelect Your choice :");
                int choice = sc.nextInt();
                System.out.println();

                if(choice == 1){
                    seeAccountDetails(connection, sc,contactno,sqlseeAccWallet,sqlseeAccBank);
                    break;
                } else if (choice == 2) {
                    checkBalance(connection, sc,contactno);
                    break;
                } else if (choice == 3) {
                    payToMobileNumber(connection,sc,contactno);
                    break;
                } else if (choice == 4) {
                    switchAccount(connection,sc);
                    break;
                } else if (choice == 5) {
                    addAmountToWallet(connection,sc,contactno);
                    break;
                }else if (choice == 6) {
                    addAmountToBank(connection,sc,contactno);
                    break;
                } else if (choice == 7) {
                    exitTheapp(connection,sc);
                    break;
                } else{
                    System.out.println("Invalid choice !!!");
                }


            }while (true);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

}
