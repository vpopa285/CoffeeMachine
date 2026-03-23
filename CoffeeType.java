package machine;

public enum CoffeeType {
    ESPRESSO(new Coffee(250, 0, 16, 4)),
    LATTE(new Coffee(350, 75, 20, 7)),
    CAPPUCCINO(new Coffee(200, 100, 12, 6));

    private final Coffee recipe;

    CoffeeType(Coffee recipe) {
        this.recipe = recipe;
    }

    public Coffee getRecipe() {
        return recipe;
    }
}
