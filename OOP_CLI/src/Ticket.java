import java.math.BigDecimal;

public class Ticket {
    // Unique identifier for the ticket
    private int ticketId;

    // Name of the event associated with the ticket
    private String eventName;

    // Price of the ticket, using BigDecimal to handle monetary values precisely
    private BigDecimal ticketPrice;

    // Constructor to initialize the ticket object with its details
    public Ticket(int ticketId, String eventName, BigDecimal ticketPrice) {
        this.ticketId = ticketId; // Assign the unique ticket ID
        this.eventName = eventName; // Assign the event name
        this.ticketPrice = ticketPrice; // Assign the ticket price
    }

    // Getter for ticket ID
    public int getTicketId() {
        return ticketId;
    }

    // Setter for ticket ID
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    // Getter for the event name
    public String getEventName() {
        return eventName;
    }

    // Getter for the ticket price
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    // Setter for the ticket price
    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    // Overrides the default toString method to provide ticket details as a string
    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
