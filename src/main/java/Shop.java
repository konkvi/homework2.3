import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Shop {
    List<String> cars = new ArrayList<>();
    Lock vendor = new ReentrantLock(true);
    Condition carCondition = vendor.newCondition();
    public static final int TIME_TO_WAIT = 1000;
    public static final int MAXCOUNT = 10;


    public void buyCar(){
        vendor.lock();
        try {
            String consumerName = Thread.currentThread().getName();
            System.out.printf("%s зашел в автосалон\n", consumerName);
            while (cars.isEmpty()) {
                System.out.println("Машин в продаже нет");
                try {
                    carCondition.await();
                    Thread.sleep(TIME_TO_WAIT); // усыпляем поток для ожидания поступления машины
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf("%s уехал на новеньком авто\n", consumerName);
        } finally {
            vendor.unlock();
        }
    }

    public void buildCar() {
        vendor.lock();
        try {
            while (true) {
                String vendorName = Thread.currentThread().getName();
                System.out.printf("Производитель %s начал производство машины\n", vendorName);
                if (cars.size() < MAXCOUNT) {
                    cars.add(vendorName);
                    System.out.printf("Производитель %s выпустил авто\n", vendorName);
                    carCondition.signal();
                    continue;
                } else {
                    System.out.println("Производство машин закончено");
                    break;
                }
            }
        } finally {
            vendor.unlock();
        }
    }
}