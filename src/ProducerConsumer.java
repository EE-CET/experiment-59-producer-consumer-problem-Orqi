class SharedResource {
    int item;
    boolean available = false;

    synchronized void put(int item) {
        try {
            while (available) {
                wait();
            }
            this.item = item;
            available = true;
            System.out.println("Produced: " + item);
            notify();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    synchronized void get() {
        try {
            while (!available) {
                wait();
            }
            System.out.println("Consumed: " + item);
            available = false;
            notify();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

class Producer extends Thread {
    SharedResource resource;

    Producer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.put(i);
        }
    }
}

class Consumer extends Thread {
    SharedResource resource;

    Consumer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.get();
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Producer p = new Producer(resource);
        Consumer c = new Consumer(resource);

        p.start();
        c.start();
    }
}