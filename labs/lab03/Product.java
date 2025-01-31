import java.util.ArrayList;
import java.util.List;

public class Product {
    private String productName;
    private long price;
    private boolean inStock;
    private List <String> tags; // abstraction

    public Product()
    {
        this.productName = "Unknown";
        this.price = 0;
        this.inStock = false;
        this.tags  = new ArrayList <String> ();
    }

    public Product(String productName)
    {
        // Validate Product Name
        if(productName == null || productName.isEmpty())
        {
            this.productName = "Unknown";
        }
        else
        {
            this.productName = productName;
        }
        this.price = 0;
        this.inStock = false;
        this.tags  = new ArrayList <String> ();
    }

    public Product(String productName, long price)
    {
        // Validate Product Name
        if(productName == null || productName.isEmpty())
        {
            this.productName = "Unknown";
        }
        else
        {
            this.productName = productName;
        }

        // Validate price
        if(price < 0)
        {
            this.price = 0;
        }
        else
        {
           this.price = price;
        }
        this.inStock = false;
        this.tags  = new ArrayList <String> ();
    }

    public Product(String productName, long price, boolean inStock)
    {
        // Validate Product Name
        if(productName == null || productName.isEmpty())
        {
            this.productName = "Unknown";
        }
        else
        {
            this.productName = productName;
        }
        // Validate price
        if(price < 0)
        {
            this.price = 0;
        }
        else
        {
           this.price = price;
        }
        this.inStock = inStock;
        this.tags  = new ArrayList <String> ();
    }

    public Product(String productName, long price, List <String> tags)
    {
        // Validate Product Name
        if(productName == null || productName.isEmpty())
        {
            this.productName = "Unknown";
        }
        else
        {
            this.productName = productName;
        }
        // Validate price
        if(price < 0)
        {
            this.price = 0;
        }
        else
        {
           this.price = price;
        }
        this.inStock = false;

        // Validate tags
        if(tags == null)
        {
            this.tags  = new ArrayList <String> ();
        }
        else
        {
            this.tags  = new ArrayList <String> ();
            for(String tag : tags)
            {
                this.tags.add(tag);
            }
        }
    }

    public Product(String productName, long price, boolean inStock, List <String> tags) 
    {
        // Validate Product Name
        if(productName == null || productName.isEmpty())
        {
            this.productName = "Unknown";
        }
        else
        {
            this.productName = productName;
        }

        // Validate price
        if(price < 0)
        {
            this.price = 0;
        }
        else
        {
           this.price = price;
        }

        this.inStock = inStock;
        this.tags  = new ArrayList <String> ();
        // Validate tags
        if(tags == null)
        {
            this.tags  = new ArrayList <String> ();
        }
        else
        {
            this.tags  = new ArrayList <String> ();
            for(String tag : tags)
            {
                this.tags.add(tag);
            }
        }
    }

    public Product(Product product)
    {
        this.productName = product.productName;
        this.price = product.price;
        this.inStock = product.inStock;
        this.tags = new ArrayList <String> ();
        for (String tag : product.tags)
        {
            this.tags.add(new String(tag));
        }
    }

    public List<String> getTags()
    {
        return this.tags;
    }

    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }

    public void addTag(String tag)
    {
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", tags=" + tags +
                '}';
    }

    public static void main(String[] args) {
        List<String> tags = null;
        
        Product product1 = new Product(null, -250, true, tags);
        // product1.addTag(null);
        
        System.out.println(product1);

        // List<String> tags = new ArrayList<>();
        // tags.add("Gaming");
        // tags.add("High Performance");

        // Product originalProduct = new Product("Gaming Laptop", 1500, true, tags);

        // // Creating a copy of originalProduct using the new constructor
        // Product copiedProduct = new Product(originalProduct);

        // // Modifying the original tags list
        // tags.add("Modified Tag");

        // // Printing the results to verify deep copy
        // System.out.println("=== Verifying Deep Copy: Modifying Original Tags List ===");
        // System.out.println("Original tags list: " + tags);
        // System.out.println("Original product tags (should not be affected): " + originalProduct.getTags());
        // System.out.println("Copied product tags (should not be affected): " + copiedProduct.getTags());
    }
}