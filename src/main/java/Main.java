public class Main {
    public static void main(String[] args) {
        final Shop shop = new Shop();

        new Thread(shop::buyCar, "Покупатель 1").start();
        new Thread(shop::buyCar, "Покупатель 2").start();
        new Thread(shop::buyCar, "Покупатель 3").start();
        new Thread(shop::buyCar, "Покупатель 4").start();
        new Thread(shop::buyCar, "Покупатель 5").start();
        new Thread(shop::buyCar, "Покупатель 6").start();
        new Thread(shop::buyCar, "Покупатель 7").start();
        new Thread(shop::buyCar, "Покупатель 8").start();
        new Thread(shop::buyCar, "Покупатель 9").start();
        new Thread(shop::buyCar, "Покупатель 10").start();
        new Thread(shop::buildCar, "Toyota").start();

    }
}
