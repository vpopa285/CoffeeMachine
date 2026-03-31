package org.machine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeTypeTest {

    @Test
    void espressoRecipeTest() {
        Coffee testCoffee = CoffeeType.ESPRESSO.getRecipe();
        assertAll("Testing Espresso Recipe",
                () -> assertEquals(250, testCoffee.water()),
                () -> assertEquals(0, testCoffee.milk()),
                () -> assertEquals(16, testCoffee.coffee()),
                () -> assertEquals(4, testCoffee.price()));
    }

    @Test
    void latteRecipeTest() {
        Coffee testCoffee = CoffeeType.LATTE.getRecipe();
        assertAll("Testing Latte Recipe",
                () -> assertEquals(350, testCoffee.water()),
                () -> assertEquals(75, testCoffee.milk()),
                () -> assertEquals(20, testCoffee.coffee()),
                () -> assertEquals(7, testCoffee.price()));
    }

    @Test
    void cappuccinoRecipeTest() {
        Coffee testCoffee = CoffeeType.CAPPUCCINO.getRecipe();
        assertAll("Testing Cappuccino Recipe",
                () -> assertEquals(200, testCoffee.water()),
                () -> assertEquals(100, testCoffee.milk()),
                () -> assertEquals(12, testCoffee.coffee()),
                () -> assertEquals(6, testCoffee.price()));
    }

    @Test
    void recipeValidityTest() {
        for (CoffeeType type : CoffeeType.values()) {
            assertNotNull(type.getRecipe());
        }
    }

}
