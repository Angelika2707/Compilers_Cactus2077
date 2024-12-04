# Compilers_Cactus2077

## Technology stack
Project: I \
Target platform: JVM \
Implementation language/tool: Java, bison-based parser \
Target language: Jasmin assembler 

## Usage
- After cloning the repository and installing Maven dependencies, write your source code in the `input.txt` file. This code will be processed by the lexer and parser to generate the necessary output files.
- Compile the project by running the `Main.java` file. This will generate a `program.j` file and `.j` file for every record declared.
- After generating files, use **Jasmin** to convert it into Java bytecode. Make sure you have Jasmin installed, then run the following command(s):
```bash
java -jar "path-to-jasmin.jar" program.j
```
and
```bash
java -jar "path-to-jasmin.jar" record-name.j
```
- Once the bytecode is compiled, you can execute the program by running the following command
```bash
java Program
```
