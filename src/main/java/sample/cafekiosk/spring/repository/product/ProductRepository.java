package sample.cafekiosk.spring.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingType;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllBySellingTypeIn(List<ProductSellingType> sellingTypes);
}
