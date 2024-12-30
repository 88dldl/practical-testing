package sample.cafekiosk.unit.beverage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AmericanoTest {
    @Test
    void getName() {
        Americano americano = new Americano();

        assertEquals(americano.getName(), "Americano"); //junit
        assertThat(americano.getName()).isEqualTo("Americano"); //assertj
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4000);
    }
}