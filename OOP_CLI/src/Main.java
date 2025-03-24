import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration configuration = null;

        System.out.println("Welcome to the Ticketing System!");
        String userChoice = getUserChoice(scanner);

        while (true) {
            // Load or configure a new event setup
            if (!userChoice.equals("YES")) {
                configuration = configureNewEvent(scanner);
                if (configuration == null) continue;
            } else {
                configuration = Configuration.loadFromJson();
                if (configuration == null) {
                    System.out.println("No saved configuration found. Starting fresh.");
                    configuration = configureNewEvent(scanner);
                }
            }

            // After configuration is successful, proceed with ticket system simulation
            startTicketSystem(configuration);

            break; // Exit after successful setup and ticket system start
        }

        scanner.close();
    }

    private static String getUserChoice(Scanner scanner) {
        System.out.print("Do you want to load previous settings (yes/no)? ");
        String choice = scanner.nextLine().toUpperCase().trim();
        while (!(choice.equals("YES") || choice.equals("NO"))) {
            System.out.print("Invalid input. Please enter 'yes' or 'no': ");
            choice = scanner.nextLine().toUpperCase().trim();
        }
        return choice;
    }

    private static Configuration configureNewEvent(Scanner scanner) {
        try {
            System.out.println("Enter details for the new event:");

            // Event name and location
            System.out.print("Event Name: ");
            String eventName = scanner.nextLine();

            System.out.print("Venue Location: ");
            String venueLocation = scanner.nextLine();

            // Ticket price and total ticket availability
            double ticketCost = getDoubleInput(scanner, "Ticket Price: ");
            int totalTicketsAvailable = getIntInput(scanner, "Total Tickets Available: ", 1);

            // Ticket release rate and retrieval rate
            int releaseRate = getIntInput(scanner, "Ticket Release Rate (1-15): ", 1, 15);
            int retrievalRate = getIntInput(scanner, "Customer Retrieval Rate (1-15): ", 1, 15);

            // Ticket pool capacity
            int maxPoolCapacity = getIntInput(scanner, "Maximum Ticket Pool Capacity: ", 1, totalTicketsAvailable - 1);

            // Save the configuration
            Configuration configuration = new Configuration(eventName, venueLocation, ticketCost, totalTicketsAvailable, releaseRate, retrievalRate, maxPoolCapacity);
            configuration.saveToJson();
            configuration.saveToTextFile();

            return configuration;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct data types.");
            scanner.nextLine(); // Clear the buffer
            return null;
        }
    }

    private static double getDoubleInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextDouble();
    }

    private static int getIntInput(Scanner scanner, String prompt, int minValue) {
        int value = 0;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input
            }
            value = scanner.nextInt();
        } while (value < minValue);
        return value;
    }

    private static int getIntInput(Scanner scanner, String prompt, int minValue, int maxValue) {
        int value = 0;
        do {
            value = getIntInput(scanner, prompt, minValue);
            if (value > maxValue) {
                System.out.println("Value must not exceed " + maxValue);
            }
        } while (value > maxValue);
        return value;
    }

    private static void startTicketSystem(Configuration configuration) {
        TicketPool pool = new TicketPool(configuration.getMaxTicketPoolCapacity());

        // Create and start vendor threads
        List<Thread> vendorThreads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Vendor vendor = new Vendor(pool, configuration.getTotalTickets(), configuration.getTicketReleaseRate(), configuration);
            Thread vendorThread = new Thread(vendor, "Vendor-" + i);
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Create and start customer threads
        List<Thread> customerThreads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer(pool, configuration.getCustomerRetrievalRate(), 10);
            Thread customerThread = new Thread(customer, "Customer-" + i);
            customerThreads.add(customerThread);
            customerThread.start();
        }


        for (Thread vendorThread : vendorThreads) {
            try {
                vendorThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        for (Thread customerThread : customerThreads) {
            try {
                customerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
