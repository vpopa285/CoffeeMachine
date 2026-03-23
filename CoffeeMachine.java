package machine;
import java.util.Scanner;

public class CoffeeMachine {
    private int machineWater;
    private int machineMilk;
    private int machineCoffee;
    private int machineCups;
    private int machineAmount;
    private int coffeeCount;
    private static final int CLEAN_LIMIT = 10;
    public CoffeeMachine(){
        machineWater = 400;
        machineMilk = 540;
        machineCoffee = 120;
        machineCups = 9;
        machineAmount = 550;
        coffeeCount = 0;
    }
    @Override
    public String toString(){
        return "\nThe coffee machine has:\n" +
                machineWater +" ml of water\n" +
                machineMilk + " ml of milk\n" +
                machineCoffee + " g of coffee beans\n" +
                machineCups + " disposable cups\n" +
                "$" + machineAmount +" of money";
    }
    public boolean hasResources(Coffee coffe) {
        String sorry = "Sorry, not enough ";
        if(machineWater < coffe.getWater()) {
            System.out.println(sorry + "water!");
            return false;
        }else if(machineMilk < coffe.getMilk()) {
            System.out.println(sorry + "milk!");
            return false;
        }else if(machineCoffee < coffe.getCoffee()) {
            System.out.println(sorry + "coffee!");
            return false;
        }else if(machineCups < 1) {
            System.out.println(sorry + "cups!");
            return false;
        }
        return true;
    }
    public void buy() {
        if (coffeeCount == CLEAN_LIMIT){
            System.out.println("I need cleaning!");
        }else{
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
            Scanner sc = new Scanner(System.in);
            String x = sc.nextLine();
            Coffee coffe = null;
            switch (x) {
                case "1":
                    coffe = hasResources(new Coffee(250, 0, 16, 4)) ? new Coffee(250, 0, 16, 4) : null;
                    break;
                case "2":
                    coffe = hasResources(new Coffee(350, 75, 20, 7)) ? new Coffee(350, 75, 20, 7) : null;
                    break;
                case "3":
                    coffe = hasResources(new Coffee(200, 100, 12, 6)) ? new Coffee(200, 100, 12, 6) : null;
                    break;
                case "back":
                    break;
                default:
                    System.out.println("Wrong product!");
                    break;
            }
            if(coffe != null){
                machineWater -= coffe.getWater();
                machineMilk -= coffe.getMilk();
                machineCoffee -= coffe.getCoffee();
                machineAmount += coffe.getPrice();
                machineCups--;
                coffeeCount++;
                System.out.println("I have enough resources, making you a coffee!");
            }
        }
    }
    public void fill(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add: ");
        int fillWater = sc.nextInt();
        System.out.println("Write how many ml of milk you want to add: ");
        int fillMilk = sc.nextInt();
        System.out.println("Write how many grams of coffee you want to add: ");
        int fillCoffee = sc.nextInt();
        System.out.println("Write how many disposable cups you want to add: ");
        int fillMaxCups = sc.nextInt();
        if(fillWater >= 0 && fillMilk >= 0 && fillCoffee >= 0 && fillMaxCups >= 0){
            machineWater+=fillWater;
            machineMilk+=fillMilk;
            machineCoffee+=fillCoffee;
            machineCups+=fillMaxCups;
        }else{
            System.out.println("Wrong data! Cannot insert a negative quantity in the machine.");
        }
    }
    public void take(){
        if(machineAmount > 0){
            System.out.println("I gave you $" + machineAmount);
            machineAmount = 0;
        }else{
            System.out.println("I dont have money to give you!");
        }
    }
    public void clean(){
        coffeeCount = 0;
        System.out.println("I have been cleaned!");
    }
    public void init(){
        Scanner sc = new Scanner(System.in);
        boolean isFinished = false;
        while(!isFinished) {
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
                    System.out.println(toString());
                    break;
                case "exit":
                    isFinished = true;
                    break;
                default:
                    System.out.println("Incorrect action!");
                    break;
            }
        }

    }

    public static void main(String[] args) {
        CoffeeMachine machine1 = new CoffeeMachine();
        machine1.init();
    }
}
