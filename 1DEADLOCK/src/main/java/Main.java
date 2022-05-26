public class Main
{
    public static void main(String[] args)
    {
        // когда барьер будет иметь значение 0, все потоки начнут исполнение
        final MyBarrier mB = new MyBarrier(2);

        Thread[] threads = new Thread[2];

        Potok potok0 = new Potok(mB, 0);
        Potok potok1 = new Potok(mB, 1);
        threads[0] = new Thread(potok0);
        threads[1] = new Thread(potok1);
        threads[0].start();
        threads[1].start();

        // отдельный поток наблюдателя, чтобы писать в консоль состояние потоков 0 и 1
        Observer observ = new Observer(threads[0], threads[1]);
        Thread tr = new Thread(observ);
        tr.start();

        try
        {
            threads[0].join();
            threads[1].join();
            observ.SetCanStop();
        } catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Main thread finished.");
    }
}
