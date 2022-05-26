import javassist.*;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class ClassTransformer implements ClassFileTransformer
{
    // запускается для каждого загруженного класса
    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        ClassPool classPool = ClassPool.getDefault();
        CtClass currentClass;
        try {
            currentClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer)); // создали наш класс (копия)
            recordAllAllocations(currentClass); // записывает все события аллокации с именами объектов

            // замер времени на выполнение каждого метода во всех классах, определенных в пакете nsu.fit.javaperf
            if (currentClass.getPackageName().startsWith("nsu.fit.javaperf")) { // если мы в нужном нам классе
                CtMethod[] methods = currentClass.getDeclaredMethods(); // получили все методы
                Arrays.stream(methods).forEach(method -> {
                    if ((method.getMethodInfo().getAccessFlags() & AccessFlag.STATIC) == 0) { // если не статический метод (чтобы не попасть в main)
                        try {
                            method.addLocalVariable("startTime", CtClass.longType);
                            method.insertBefore("startTime = System.currentTimeMillis();\n");
                            method.insertAfter("long finish = System.currentTimeMillis();\nlong timeElapsed = finish - startTime;\nSystem.out.println(\"INSERTED: Elapsed Time: \" + timeElapsed + \"ms\");");
                        } catch (CannotCompileException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
            return currentClass.toBytecode();
        } catch (IOException | CannotCompileException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void recordAllAllocations(CtClass currentClass) {
        CtMethod[] methods = currentClass.getDeclaredMethods(); // получили все методы, которые есть в классе
        Arrays.stream(methods).forEach(ClassTransformer::func); // для каждого чекаем, сколько аллокаций зовется
    }

    private static void func(CtMethod method) {
        try {
            CodeAttribute attribute = method.getMethodInfo().getCodeAttribute();
            if (attribute == null) { // будет null, если нет доступа к методу
                return;
            } else { // когда есть доступ к методу
                CodeIterator iterator = attribute.iterator(); // итератор по командам jvm
                while (iterator.hasNext()) { // получили след команду jvm
                    int index = iterator.next();
                    int opcode = iterator.byteAt(index); // получили номер команды
                    if (opcode == 187 || opcode == 188) { // 187 - new() 188 - new array()
                        System.out.println(method.getLongName() + ": has been allocated.");
                    }
                }
            }
        } catch (BadBytecode e) {
            e.printStackTrace();
        }
    }

}
