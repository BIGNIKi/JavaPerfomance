public class Observer implements Runnable
{
    private final Thread Th0;
    private final Thread Th1;

    private boolean _canStop = false;

    public Observer(Thread th0, Thread th1)
    {
        Th0 = th0;
        Th1 = th1;
    }

    public void SetCanStop()
    {
        _canStop = true;
    }

    @Override
    public void run()
    {
        while(!_canStop)
        {
            System.out.println("Thread one state is " + Th0.getState());
            System.out.println("Thread two state is " + Th1.getState());
            try
            {
                Thread.sleep(3000);
            } catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
