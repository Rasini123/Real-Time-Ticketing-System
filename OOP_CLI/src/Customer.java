public class Customer implements Runnable {
    // The shared TicketPool between Vendors and Customers
    private TicketPool ticketPool;

    // The frequency (in seconds) at which the customer will retrieve tickets from the pool
    private int customerRetrievalRate;

    // The quantity of tickets the customer wants to purchase
    private int quantity;

    // Constructor to initialize the Customer with required parameters
    public Customer(TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        this.ticketPool = ticketPool; // Assign the shared TicketPool
        this.customerRetrievalRate = customerRetrievalRate; // Set retrieval rate
        this.quantity = quantity; // Set the number of tickets to purchase
    }

    // Implementing the run() method from the Runnable interface
    @Override
    public void run() {
        // Loop to retrieve the specified number of tickets
        for (int i = 0; i < quantity; i++) {
            // Remove a ticket from the pool using the TicketPool's removeTicket method
            Ticket ticket = ticketPool.removeTicket();

            // Log the details of the ticket purchased by the current thread (customer)
            System.out.println("Ticket bought by " + Thread.currentThread().getName() + ". Ticket is " + ticket);

            // Introduce a delay (in milliseconds) to simulate retrieval rate
            try {
                Thread.sleep(customerRetrievalRate * 1000); // Convert seconds to milliseconds
            } catch (InterruptedException e) {
                // Handle interruptions during sleep and propagate the exception
                throw new RuntimeException(e);
            }
        }
    }
}
