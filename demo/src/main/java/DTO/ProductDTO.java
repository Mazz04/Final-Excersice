package DTO;
import lombok.Data;

@Data
public class ProductDTO {
    private String id;
    private String name;
    private String category;
    private double price;
    private String size;
    private int stock;
}
