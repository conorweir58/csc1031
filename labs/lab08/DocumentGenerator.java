import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

interface Reader{
    Scanner scanner = new Scanner(System.in);
}

abstract class Document implements Reader {
    protected List<String> content = new ArrayList<>();

    // Templaete method
    public final void generateDocument() {
        createHeader();
        createBody();
        createFooter();
        printDocument();
    }

    private void createHeader() {
        content.add("=== " + this.getClass().getSimpleName().toUpperCase() + " ===");
        System.out.print("Enter company name: ");
        String companyName = scanner.nextLine();
        if (companyName.isEmpty()) {
            System.out.println("Error: Company name cannot be empty.");
            System.exit(0);
            throw new IllegalArgumentException("Company name cannot be empty.");
        }

        System.out.print("Enter date (DD/MM/YYYY): ");
        String date = scanner.nextLine();
        if (date.isEmpty()) throw new IllegalArgumentException("Date cannot be empty.");

        // Add lines to the document
        content.add("Company: " + companyName);
        content.add("Date: " + date);
    }

    public abstract void createBody();

    private void createFooter() {
        if (this instanceof Report) {
            content.add("Reviewed by: Management Department");
        } else {
            content.add("Prepared by: AutoDoc System");
            content.add("Document Type: " + this.getClass().getSimpleName().toUpperCase());
        }
        content.add("=========================");
    }

    private void printDocument()
    {
        System.out.println();
        System.out.println("=== Printing Document ===");

        for(String line : content)
        {
            System.out.println(line);
        }
    }
}
    
class Invoice extends Document {

    public void createBody()
    {
        System.out.print("Enter total amount: ");
        try {
            Double totalDue = scanner.nextDouble();

            if(totalDue <= 0) {
                System.out.println("Error: Total amount must be positive.");
                System.exit(0);
                throw new IllegalArgumentException("Total amount must be positive.");
            }
            content.add("Total Due: €" + totalDue);
        }
        catch (InputMismatchException e)
        {
            System.out.println("Error: Total amount must be numeric.");
            System.exit(0);
        }
    }
}

class Report extends Document {

    public void createBody()
    {
        System.out.print("Enter report summary: ");
        String reportSummary = scanner.nextLine();

        if(reportSummary.isEmpty()) System.out.println("Warning: Summary is empty.");

        content.add("Report Summary: " + reportSummary);
    }
}

class Receipt extends Document{

    public void createBody()
    {
        System.out.print("Enter amount paid: ");
        Double amountPaid = scanner.nextDouble();

        if(amountPaid <= 0) throw new IllegalArgumentException("Amount paid must be positive.");

        content.add("Total Paid: €" + amountPaid);

        System.out.print("Enter number of items: ");
        int itemsCount = scanner.nextInt();

        if(itemsCount <= 0) {
            System.out.println("Error: Items count must be positive.");
            System.exit(0); 
            throw new IllegalArgumentException("Items count must be positive.");
        }

        content.add("Items Purchased: " + itemsCount);

        double pricePerItem = amountPaid / itemsCount;
        content.add("Price per Item: €" + pricePerItem);
    }
}

public class DocumentGenerator implements Reader{

    public static void main(String[] args) {
        Document document = null;
        try {
            System.out.println("Choose document type: (INV) Invoice, (REP) Report, (REC) Receipt");
            String choice = scanner.nextLine();
            switch (choice) {
                case "INV":
                    document = new Invoice();
                    break;
                case "REP":
                    document = new Report();
                    break;
                case "REC":
                    document = new Receipt();
                    break;
                default:
                    System.out.println("Invalid choice. Exiting.");
                    // throw new IllegalArgumentException("Invalid document choice.");
            }
        } finally {
            if(document != null)
            {
                document.generateDocument();
            }
        }
    }
}
            
            

          
