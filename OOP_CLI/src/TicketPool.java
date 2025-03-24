import java.util.List;
import java.util.Vector;

public class TicketPool {
    // A thread-safe list to store tickets
    private List<Ticket> ticketQueue;

    // Maximum number of tickets that can be held in the pool
    private int maximumCapacity;

    // Constructor to initialize the ticket pool with a given capacity
    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity; // Set the maximum capacity
        this.ticketQueue = new Vector<>(); // Initialize the ticket queue using Vector for thread-safety
    }

    // Add Ticket method which is used by Vendors to add tickets to the pool
    public synchronized void addTickets(Ticket ticket) { // Need to synchronize
        while (ticketQueue.size() >= maximumCapacity) { // Check if pool is full
            try {
                wait(); // Wait until space is available
            } catch (InterruptedException e) {
                // Handle thread interruption and propagate the exception
                throw new RuntimeException(e.getMessage());
            }
        }

        this.ticketQueue.add(ticket); // Add the ticket to the pool
        notifyAll(); // Notify all waiting threads about the change

        // Print the thread name and current pool size after adding a ticket
        System.out.println(Thread.currentThread().getName() + " has added a ticket to the Pool. Current size is " + ticketQueue.size());
    }

    // Buy Ticket method is used by Customers when buying tickets (CONSUMER)
    public synchronized Ticket removeTicket() {
        while (ticketQueue.isEmpty()) { // Check if pool is empty
            try {
                wait(); // Wait until a ticket becomes available
            } catch (InterruptedException e) {
                // Handle thread interruption and propagate the exception
                throw new RuntimeException(e.getMessage());
            }
        }

        Ticket ticket = ticketQueue.remove(0); // Remove the first ticket from the pool
        notifyAll(); // Notify all waiting threads about the change

        // Print the thread name, current pool size, and details of the removed ticket
        System.out.println(Thread.currentThread().getName() + " has bought a ticket from the Pool. Current size is " + ticketQueue.size() + ". Ticket is " + ticket);

        return ticket; // Return the removed ticket
    }
}
