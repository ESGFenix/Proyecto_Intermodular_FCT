package com.view;

import java.util.Scanner;

public class Menu 
{
    private Scanner sc = new Scanner(System.in);
    
    public void showMenu()
    {
        int option = 0;
        System.out.println("Welcome to Alquilaria's renting software!");
        System.out.println("1. Contract options");
        System.out.println("2. Landlord options");
        System.out.println("3. Tenant options");
        System.out.println("4. Tenement options");
        System.out.println("5. House type options");
        System.out.println("6. Exit");
        System.out.print("Please select an option: ");
        
        while(!sc.hasNextInt() && option != 6)
        {
            System.out.println("Invalid option. Please try again.");
            sc.next(); // Clear the invalid input
            System.out.print("Please select an option: ");
        }

        option = sc.nextInt();
        switch (option)
        {
            case 1:
                System.out.println("You selected Contract options.");
                // Call contract options method here
                break;
            case 2:
                System.out.println("You selected Landlord options.");
                // Call landlord options method here
                break;
            case 3:
                System.out.println("You selected Tenant options.");
                // Call tenant options method here
                break;
            case 4:
                System.out.println("You selected Tenement options.");
                // Call tenement options method here
                break;
            case 5:
                System.out.println("You selected House type options.");
                // Call house type options method here
                break;
            case 6:
                System.out.println("Exiting the program. Goodbye!");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    public void showContractMenu()
    {
        int option = 0;
        System.out.println("Contract Options:");
        System.out.println("1. Create Contract");
        System.out.println("2. View Contracts");
        System.out.println("3. Update Contract");
        System.out.println("4. Delete Contract");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please select an option: ");
        
        while(!sc.hasNextInt() && option != 6)
        {
            System.out.println("Invalid option. Please try again.");
            sc.next(); // Clear the invalid input
            System.out.print("Please select an option: ");
        }
        option = sc.nextInt();

        
    }
}
