echo "INICIANDO COMPILACION JFLEX"
java -jar jflex-full-1.8.2.jar Lexer.jflex
javac jflex-1.8.2/lib/CounterLexer.java
java CounterLexer 