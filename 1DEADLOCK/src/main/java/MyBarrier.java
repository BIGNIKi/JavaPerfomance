/**
 * используется для синхронизации старта countOfThreads потоков.
 */
public class MyBarrier
{
    private int _countToStart;

    public MyBarrier(int countOfThreads)
    {
        _countToStart = countOfThreads;
    }

    public void Realize()
    {
        _countToStart--;
        synchronized(this)
        {
            this.notifyAll();
        }
    }

    public boolean CanStart()
    {
        return _countToStart == 0;
    }
}
