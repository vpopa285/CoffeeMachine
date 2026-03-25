package org.example;
import java.util.Scanner;

public class CoffeeMachine {
    private int machineWater;
    private int machineMilk;
    private int machineCoffee;
    private int machineCups;
    private int machineAmount;
    private int coffeeCount;
    private static final int CLEAN_LIMIT = 10;

    public CoffeeMachine() {
        machineWater = 400;
        machineMilk = 540;
        machineCoffee = 120;
        machineCups = 9;
        machineAmount = 550;
        coffeeCount = 0;
    }

    public static void main(String[] args) {
        new CoffeeMachine().init();
    }

    private boolean hasResources(Coffee coffee) {
        final String sorry = "Sorry, not enough ";

        if (machineWater < coffee.water()) {
            System.out.println(sorry + "water!");
            return false;
        }
        if (machineMilk < coffee.milk()) {
            System.out.println(sorry + "milk!");
            return false;
        }
        if (machineCoffee < coffee.coffee()) {
            System.out.println(sorry + "coffee!");
            return false;
        }
        if (machineCups < 1) {
            System.out.println(sorry + "cups!");
            return false;
        }
        return true;
    }

    private void buy() {
        if (coffeeCount == CLEAN_LIMIT) {
            System.out.println("I need cleaning!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        String choice = sc.nextLine();

        Coffee coffee = null;

        switch (choice) {
            case "1":
                coffee = CoffeeType.ESPRESSO.getRecipe();
                break;
            case "2":
                coffee = CoffeeType.LATTE.getRecipe();
                break;
            case "3":
                coffee = CoffeeType.CAPPUCCINO.getRecipe();
                break;
            case "back":
                return;
            default:
                System.out.println("Wrong product!");
                return;
        }

        if (!hasResources(coffee)) {
            return;
        }

        machineWater -= coffee.water();
        machineMilk -= coffee.milk();
        machineCoffee -= coffee.coffee();
        machineAmount += coffee.price();
        machineCups--;
        coffeeCount++;
        System.out.println("I have enough resources, making you a coffee!");
    }

    private void fill() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Write how many ml of water you want to add: ");
        int water = sc.nextInt();
        if (water < 0) {
            System.out.println("Invalid water amount!");
            return;
        }

        System.out.println("Write how many ml of milk you want to add: ");
        int milk = sc.nextInt();
        if (milk < 0) {
            System.out.println("Invalid milk amount!");
            return;
        }

        System.out.println("Write how many grams of coffee you want to add: ");
        int coffee = sc.nextInt();
        if (coffee < 0) {
            System.out.println("Invalid coffee amount!");
            return;
        }

        System.out.println("Write how many disposable cups you want to add: ");
        int cups = sc.nextInt();
        if (cups < 0) {
            System.out.println("Invalid cups amount!");
            return;
        }

        machineWater += water;
        machineMilk += milk;
        machineCoffee += coffee;
        machineCups += cups;
    }

    private void take() {
        if (machineAmount > 0) {
            System.out.println("I gave you $" + machineAmount);
            machineAmount = 0;
            return;
        }
        System.out.println("I dont have money to give you!");
    }

    private void clean() {
        coffeeCount = 0;
        System.out.println("I have been cleaned!");
    }

    public void init() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nWrite action (buy, fill, take, clean, remaining, exit): ");
            String action = sc.nextLine();

            switch (action) {
                case "buy":
                    buy();
                    break;
                case "fill":
                    fill();
                    break;
                case "take":
                    take();
                    break;
                case "clean":
                    clean();
                    break;
                case "remaining":
                    System.out.println(this);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Incorrect action!");
            }
        }
    }

    @Override
    public String toString() {
        return "\nThe coffee machine has:\n" +
                machineWater + " ml of water\n" +
                machineMilk + " ml of milk\n" +
                machineCoffee + " g of coffee beans\n" +
                machineCups + " disposable cups\n" +
                "$" + machineAmount + " of money";
    }
}