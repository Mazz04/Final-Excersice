package Controller;
import Controller.ProductController;
import Model.Product;
import Repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    private List<Product> productList;

    @BeforeEach
    public void setup() {
        productList = new ArrayList<Product>();
        productList.add(new Product("1", "Coca-Cola", "Soda", 2500, "200ml", 22));
        productList.add(new Product("2", "Papa Margarita", "Chips", 3000, "100g", 42));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        given(productRepository.findAll()).willReturn(productList);

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(productList.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Coca-Cola"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Papa Margarita"))
                .andDo(print());
    }

    @Test
    public void testGetProductByName() throws Exception {
        given(productRepository.findByName("Coca-Cola")).willReturn(productList.subList(0, 1));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/{name}", "Coca-Cola")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Coca-Cola"))
                .andDo(print());
    }
}
