public class Main {
    public static void main(String[] args) {
        final Shop shop = new Shop();
        final int CONSUMER_COUNT = 10;

        for (int i = 1; i <= CONSUMER_COUNT; i++) {
            new Thread(null, shop::buyCar, "Покупатель " + i).start();
        }

        new Thread(shop::buildCar, "Toyota").start();

    }
}
