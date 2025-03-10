package sample.cafekiosk.spring.controller.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.ApiResponse;
import sample.cafekiosk.spring.controller.order.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.service.order.OrderService;
import sample.cafekiosk.spring.service.order.response.OrderResponse;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return ApiResponse.oK(orderService.createOrder(request.toServiceRequest(), registeredDateTime));
    }
}
