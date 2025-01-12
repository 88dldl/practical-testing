package sample.cafekiosk.spring.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.repository.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.spring.repository.order.OrderRepository;
import sample.cafekiosk.spring.repository.orderProduct.OrderProductRepository;
import sample.cafekiosk.spring.repository.product.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static sample.cafekiosk.spring.domain.order.OrderStatus.PAYMENT_COMPLETED;
import static sample.cafekiosk.spring.domain.product.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
class OrderStatisticsServiceTest {
    @Autowired
    private OrderStatisticsService orderStatisticsService;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;
    @MockBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제 완료 주문들을 조회하여 매출 통계 메일을 전송한다. ")
    @Test
    void sendOrderStatisticsMail() {
        //given
        LocalDateTime now = LocalDateTime.of(2025, 1, 13, 10, 0);

        Product product1 = createProduct("001", HANDMADE, 1000);
        Product product2 = createProduct("002", HANDMADE, 2000);
        Product product3 = createProduct("003", HANDMADE, 3000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = createPaymentCompletedOrder(products, LocalDateTime.of(2025, 1, 12, 23, 59, 59));
        Order order2 = createPaymentCompletedOrder(products, now);
        Order order3 = createPaymentCompletedOrder(products, LocalDateTime.of(2025, 1, 13, 23, 59, 59));
        Order order4 = createPaymentCompletedOrder(products, LocalDateTime.of(2025, 1, 14, 0, 0));

        when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        //when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2025, 1, 13), "test@test.com");

        //then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 12000원 입니다.");
    }

    private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime registeredDateTime) {
        Order order = Order.builder()
                .status(PAYMENT_COMPLETED)
                .products(products)
                .registeredDateTime(registeredDateTime)
                .build();
        return orderRepository.save(order);
    }

    private Product createProduct(String ProductNumber, ProductType type, int price) {
        return Product.builder()
                .productNumber(ProductNumber)
                .type(type)
                .sellingType(SELLING)
                .name("아메리카노")
                .price(price)
                .build();
    }

}