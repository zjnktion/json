package cn.zjnktion.json;

/**
 * @author zjnktion
 */
public class ThreadException {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                synchronized (this) {
                    System.out.println(t.getName() + "::::" + e.getMessage());
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        Thread t1 = new Thread(new ThreadH());
        Thread t2 = new Thread(new ThreadH());

        t1.start();
        t2.start();

        System.out.println(Integer.MAX_VALUE);
    }

    static class ThreadH implements Runnable {

        @Override
        public void run() {
            throw new RuntimeException("adf");
        }
    }
}
