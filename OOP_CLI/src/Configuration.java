import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Configuration {
    private String name;
    private String location;
    private double price;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketPoolCapacity;

    private static final String CONFIG_JSON = "event_config.json";
    private static final String CONFIG_TXT = "event_config.txt";

    // Constructor to initialize the setup with parameters
    public Configuration(String name, String location, double price, int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketPoolCapacity) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketPoolCapacity = maxTicketPoolCapacity;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketPoolCapacity() {
        return maxTicketPoolCapacity;
    }

    public void setMaxTicketPoolCapacity(int maxTicketPoolCapacity) {
        this.maxTicketPoolCapacity = maxTicketPoolCapacity;
    }

    // Save the setup data to a JSON file
    public void saveToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(CONFIG_JSON)) {
            gson.toJson(this, writer); // Serialize this object to JSON
            System.out.println("Event settings saved to JSON file: " + CONFIG_JSON);
        } catch (IOException e) {
            System.err.println("Error saving to JSON: " + e.getMessage());
        }
    }

    // Save the setup data to a plain text file
    public void saveToTextFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_TXT))) {
            writer.write("Event Name: " + this.name + "\n");
            writer.write("Venue Location: " + this.location + "\n");
            writer.write("Ticket Price: " + this.price + "\n");
            writer.write("Total Tickets Available: " + this.totalTickets + "\n");
            writer.write("Ticket Release Rate: " + this.ticketReleaseRate + "\n");
            writer.write("Customer Retrieval Rate: " + this.customerRetrievalRate + "\n");
            writer.write("Maximum Ticket Pool Capacity: " + this.maxTicketPoolCapacity + "\n");
            System.out.println("Event settings saved to Text file: " + CONFIG_TXT);
        } catch (IOException e) {
            System.err.println("Error saving to Text file: " + e.getMessage());
        }
    }

    // Load event configuration from a JSON file
    public static Configuration loadFromJson() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(CONFIG_JSON)) {
            return gson.fromJson(reader, Configuration.class); // Deserialize JSON to Setup object
        } catch (IOException e) {
            System.err.println("Error loading configuration from JSON: " + e.getMessage());
        }
        return null; // Return null if there's an issue loading the JSON
    }
}
