package sample.cafekiosk.spring.service.order.response;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.service.product.response.ProductResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {
    private Long id;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;

    @Builder
    private OrderResponse(Long id, LocalDateTime registeredDateTime, List<ProductResponse> products) {
        this.id = id;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }
}
