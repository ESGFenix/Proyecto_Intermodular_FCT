package com.view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import com.controller.*;
import com.model.Database;

public class Menu 
{
    private static Scanner sc = new Scanner(System.in);
    
    public static boolean showMenu(Database db)
    {
        
        System.out.println("---------------------------------------------------------------------------------------------------");
        int option = 0;
        System.out.println("Welcome to Alquilaria's renting software!");
        System.out.println("1. Contract options");
        System.out.println("2. Landlord options");
        System.out.println("3. Tenant options");
        System.out.println("4. Tenement options");
        System.out.println("5. House type options");
        System.out.println("6. Advanced queries");
        System.out.println("7. Export data");
        System.out.println("8. Exit");
        System.out.print("Please select an option: ");
        
        while(!sc.hasNextInt())
        {
            System.out.println("Invalid option. Please try again.");
            sc.next(); // Clear the invalid input
            System.out.print("Please select an option: ");
        }

        option = sc.nextInt();
        switch (option)
        {
            case 1:
                while(showContractMenu(db));
                break;
            case 2:
                while(showLandlordMenu(db));
                break;
            case 3:
                while(showTenantMenu(db));
                break;
            case 4:
                while(showTenementMenu(db));
                break;
            case 5:
                System.out.println("You selected House type options.");
                // Call house type options method here
                break;
            case 6:
                System.out.println("You selected House type options.");
                // Call house type options method here
                break;
            case 7:
                System.out.println("You selected House type options.");
                // Call house type options method here
                break;
            case 8:
                System.out.println("Exiting the program. Goodbye!");
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    public static boolean showContractMenu(Database db)
    {
        System.out.println("---------------------------------------------------------------------------------------------------");
        int option = 0;
        System.out.println("Contract Options:");
        System.out.println("1. Create Contract");
        System.out.println("2. View Contracts");
        System.out.println("3. Update Contract");
        System.out.println("4. Change Contract Status");
        System.out.println("5. Delete Contract");
        System.out.println("6. Back to Main Menu");
        System.out.print("Please select an option: ");
        
        while(!sc.hasNextInt())
        {
            System.out.println("Invalid option. Please try again.");
            sc.next(); // Clear the invalid input
            System.out.print("Please select an option: ");
        }
        option = sc.nextInt();
        sc.nextLine();

        switch (option) 
        {
            case 1:
                int contract_status;
                System.out.print("Enter the tenant's ID: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid tenant ID.");
                    sc.next(); // Clear the invalid input
                }
                int id_tenant = sc.nextInt();
                sc.nextLine();

                System.out.print("Insert the tenement's ID: ");
                String id_tenement = sc.nextLine();

                System.out.print("Insert the rent price: ");
                while (!sc.hasNextFloat()) 
                {
                    System.out.println("Invalid input. Please enter a valid rent price.");    
                    sc.nextLine();
                }
                float rent_price = sc.nextFloat();
                sc.nextLine();
                    
                System.out.print("Enter the starting date of the contract (yyyy-MM-dd): ");
                Date start_date = Date.valueOf(sc.nextLine());

                System.out.print("Enter the finishing date of the contract (yyyy-MM-dd): ");
                Date end_date = Date.valueOf(sc.nextLine());

                System.out.print("Enter the contract status (1. ACTIVE / 2. PENDING)");
                
                while(true)
                {
                    if (sc.nextInt() == 1)
                    {
                        contract_status = 1;
                        break;
                    }
                    else if (sc.nextInt() == 2)
                    {
                        contract_status = 2;
                        break;
                    }

                    else
                        System.out.println("Invalid option, please insert a valid contract status");
                }

                Contract.InsertContract(id_tenement, id_tenant, start_date, end_date, rent_price, contract_status, db);
                System.out.println("Contract created succesfully!");
                break;
        
            case 2:
                System.out.print("Enter the tenement's ID: ");
                id_tenement = sc.nextLine().toUpperCase();

                System.out.print("Enter the tenant's ID: ");
                while (!sc.hasNextInt()) 
                {
                    System.out.println("Invalid input. Please enter a valid tenant ID.");
                    sc.next(); // Clear the invalid input
                }
                id_tenant = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter the contract's starting date (yyyy-MM-dd): ");
                start_date = Date.valueOf(sc.nextLine());

                Contract.SelectContract(id_tenement, id_tenant, start_date, db);
                break;
                
            case 3:
                System.out.print("Enter the tenant's ID: ");
                while (!sc.hasNextInt()) 
                {
                    System.out.println("Invalid input. Please enter a valid tenant ID.");
                    sc.next(); // Clear the invalid input
                }
                id_tenant = sc.nextInt();
                sc.nextLine();

                System.out.print("Insert the tenement's ID: ");
                id_tenement = sc.nextLine();

                System.out.print("Insert the rent price: ");
                while (!sc.hasNextFloat()) 
                {
                    System.out.println("Invalid input. Please enter a valid rent price.");    
                    sc.nextLine();
                }
                rent_price = sc.nextFloat();
                sc.nextLine();
                    
                System.out.print("Enter the starting date of the contract (yyyy-MM-dd): ");
                start_date = Date.valueOf(sc.nextLine());

                System.out.print("Enter the finishing date of the contract (yyyy-MM-dd): ");
                end_date = Date.valueOf(sc.nextLine());

                System.out.print("Enter the contract status (1. ACTIVE / 2. PENDING)");
                
                while(true)
                {
                    if (sc.nextInt() == 1)
                    {
                        contract_status = 1;
                        break;
                    }
                    else if (sc.nextInt() == 2)
                    {
                        contract_status = 2;
                        break;
                    }

                    else
                        System.out.println("Invalid option, please insert a valid contract status");
                }

                Contract.ModifyContract(id_tenement, id_tenant, start_date, end_date, rent_price, contract_status, db);
                System.out.println("Contract updated succesfully!");
                break;
                
            case 4:
                System.out.print("Enter the tenement's ID: ");
                id_tenement = sc.nextLine();

                System.out.print("Enter the tenant's ID: ");
                while (!sc.hasNextInt()) 
                {
                    System.out.println("Invalid input. Please enter a valid tenant ID.");
                    sc.next(); // Clear the invalid input
                }
                id_tenant = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter the contract's starting date (yyyy-MM-dd): ");
                start_date = Date.valueOf(sc.nextLine());

                System.out.print("Enter the new contract status (1. ACTIVE / 2. PENDING / 3. EXPIRED): ");
                while(true)
                {
                    if (sc.nextInt() == 1)
                    {
                        contract_status = 1;
                        break;
                    }
                    else if (sc.nextInt() == 2)
                    {
                        contract_status = 2;
                        break;
                    }
                    else if (sc.nextInt() == 3)
                    {
                        contract_status = 3;
                        break;
                    }
                    else
                        System.out.println("Invalid option, please insert a valid contract status");
                }
                Contract.ContractStateChange(id_tenement, id_tenant, start_date, contract_status, db);
                System.out.println("Contract status updated succesfully!");
                break;
                
            case 5:
                System.out.print("Enter the tenement's ID: ");
                id_tenement = sc.nextLine();

                System.out.print("Enter the tenant's ID: ");
                while (!sc.hasNextInt()) 
                {
                    System.out.println("Invalid input. Please enter a valid tenant ID.");
                    sc.next(); // Clear the invalid input
                }
                id_tenant = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter the contract's starting date (yyyy-MM-dd): ");
                start_date = Date.valueOf(sc.nextLine());

                Contract.DeleteContract(id_tenement, id_tenant, start_date, db);
                break;
            case 6:
                System.out.println("Returning to the main menu...");
                return false;
            default:
            System.out.println("Please, select a valid option.");
                break;
        }
        return true;
    }

    public static boolean showLandlordMenu(Database db)
    {
        System.out.println("---------------------------------------------------------------------------------------------------");
        int option = 0;
        System.out.println("Landlord Options:");
        System.out.println("1. Create Landlord");
        System.out.println("2. View Landlords");
        System.out.println("3. Update Landlord");
        System.out.println("4. Delete Landlord");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please select an option: ");
        
        while(!sc.hasNextInt())
        {   
            System.out.println("Invalid option. Please try again.");
            sc.next(); // Clear the invalid input
            System.out.print("Please select an option: ");
        }
        option = sc.nextInt();
        sc.nextLine(); // Consume the newline character


        switch (option) 
        {
            case 1:
                System.out.print("Enter the landlord's DNI: ");
                String DNI = sc.nextLine().toUpperCase();

                System.out.print("Enter the landlord's name: ");
                String name = sc.nextLine();
                
                ArrayList<String> phone_numbers = new ArrayList<String>();

                while (true) 
                {
                    System.out.print("Insert the landlord's phone number/s (leave empty to stop): ");
                    String phone = sc.nextLine();
                    if (phone.isEmpty() || phone.isBlank()) 
                    {
                        if(phone_numbers.size() == 0)
                        {
                            System.out.print("Please, insert at least one phone number.");
                            continue;
                        }

                        break;
                    } 
                    else
                        phone_numbers.add(phone);
                }

                ArrayList<String> emails = new ArrayList<String>();

                while (true) 
                {
                    System.out.print("Insert the landlord's email/s (leave empty to stop): ");
                    String email = sc.nextLine();
                    if (email.isBlank())
                    {
                        if(emails.size() == 0)
                        {
                            System.out.println("Please, insert at least one email.");
                            continue;
                        }
                        else
                            break;
                    }
                    emails.add(email);
                }

                Landlord.InsertLandlord(DNI, name, phone_numbers, emails, db);
                break;
        
            case 2:
                System.out.print("Enter the Landlord's ID: ");
                while (!sc.hasNextInt()) 
                {
                    System.out.println("Invalid input. Please enter a valid ID.");    
                    sc.nextLine();
                }
                int id_landlord = sc.nextInt();
                sc.nextLine();

                Landlord.SelectLandlord(id_landlord, db);
                break;
                
            case 3:
                System.out.print("Enter the landlord's ID: ");
                while (!sc.hasNextInt()) 
                {
                    System.out.println("Invalid input. Please enter a valid tenant ID.");
                    sc.next(); // Clear the invalid input
                }
                id_landlord = sc.nextInt();
                sc.nextLine();

                System.out.print("Insert the landlord's DNI: ");
                DNI = sc.nextLine();
                    
                System.out.print("Insert the landlord's name: ");
                name = sc.nextLine();

                phone_numbers = new ArrayList<String>();

                while (true) 
                {
                    System.out.print("Insert the landlord's phone number/s (leave empty to stop): ");
                    String phone = sc.nextLine();
                    if (phone.isEmpty() || phone.isBlank()) 
                    {
                        if(phone_numbers.size() == 0)
                        {
                            System.out.print("Please, insert at least one phone number.");
                            continue;
                        }

                        break;
                    } 
                    else
                        phone_numbers.add(phone);
                }

                emails = new ArrayList<String>();

                while (true) 
                {
                    System.out.print("Insert the landlord's email/s (leave empty to stop): ");
                    String email = sc.nextLine();
                    if (email.isBlank())
                    {
                        if(emails.size() == 0)
                        {
                            System.out.println("Please, insert at least one email.");
                            continue;
                        }
                        else
                            break;
                    }
                    emails.add(email);
                }

                Landlord.ModifyLandlord(id_landlord, DNI, name, phone_numbers, emails, db);
                System.out.println("Landlord updated succesfully!");
                break;
                
            case 4:
                System.out.print("Enter the landlord's ID: ");
                int landlord_id = sc.nextInt();
                sc.nextLine();

                Landlord.DeleteLandlord(landlord_id, db);
                System.out.println("Landlord deleted succesfully!");
                break;
            case 5:
                System.out.println("Returning to the main menu...");
                return false;
            default:
            System.out.println("Please, select a valid option.");
                break;
        }
        return true;
    }

    public static boolean showTenantMenu(Database db)
    {
        System.out.println("---------------------------------------------------------------------------------------------------");
        int option = 0;
        System.out.println("Tenant Options:");
        System.out.println("1. Create Tenant");
        System.out.println("2. View Tenant");
        System.out.println("3. Update Tenant");
        System.out.println("4. Delete Tenant");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please select an option: ");
        
        while(!sc.hasNextInt())
        {   
            System.out.println("Invalid option. Please try again.");
            sc.next(); // Clear the invalid input
            System.out.print("Please select an option: ");
        }
        option = sc.nextInt();
        sc.nextLine(); // Consume the newline character


        switch (option) 
        {
            case 1:
                System.out.print("Enter the tenant's DNI: ");
                String DNI = sc.nextLine().toUpperCase();

                System.out.print("Enter the tenant's name: ");
                String name = sc.nextLine();
                
                System.out.print("Does the tenant have a pet? (1. Yes / Any other number. No): ");
                while(!sc.hasNextInt())
                {
                    System.out.println("Invalid input. Please enter a valid option.");
                    sc.next();
                }
                int hasPet = sc.nextInt() == 1 ? 1 : 0;

                ArrayList<String> phone_numbers = new ArrayList<String>();

                while (true) 
                {
                    System.out.print("Insert the tenant's phone number/s (leave empty to stop): ");
                    String phone = sc.nextLine();
                    if (phone.isEmpty() || phone.isBlank()) 
                    {
                        if(phone_numbers.size() == 0)
                        {
                            System.out.print("Please, insert at least one phone number.");
                            continue;
                        }
                        break;
                    } 
                    else
                        phone_numbers.add(phone);
                }

                ArrayList<String> emails = new ArrayList<String>();

                while (true) 
                {
                    System.out.print("Insert the landlord's email/s (leave empty to stop): ");
                    String email = sc.nextLine();
                    if (email.isBlank())
                    {
                        if(emails.size() == 0)
                        {
                            System.out.println("Please, insert at least one email.");
                            continue;
                        }
                        else
                            break;
                    }
                    emails.add(email);
                }

                Tenant.InsertTenant(DNI, name, hasPet, phone_numbers, emails, db);
                break;
        
            case 2:
                System.out.print("Enter the Tenant's ID: ");
                while (!sc.hasNextInt()) 
                {
                    System.out.println("Invalid input. Please enter a valid ID.");    
                    sc.nextLine();
                }
                int id_tenant = sc.nextInt();
                sc.nextLine();

                Tenant.SelectTenant(id_tenant, db);
                break;
                
            case 3:
                System.out.print("Enter the tenant's ID: ");
                while (!sc.hasNextInt()) 
                {
                    System.out.println("Invalid input. Please enter a valid tenant ID.");
                    sc.next();
                }
                id_tenant = sc.nextInt();
                sc.nextLine();

                System.out.print("Insert the tenant's DNI: ");
                DNI = sc.nextLine();
                    
                System.out.print("Insert the tenant's name: ");
                name = sc.nextLine();

                System.out.print("Does the tenant have a pet? (1. Yes / Any other number. No): ");
                while(!sc.hasNextInt())
                {
                    System.out.println("Invalid input. Please enter a valid option.");
                    sc.next();
                }
                hasPet = sc.nextInt() == 1 ? 1 : 0;

                phone_numbers = new ArrayList<String>();

                while (true) 
                {
                    System.out.print("Insert the tenant's phone number/s (leave empty to stop): ");
                    String phone = sc.nextLine();
                    if (phone.isEmpty() || phone.isBlank()) 
                    {
                        if(phone_numbers.size() == 0)
                        {
                            System.out.print("Please, insert at least one phone number.");
                            continue;
                        }

                        break;
                    } 
                    else
                        phone_numbers.add(phone);
                }

                emails = new ArrayList<String>();

                while (true) 
                {
                    System.out.print("Insert the tenant's email/s (leave empty to stop): ");
                    String email = sc.nextLine();
                    if (email.isBlank())
                    {
                        if(emails.size() == 0)
                        {
                            System.out.println("Please, insert at least one email.");
                            continue;
                        }
                        else
                            break;
                    }
                    emails.add(email);
                }

                Tenant.ModifyTenant(id_tenant, DNI, name, hasPet, emails, phone_numbers, db);
                System.out.println("Tenant updated succesfully!");
                break;
                
            case 4:
                System.out.print("Enter the Tenant's ID: ");
                id_tenant = sc.nextInt();
                sc.nextLine();

                Tenant.DeleteTenant(id_tenant, db);
                System.out.println("Tenant deleted succesfully!");
                break;
            case 5:
                System.out.println("Returning to the main menu...");
                return false;
            default:
            System.out.println("Please, select a valid option.");
                break;
        }
        return true;
    }

    public static boolean showTenementMenu(Database db)
    {
        System.out.println("---------------------------------------------------------------------------------------------------");
        int option = 0;
        System.out.println("Tenement Options:");
        System.out.println("1. Create Tenement");
        System.out.println("2. View Tenement");
        System.out.println("3. Update Tenement");
        System.out.println("4. Delete Tenement");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please select an option: ");
        
        while(!sc.hasNextInt())
        {   
            System.out.println("Invalid option. Please try again.");
            sc.next();
            System.out.print("Please select an option: ");
        }
        option = sc.nextInt();
        sc.nextLine();


        switch (option) 
        {
            case 1:
                System.out.print("Enter the tenement's ID (Preferably in this format: TXXX): ");
                String id_tenement = sc.nextLine().toUpperCase();

                System.out.print("Enter the tenement's owner ID: ");
                while(!sc.hasNextInt())
                {
                    System.out.println("Invalid input. Please enter a valid ID.");
                    sc.next();
                }
                int id_landlord = sc.nextInt();
                
                System.out.print("Enter the tenement's rent price: ");
                while(!sc.hasNextFloat())
                {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next();
                }
                float rent = sc.nextFloat();

                System.out.print("Enter the tenement's surface: ");
                while(!sc.hasNextFloat())
                {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next();
                }
                float surface = sc.nextFloat();

                System.out.print("Enter the tenement's description: ");
                String description = sc.nextLine();

                System.out.print("Enter the tenement's type (consult at the House Type menu): ");
                while(!sc.hasNextInt())
                {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.next();
                }
                int house_type = sc.nextInt();
                sc.nextLine();

                System.out.print("Does the tenement accept pets? (1. Yes / Any other number. No): ");
                while(!sc.hasNextInt())
                {
                    System.out.println("Invalid input. Please enter a valid option.");
                    sc.next();
                }
                int acceptsPets = sc.nextInt() == 1 ? 1 : 0;

                System.out.print("Enter the tenement's address: ");
                String address = sc.nextLine();

                Tenement.InsertTenement(id_tenement, id_landlord, rent, surface, description, house_type, acceptsPets, address, db);
                break;
        
            case 2:
                System.out.print("Enter the Tenement's ID: ");
                id_tenement = sc.nextLine().toUpperCase();
                System.out.print("Enter the Tenement's owner ID: ");
                while(!sc.hasNextInt())
                {
                    System.out.println("Invalid input. Please enter a valid ID.");
                    sc.next();
                }
                id_landlord = sc.nextInt();
                sc.nextLine();

                Tenement.SelectTenement(id_tenement, id_landlord, db);
                break;
                
            case 3:
                System.out.print("Enter the tenement's ID (Preferably in this format: TXXX): ");
                id_tenement = sc.nextLine().toUpperCase();

                System.out.print("Enter the tenement's owner ID: ");
                while(!sc.hasNextInt())
                {
                    System.out.println("Invalid input. Please enter a valid ID.");
                    sc.next();
                }
                id_landlord = sc.nextInt();
                
                System.out.print("Enter the tenement's rent price: ");
                while(!sc.hasNextFloat())
                {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next();
                }
                rent = sc.nextFloat();

                System.out.print("Enter the tenement's surface: ");
                while(!sc.hasNextFloat())
                {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next();
                }
                surface = sc.nextFloat();

                System.out.print("Enter the tenement's description: ");
                description = sc.nextLine();

                System.out.print("Enter the tenement's type (consult at the House Type menu): ");
                while(!sc.hasNextInt())
                {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.next();
                }
                house_type = sc.nextInt();
                sc.nextLine();

                System.out.print("Does the tenement accept pets? (1. Yes / Any other number. No): ");
                while(!sc.hasNextInt())
                {
                    System.out.println("Invalid input. Please enter a valid option.");
                    sc.next();
                }
                acceptsPets = sc.nextInt() == 1 ? 1 : 0;

                System.out.print("Enter the tenement's address: ");
                address = sc.nextLine();

                Tenement.ModifyTenement(id_tenement, id_landlord, rent, surface, description, house_type, acceptsPets, address, db);
                break;
                
            case 4:
                System.out.print("Enter the Tenement's ID: ");
                id_tenement = sc.nextLine().toUpperCase();
                System.out.print("Enter the Tenement's owner ID: ");
                while(!sc.hasNextInt())
                {
                    System.out.println("Invalid input. Please enter a valid ID.");
                    sc.next();
                }
                id_landlord = sc.nextInt();
                sc.nextLine();

                Tenement.DeleteTenement(id_tenement, id_landlord, db);
                System.out.println("Tenant deleted succesfully!");
                break;
            case 5:
                System.out.println("Returning to the main menu...");
                return false;
            default:
            System.out.println("Please, select a valid option.");
                break;
        }
        return true;
    }
}
