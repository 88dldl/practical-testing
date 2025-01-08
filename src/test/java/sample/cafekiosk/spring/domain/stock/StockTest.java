package sample.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StockTest {
    @DisplayName("재고의 수량이 제공된 수량보다 적은지 확인한다.")
    @Test
    void isQuantityLessThan() {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //when
        boolean result = stock.isQuantityLessThan(quantity);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    void deductQuantity() {
        //given
        Stock stock = Stock.create("001", 2);
        int quantity = 1;

        //when
        stock.deductQuantity(quantity);

        //then
        assertThat(stock.getQuantity()).isEqualTo(1);
    }
}