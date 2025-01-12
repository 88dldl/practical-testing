package sample.cafekiosk.spring.repository.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.order.OrderStatus.CANCELED;
import static sample.cafekiosk.spring.domain.order.OrderStatus.PAYMENT_COMPLETED;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("주어진 날짜와 주문 상태에 따라 주문 목록을 조회한다.")
    @Test
    void findOrdersBy() {
        //given
        LocalDateTime registeredTime = LocalDateTime.of(2025, 1, 13, 23, 0);
        LocalDateTime startTime = registeredTime.toLocalDate().atStartOfDay();
        LocalDateTime endTime = startTime.plusDays(1);

        Order order1 = createOrder(registeredTime, PAYMENT_COMPLETED);
        Order order2 = createOrder(registeredTime.plusHours(1), CANCELED);

        orderRepository.saveAll(List.of(order1, order2));

        //when
        List<Order> orders = orderRepository.findOrdersBy(startTime, endTime, PAYMENT_COMPLETED);

        //then
        assertThat(orders).extracting("registeredDateTime", "status")
                .containsExactlyInAnyOrder(
                        tuple(registeredTime, PAYMENT_COMPLETED)
                );
    }

    private Order createOrder(LocalDateTime orderTime, OrderStatus canceled) {
        return Order.builder()
                .products(List.of())
                .registeredDateTime(orderTime)
                .status(canceled)
                .build();
    }

}