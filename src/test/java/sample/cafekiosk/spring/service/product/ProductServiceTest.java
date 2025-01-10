package sample.cafekiosk.spring.service.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sample.cafekiosk.spring.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.repository.product.ProductRepository;
import sample.cafekiosk.spring.service.product.response.ProductResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("신규 상품을 등록한다. 가장 최근 상품의 상품 번호에서 1증가 한 값이다.")
    @Test
    void createProduct() {
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.saveAll(List.of(product1));

        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingType(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();
        //when
        ProductResponse productResponse = productService.createProduct(request);

        //then
        assertThat(productResponse).extracting("productNumber", "type", "sellingType", "name", "price")
                .contains("002", HANDMADE, SELLING, "카푸치노", 5000);

    }

    private Product createProduct(String ProductNumber, ProductType type, ProductSellingType sellingType, String name, int price) {
        return Product.builder()
                .productNumber(ProductNumber)
                .type(type)
                .sellingType(sellingType)
                .name(name)
                .price(price)
                .build();
    }
}