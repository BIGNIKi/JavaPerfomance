import java.lang.instrument.Instrumentation;

public class Agent007
{
    public static void premain(String agentArgument, Instrumentation instrumentation) {
        // описываем событие, которое должно произойти после окончания работы JVM
        Thread printingHook = new Thread(() -> System.out.println("Total loaded classes: " + instrumentation.getAllLoadedClasses().length));
        // printing Hook исполнится после прекращения работы программы и выведет общее число загруженных классов
        Runtime.getRuntime().addShutdownHook(printingHook);

        instrumentation.addTransformer(new ClassTransformer()); // добавляем преобразователь класса
    }
}
