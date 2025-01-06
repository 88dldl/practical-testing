package sample.cafekiosk.spring.repository.orderProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.cafekiosk.spring.domain.orderProduct.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
