package PaiseSendKarokaPKG;

import java.sql.Connection;
import java.util.Scanner;

public class MMFunction7 {
    public static void exitTheapp(Connection connection, Scanner sc) throws InterruptedException {
        System.out.println("System Longing Out!!!!");
        int i=5;
        while(i != 0){
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println();
        System.out.println("Thank you for Using Mobile Pay !!!");
        System.out.println("Thank you for Getting touch!!!!");

    }
}
