package sample.cafekiosk.spring.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.cafekiosk.spring.domain.stock.Stock;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByProductNumberIn(List<String> productNumbers);
}
