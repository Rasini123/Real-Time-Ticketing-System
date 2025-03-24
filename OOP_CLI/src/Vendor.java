import java.math.BigDecimal;

public class Vendor implements Runnable {
    // The shared TicketPool among Vendors and Customers
    private TicketPool ticketPool;

    // Total number of tickets the Vendor will sell
    private int totalTickets;

    // Rate at which tickets will be added to the pool (in seconds)
    private int ticketReleaseRate;

    // Configuration object containing event details (e.g., name and price)
    private Configuration configuration;

    // Constructor to initialize the Vendor with necessary parameters
    public Vendor(TicketPool ticketPool, int totalTickets, int ticketReleaseRate, Configuration configuration) {
        this.ticketPool = ticketPool; // Assign the shared TicketPool
        this.totalTickets = totalTickets; // Total number of tickets to add
        this.ticketReleaseRate = ticketReleaseRate; // Delay between adding tickets
        this.configuration = configuration; // Event configuration details
    }

    // Implementing the run() method from Runnable interface
    @Override
    public void run() {
        // Loop to create and add the specified number of tickets to the pool
        for (int i = 1; i <= totalTickets; i++) {
            // Create a new Ticket object with a unique ID, event name, and price
            Ticket ticket = new Ticket(i, configuration.getName(), new BigDecimal(configuration.getPrice()));

            // Add the created ticket to the TicketPool
            ticketPool.addTickets(ticket);

            // Introduce a delay (in milliseconds) to simulate ticket release frequency
            try {
                Thread.sleep(ticketReleaseRate * 1000); // Convert seconds to milliseconds
            } catch (InterruptedException e) {
                // Handle thread interruption during sleep
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
