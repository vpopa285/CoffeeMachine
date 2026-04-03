package org.machine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeTypeTest {

    @ParameterizedTest
    @MethodSource("recipes")
    void coffeeRecipesExactValues(CoffeeType type, Coffee expected) {
        Coffee actual = type.getRecipe();

        assertAll(
                () -> assertEquals(expected.water(), actual.water()),
                () -> assertEquals(expected.milk(), actual.milk()),
                () -> assertEquals(expected.coffee(), actual.coffee()),
                () -> assertEquals(expected.price(), actual.price())
        );
    }

    public static Stream<Arguments> recipes() {
        return Stream.of(
                Arguments.of(CoffeeType.ESPRESSO, new Coffee(250, 0, 16, 4)),
                Arguments.of(CoffeeType.LATTE, new Coffee(350, 75, 20, 7)),
                Arguments.of(CoffeeType.CAPPUCCINO, new Coffee(200, 100, 12, 6))
        );
    }

    @ParameterizedTest
    @EnumSource(CoffeeType.class)
    void recipesValidityTest(CoffeeType type) {
        Coffee coffee = type.getRecipe();

        assertAll(
                () -> assertNotNull(coffee),
                () -> assertTrue(coffee.water() >= 0),
                () -> assertTrue(coffee.milk() >= 0),
                () -> assertTrue(coffee.coffee() >= 0),
                () -> assertTrue(coffee.price() > 0)
        );
    }

}
