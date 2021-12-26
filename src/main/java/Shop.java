import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Shop {
    List<String> cars = new ArrayList<>();
    Lock consumer = new ReentrantLock(true);
    Condition carCondition = consumer.newCondition();
    public static final int TIME_TO_BUILD = 3000;
    public static final int MAX_COUNT = 10;


    public void buyCar(){
        consumer.lock();
        try {
            String consumerName = Thread.currentThread().getName();
            System.out.printf("%s зашел в автосалон\n", consumerName);
            while (cars.isEmpty()) {
                System.out.println("Машин в продаже нет");
                try {
                    carCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s уехал на новеньком авто\n", consumerName);
                cars.remove(0);
            }
        } finally {
            consumer.unlock();
        }
    }

    public void buildCar() {
        consumer.lock();
        try {
            while (true) {
                String vendorName = Thread.currentThread().getName();
                System.out.printf("Производитель %s начал производство машины\n", vendorName);
                if (cars.size() < MAX_COUNT) {
                    Thread.sleep(TIME_TO_BUILD);
                    cars.add(vendorName);
                    System.out.printf("Производитель %s выпустил авто\n", vendorName);
                    carCondition.signal();
                } else {
                    System.out.println("Производство машин закончено");
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consumer.unlock();
        }
    }

}