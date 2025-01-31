import java.util.ArrayList;

public class Fridge {
    private ArrayList<String> foodItems = new ArrayList<>();
    private int balance = 0;

    public Fridge(int initialBalance) {
        if(initialBalance < 0) {
            System.out.println("Error");
            this.balance = 0;
            return;
        }
        this.balance = initialBalance;
    }

    public void addFood(String item, int cost) {
        if(this.balance < cost || item == null || cost < 0) {
            System.out.println("Error");
            return;
        }
        this.foodItems.add(item);
        this.balance -= cost;

        System.out.printf("Item %s has been added to the fridge.\n", item);
    }

    public void getFood(String item) {
        if(this.foodItems.contains(item))
        {
            this.foodItems.remove(item);

            System.out.printf("Item %s has been removed from the fridge.\n", item);
        }
        else
        {
            System.out.println("Error");
        }
    }

    public void checkStatus() {
        System.out.println("Food items:");
        if(this.foodItems.size() == 0) {
            System.out.println("(none)");
        }
        else{
            for (String item : this.foodItems) {
                System.out.println(item);
            }
        }
        System.out.println("Balance: €" + this.balance);
    }

    public static void main(String[] args) {
        Fridge fridge = new Fridge(5); // Initial balance: €5

        fridge.addFood(null, 3);
        fridge.addFood("Bread", 1);

        // Not enough money now
        // Error message should appear
        fridge.addFood("Eggs", 4);

        fridge.getFood("Milk");
        fridge.getFood("Juice"); // Error message should appear

        fridge.checkStatus();

        System.out.println("Program executed successfully.");
    }
}



