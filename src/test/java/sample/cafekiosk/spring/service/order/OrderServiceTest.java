package sample.cafekiosk.spring.service.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.repository.product.ProductRepository;
import sample.cafekiosk.spring.service.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.service.order.response.OrderResponse;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderService orderService;

    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder() {
        //given
        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 2000);
        Product product3 = createProduct(HANDMADE, "003", 3000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        //when
        LocalDateTime registerDateTime = LocalDateTime.now();
        OrderResponse response = orderService.createOrder(request, registerDateTime);

        //then
        // id는 얼마인지 중요하지 않고 있으면 된다,
        assertThat(response.getId()).isNotNull();

        // 주문 검증
        assertThat(response)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registerDateTime, 3000);

        // 주문 안 상품들 검증
        assertThat(response.getProducts())
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("002", 2000)
                );

    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithDuplicateProductNumners() {
        //given
        Product product1 = createProduct(HANDMADE, "001", 1000);
        productRepository.saveAll(List.of(product1));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001"))
                .build();

        //when
        LocalDateTime registerDateTime = LocalDateTime.now();
        OrderResponse response = orderService.createOrder(request, registerDateTime);

        //then
        assertThat(response.getId()).isNotNull();

        // 주문 검증
        assertThat(response)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registerDateTime, 2000);

        // 주문 안 상품들 검증
        assertThat(response.getProducts())
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("001", 1000)
                );
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingType(SELLING)
                .name("아메리카노")
                .price(price)
                .build();
    }
}