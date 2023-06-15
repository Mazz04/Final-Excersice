package Model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String category;
    private double price;
    private String size;
    private int stock;

    public Product(String id, String name, String category, int price, String size, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.size = size;
        this.stock = stock;
    }

    public Product() {
    }
}