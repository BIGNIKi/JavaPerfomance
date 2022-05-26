// gradlew build - запускаем через Intellij
javac mainClassHere\nsu\fit\javaperf\TransactionProcessor.java
cd mainClassHere
jar -cvfm TransactionProcessor.jar MANIFEST.MF nsu\fit\javaperf\*.class
cd ..
java -javaagent:build\libs\Agent007-1.0-SNAPSHOT.jar -jar mainClassHere\TransactionProcessor.jar
pause
