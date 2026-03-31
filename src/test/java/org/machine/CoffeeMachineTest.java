package org.machine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;


class CoffeeMachineTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    private ByteArrayOutputStream captured;

    private CoffeeMachine run(String... lines) {
        String input = String.join("\n", lines) + "\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        CoffeeMachine machine = new CoffeeMachine();
        machine.init();
        return machine;
    }

    @BeforeEach
    void setUp() {
        captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void buyEspressoTest() {
        CoffeeMachine machine = run("buy", "1");
        String out = captured.toString(StandardCharsets.UTF_8);

        assertTrue(out.contains("I have enough resources, making you a coffee!"), "Wrong output when buying espresso");

        assertAll("Resources after espresso",
                () -> assertEquals(150, machine.getMachineWater()),
                () -> assertEquals(540, machine.getMachineMilk()),
                () -> assertEquals(104, machine.getMachineCoffee()),
                () -> assertEquals(8, machine.getMachineCups()),
                () -> assertEquals(554, machine.getMachineAmount())
                );
    }

    @Test
    void buyLatteTest() {
        CoffeeMachine machine = run("buy", "2");
        String out = captured.toString(StandardCharsets.UTF_8);

        assertTrue(out.contains("I have enough resources, making you a coffee!"), "Wrong output when buying latte");

        assertAll("Resources after latte",
                () -> assertEquals(50, machine.getMachineWater()),
                () -> assertEquals(465, machine.getMachineMilk()),
                () -> assertEquals(100, machine.getMachineCoffee()),
                () -> assertEquals(8, machine.getMachineCups()),
                () -> assertEquals(557, machine.getMachineAmount())
        );
    }

    @Test
    void buyCappuccinoTest() {
        CoffeeMachine machine = run("buy", "3");
        String out = captured.toString(StandardCharsets.UTF_8);

        assertTrue(out.contains("I have enough resources, making you a coffee!"), "Wrong output when buying cappuccino");

        assertAll("Resources after cappuccino",
                () -> assertEquals(200, machine.getMachineWater()),
                () -> assertEquals(440, machine.getMachineMilk()),
                () -> assertEquals(108, machine.getMachineCoffee()),
                () -> assertEquals(8, machine.getMachineCups()),
                () -> assertEquals(556, machine.getMachineAmount())
        );
    }

    @Test
    void buyBackTest() {
        CoffeeMachine machine = run("buy", "back");
        String out = captured.toString(StandardCharsets.UTF_8);

        assertFalse(out.contains("I have enough resources, making you a coffee!"));
    }

    @Test
    void buyInvalidChoiceTest() {
        CoffeeMachine machine = run("buy", "99");
        String out = captured.toString(StandardCharsets.UTF_8);

        assertTrue(out.contains("Wrong product!"));
    }

    @Test
    void notWaterTest() {
        CoffeeMachine machine = run("buy", "2", "buy", "2");
        String out = captured.toString(StandardCharsets.UTF_8);

        assertTrue(out.contains("Sorry, not enough water!"));
    }

    @Test
    void notMilkTest() {
        CoffeeMachine machine = run("fill", "5000", "0", "5000", "100",
                "buy", "3", "buy", "3", "buy", "3", "buy", "3", "buy", "3", "buy", "3"
        );

        String out = captured.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("Sorry, not enough milk!"));
    }

    @Test
    void notCoffeeBeansTest() {
        CoffeeMachine machine = run("fill", "5000", "5000", "0", "100",
                "buy", "1", "buy", "1", "buy", "1", "buy", "1",
                "buy", "1", "buy", "1", "buy", "1", "buy", "1"
        );

        String out = captured.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("Sorry, not enough coffee!"));
    }

    @Test
    void notCupsTest() {
        CoffeeMachine machine = run("fill", "5000", "5000", "5000", "0",
                "buy", "1", "buy", "1", "buy", "1", "buy", "1", "buy", "1",
                "buy", "1", "buy", "1", "buy", "1", "buy", "1", "buy", "1"
        );

        String out = captured.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("Sorry, not enough cups!"));
    }

    @Test
    void fillTest() {
        CoffeeMachine machine = run("fill", "100", "50", "20", "5");

        assertAll("Resources after fill",
                () -> assertEquals(500, machine.getMachineWater()),
                () -> assertEquals(590, machine.getMachineMilk()),
                () -> assertEquals(140, machine.getMachineCoffee()),
                () -> assertEquals(14,  machine.getMachineCups())
        );
    }

    @Test
    void fillZeroTest() {
        CoffeeMachine machine = run("fill", "0", "0", "0", "0");

        assertAll("Resources unchanged after zero fill",
                () -> assertEquals(400, machine.getMachineWater()),
                () -> assertEquals(540, machine.getMachineMilk()),
                () -> assertEquals(120, machine.getMachineCoffee()),
                () -> assertEquals(9,   machine.getMachineCups())
        );
    }

    @Test
    void fillNegativeWaterTest() {
        CoffeeMachine machine = run("fill", "-1", "0", "0", "0");
        String out = captured.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("Invalid water amount!"));
    }

    @Test
    void fillNegativeMilkest() {
        CoffeeMachine machine = run("fill", "0", "-1", "0", "0");
        String out = captured.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("Invalid milk amount!"));
    }

    @Test
    void fillNegativeCoffeeTest() {
        CoffeeMachine machine = run("fill", "0", "0", "-1", "0");
        String out = captured.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("Invalid coffee amount!"));
    }

    @Test
    void fillNegativeCupsTest() {
        CoffeeMachine machine = run("fill", "0", "0", "0", "-1");
        String out = captured.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("Invalid cups amount!"));
    }

    @Test
    void takeTest() {
        CoffeeMachine machine = run("take");
        String out = captured.toString(StandardCharsets.UTF_8);

        assertTrue(out.contains("$550"));
        assertEquals(0, machine.getMachineAmount());
    }

    @Test
    void takeEmptyTest() {
        CoffeeMachine machine = run("take", "take");
        String out = captured.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("I dont have money"));
    }

    @Test
    void cleanLimitTest() {
        CoffeeMachine machine = run(
                "fill", "5000", "5000", "5000", "50",
                "buy", "1", "buy", "1", "buy", "1", "buy", "1", "buy", "1",
                "buy", "1", "buy", "1", "buy", "1", "buy", "1", "buy", "1",
                "buy", "1"
        );

        String out = captured.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("I need cleaning!"));
    }

    @Test
    void cleanResetTest() {
        CoffeeMachine machine = run(
                "fill", "5000", "5000", "5000", "50",
                "buy", "1", "buy", "1", "buy", "1", "buy", "1", "buy", "1",
                "buy", "1", "buy", "1", "buy", "1", "buy", "1", "buy", "1",
                "clean",
                "buy", "1"
        );

        String out = captured.toString(StandardCharsets.UTF_8);
        int lastBuy = out.lastIndexOf("making you a coffee!");

        assertTrue(lastBuy > 0);
        assertFalse(out.substring(lastBuy).contains("I need cleaning"));
    }

    @Test
    void initialStateTest() {
        CoffeeMachine machine = run("remaining");
        String out = captured.toString(StandardCharsets.UTF_8);

        assertAll("Initial state display",
                () -> assertTrue(out.contains("400 ml of water")),
                () -> assertTrue(out.contains("540 ml of milk")),
                () -> assertTrue(out.contains("120 g of coffee beans")),
                () -> assertTrue(out.contains("9 disposable cups")),
                () -> assertTrue(out.contains("$550 of money"))
        );
    }

    @Test
    void unknownActionTest() {
        CoffeeMachine machine = run("h");
        String out = captured.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("Incorrect action!"));
    }

}
