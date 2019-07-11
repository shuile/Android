package test;

import design_patterns_18.Duck;
import design_patterns_18.FlyRocketPowered;
import design_patterns_18.MallardDuck;
import design_patterns_18.ModelDuck;

public class Dp18Test {

    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.performQuack();
        mallard.performFly();

        Duck model = new ModelDuck();
        model.performFly();
        model.setFlyBehavior(new FlyRocketPowered());
        model.performFly();
    }
}
