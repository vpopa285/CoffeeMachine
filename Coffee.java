package machine;

public class Coffee{
    private final int water;
    private final int milk;
    private final int coffee;
    private final int price;
    public Coffee(int water, int milk, int coffee, int price){
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.price = price;
    }
    public int getWater(){
        return water;
    }
    public int getMilk(){
        return milk;
    }
    public int getCoffee(){
        return coffee;
    }
    public int getPrice(){
        return price;
    }
}
