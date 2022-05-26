@SuppressWarnings("SynchronizeOnNonFinalField")
public class Potok implements Runnable
{
    private final MyBarrier MB;

    private final int Num;

    private static final Object ob1 = new Object();
    private static final Object ob2 = new Object();


    public Potok(MyBarrier barrier, int num)
    {
        MB = barrier;
        Num = num;
    }

    @Override
    public void run()
    {
        MB.Realize();  // барьер -1
        synchronized(MB)
        {
            if(!MB.CanStart()) // если барьер не разрешает начинать
            {
                try
                {
                    MB.wait(); // ждем, когда разрешит
                } catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("I've started when it was " + System.currentTimeMillis());

        if(Num == 0) // первый поток займет сначала ob1, потом попробует занять ob2
        {
            synchronized(ob1)
            {
                System.out.println("I am " + Num);
                synchronized(ob2)
                {
                    System.out.println("I've tried so hard but I couldn't... Num = " + Num);
                }
            }
        }
        else if(Num == 1)  // второй поток займет сначала ob2, потом попробует занять ob1
        {
            synchronized(ob2)
            {
                System.out.println("I am " + Num);
                synchronized(ob1)
                {
                    System.out.println("I've tried so hard but I couldn't... Num = " + Num);
                }
            }
        }
    }
}
