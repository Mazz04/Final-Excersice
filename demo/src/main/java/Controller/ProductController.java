package Controller;

import DTO.ProductDTO;
import Model.Product;
import Repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        Product savedProduct = productRepository.save(product);
        ProductDTO savedProductDTO = new ProductDTO();
        BeanUtils.copyProperties(savedProduct, savedProductDTO);
        return savedProductDTO;
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        BeanUtils.copyProperties(productDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        ProductDTO updatedProductDTO = new ProductDTO();
        BeanUtils.copyProperties(updatedProduct, updatedProductDTO);
        return updatedProductDTO;
    }

    @GetMapping("/{name}")
    public List<ProductDTO> getProductsByName(@PathVariable String name) {
        List<Product> products = productRepository.findByName(name);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }
}