import java.io.*;
import java.util.*;

class Shop {
    private int pcode, stock;
    private float price, discount;
    private String pname;
    private Scanner sc = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("\t\t\t\t\t_______________________");
            System.out.println("\t\t\t\t\t                       ");
            System.out.println("\t\t\t\t\t  Shopping Menu Bar    ");
            System.out.println("\t\t\t\t\t                       ");
            System.out.println("\t\t\t\t\t_______________________");
            System.out.println("\t\t\t\t\t|   1) Administrator    |");
            System.out.println("\t\t\t\t\t|   2) Buyer            |");
            System.out.println("\t\t\t\t\t|   3) Exit             |");
            System.out.println("\t\t\t\t\t|_______________________|");

            System.out.print("\n\t\t\t\t   Please select an option: ");
            int choice = getValidChoice(3);
            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> buyer();
                case 3 -> System.exit(0);
            }
        }
    }

    private void adminLogin() {
        System.out.println("\t\t\t\t   Please Login");
        System.out.print("\t\t\t\t   Enter Email: ");
        String email = sc.next();
        System.out.print("\t\t\t\t   Enter Password: ");
        String password = sc.next();

        if ("manoj@gmail.com".equals(email) && "manoj@123".equals(password)) {
            administrator();
        } else {
            System.out.println("\t\t\t\t   Invalid email/password");
        }
    }

    private void administrator() {
        while (true) {
            System.out.println("\n\n\t\t\t\t\t   Administrator Menu");
            System.out.println("\t\t\t\t\t|   1) Add the Product    |");
            System.out.println("\t\t\t\t\t|   2) Modify the Product |");
            System.out.println("\t\t\t\t\t|   3) Delete the Product |");
            System.out.println("\t\t\t\t\t|   4) Search the Product |");
            System.out.println("\t\t\t\t\t|   5) Low Stock Alert    |");
            System.out.println("\t\t\t\t\t|   6) Back to Main Menu  |");

            System.out.print("\n\t\t\t\t\t   Please enter your choice: ");
            int choice = getValidChoice(6);
            switch (choice) {
                case 1 -> add();
                case 2 -> edit();
                case 3 -> remove();
                case 4 -> searchProduct();
                case 5 -> lowStockAlert();
                case 6 -> {
                    return;
                }
            }
        }
    }

    private void buyer() {
        while (true) {
            System.out.println("\t\t\t\t\t   Buyer");
            System.out.println("\t\t\t\t\t|   1) Buy Product   |");
            System.out.println("\t\t\t\t\t|   2) Go Back       |");

            System.out.print("\t\t\t\t\t   Enter your choice: ");
            int choice = getValidChoice(2);
            if (choice == 1) {
                receipt();
            } else {
                return;
            }
        }
    }

    private int getValidChoice(int maxChoice) {
        int choice = -1;
        while (choice < 1 || choice > maxChoice) {
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > maxChoice) {
                    System.out.println("\t\t\t\t   Invalid input. Please select a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\t\t\t\t   Invalid input. Please enter a number.");
                sc.next(); // Clear invalid input
            }
        }
        return choice;
    }

    private void add() {
        System.out.println("\n\n\t\t\t\t   Add New Product");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("database.txt", true))) {
            System.out.print("\t\t\t\t   Product Code: ");
            pcode = sc.nextInt();
            System.out.print("\t\t\t\t   Name of the Product: ");
            pname = sc.next();
            System.out.print("\t\t\t\t   Price of the Product: ");
            price = sc.nextFloat();
            System.out.print("\t\t\t\t   Discount on the Product: ");
            discount = sc.nextFloat();
            System.out.print("\t\t\t\t   Stock of the Product: ");
            stock = sc.nextInt();

            writer.write(pcode + " " + pname + " " + price + " " + discount + " " + stock + "\n");
            System.out.println("\n\t\t\t   Product Added!");
        } catch (IOException e) {
            System.out.println("\n\t\t\t   Error writing to the file: " + e.getMessage());
        }
    }

    private void edit() {
        System.out.print("\t\t\t\t   Enter Product Code to Modify: ");
        int pkey = sc.nextInt();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("database.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int code = Integer.parseInt(parts[0]);

                if (code == pkey) {
                    found = true;
                    System.out.print("\t\t\t\t   New Product Code: ");
                    pcode = sc.nextInt();
                    System.out.print("\t\t\t\t   New Product Name: ");
                    pname = sc.next();
                    System.out.print("\t\t\t\t   New Product Price: ");
                    price = sc.nextFloat();
                    System.out.print("\t\t\t\t   New Discount: ");
                    discount = sc.nextFloat();
                    System.out.print("\t\t\t\t   Stock of the Product: ");
                    stock = sc.nextInt();
                    writer.write(pcode + " " + pname + " " + price + " " + discount + " " + stock + "\n");
                } else {
                    writer.write(line + "\n");
                }
            }

            if (!found) {
                System.out.println("\t\t\t\t   Product Not Found!");
            }
        } catch (IOException e) {
            System.out.println("\t\t\t\t   Error modifying the product!");
        }

        new File("database.txt").delete();
        new File("temp.txt").renameTo(new File("database.txt"));
    }

    private void remove() {
        System.out.print("\n\t\t\t\t   Enter Product Code to Delete: ");
        int pkey = sc.nextInt();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("database.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int code = Integer.parseInt(parts[0]);

                if (code == pkey) {
                    found = true;
                    System.out.println("\t\t\t\t   Product Deleted Successfully!");
                } else {
                    writer.write(line + "\n");
                }
            }

            if (!found) {
                System.out.println("\t\t\t\t   Product Not Found!");
            }
        } catch (IOException e) {
            System.out.println("\t\t\t\t   Error deleting the product: " + e.getMessage());
        }

        new File("database.txt").delete();
        new File("temp.txt").renameTo(new File("database.txt"));
    }

    private void searchProduct() {
        System.out.print("\t\t\t\t   Enter Product Name or Code to Search: ");
        String searchTerm = sc.next();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("database.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts[1].equalsIgnoreCase(searchTerm) || parts[0].equals(searchTerm)) {
                    found = true;
                    System.out.println("\n\t\t\t\t   Product Found:");
                    System.out.println("\nProNo.\tName\tPrice\tDiscount\tStock");
                    System.out.println(parts[0] + "\t" + parts[1] + "\t" + parts[2] + "\t" + parts[3] + "\t" + parts[4]);
                    break;
                }
            }

            if (!found) {
                System.out.println("\t\t\t\t   Product Not Found!");
            }
        } catch (IOException e) {
            System.out.println("\t\t\t\t   Error searching for the product: " + e.getMessage());
        }
    }

    private void lowStockAlert() {
        try (BufferedReader reader = new BufferedReader(new FileReader("database.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int stock = Integer.parseInt(parts[4]);

                if (stock < 10) {
                    System.out.println("\nProduct with Low Stock:");
                    System.out.println("Product Code: " + parts[0]);
                    System.out.println("Product Name: " + parts[1]);
                    System.out.println("Price: " + parts[2]);
                    System.out.println("Stock Remaining: " + parts[4]);
                }
            }
        } catch (IOException e) {
            System.out.println("\t\t\t\t   Error checking stock: " + e.getMessage());
        }
    }

    private void list() {
        try (BufferedReader reader = new BufferedReader(new FileReader("database.txt"))) {
            System.out.println("\n\n__________________________________");
            System.out.println("ProNo.\t\tName\t\tPrice");
            System.out.println("\n\n__________________________________");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                System.out.println(parts[0] + "\t\t" + parts[1] + "\t\t" + parts[2]);
            }
        } catch (IOException e) {
            System.out.println("\t\t\t   Error reading the file!");
        }
    }

    private void receipt() {
        int[] arrc = new int[100];
        int[] arrq = new int[100];
        int c = 0;
        char choice;
        float total = 0;

        System.out.println("\n\t\t\t\t   RECEIPT");
        list();

        do {
            System.out.print("\n\t\t\t\t   Enter Product Code: ");
            arrc[c] = sc.nextInt();
            System.out.print("\t\t\t\t   Enter Quantity: ");
            arrq[c] = sc.nextInt();

            c++;

            System.out.print("\t\t\t\t   Buy another product? (y/n): ");
            choice = sc.next().charAt(0);
        } while (choice == 'y');

        System.out.println("\nProduct No\t ProductName\t Quantity\t Price\t Amount\t Discounted Amount");

        try (BufferedReader reader = new BufferedReader(new FileReader("database.txt"))) {
            for (int i = 0; i < c; i++) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    int code = Integer.parseInt(parts[0]);

                    if (code == arrc[i]) {
                        float amount = Float.parseFloat(parts[2]) * arrq[i];
                        float disAmount = amount - (amount * Float.parseFloat(parts[3]) / 100);
                        total += disAmount;
                        System.out.println(parts[0] + "\t\t" + parts[1] + "\t\t" + arrq[i] + "\t\t" + parts[2] + "\t" + amount + "\t" + disAmount);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("\t\t\t   Error reading the receipt!");
        }

        System.out.println("\n\n__________________________________________");
        System.out.println(" Total Amount: " + total);
    }

    public static void main(String[] args) {
        Shop s = new Shop();
        s.menu();
    }
}