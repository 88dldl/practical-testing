package sample.cafekiosk.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.controller.order.OrderController;
import sample.cafekiosk.spring.controller.product.ProductController;
import sample.cafekiosk.spring.service.order.OrderService;
import sample.cafekiosk.spring.service.product.ProductService;

@WebMvcTest(controllers = {
        ProductController.class,
        OrderController.class
})
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected ProductService productService;
    @MockBean
    protected OrderService orderService;
}