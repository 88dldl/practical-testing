package sample.cafekiosk.spring.service.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.repository.product.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.BAKERY;

class ProductNumberFactoryTest extends IntegrationTestSupport {
    @Autowired
    private ProductNumberFactory productNumberFactory;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("처음 등록되는 상품의 번호를 생성한다.")
    @Test
    void createNextProductNumberWhenFirstProduct() {
        //when
        String result = productNumberFactory.createNextProductNumber();

        //then
        assertThat(result).isEqualTo("001");
    }

    @DisplayName("신규 등록되는 상품의 번호를 생성한다.")
    @Test
    void createNextProductNumberWhenSubsequentProduct() {
        //given
        Product product1 = createProduct("001");
        productRepository.save(product1);

        //when
        String result = productNumberFactory.createNextProductNumber();

        //then
        assertThat(result).isEqualTo("002");
    }

    private Product createProduct(String ProductNumber) {
        return Product.builder()
                .productNumber(ProductNumber)
                .type(BAKERY)
                .sellingType(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();
    }
}