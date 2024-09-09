import main.lexer.Lexer;
import main.lexer.Token;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        File testFolder = new File("src/resources/test");

        File[] testFiles = testFolder.listFiles((dir, name) -> {
            Pattern pattern = Pattern.compile("^Test Case \\d+ .*\\.txt$");
            return pattern.matcher(name).matches();
        });

        if (testFiles == null || testFiles.length == 0) {
            System.out.println("No test cases found.");
            return;
        }

        Lexer lexer = new Lexer();

        for (File testFile : Objects.requireNonNull(testFiles)) {
            StringBuilder programCode = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new FileReader(testFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    programCode.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<Token> tokens = lexer.tokenize(programCode.toString());
            String outputFileName = testFile.getName().replace(".txt", "_tokens.txt");
            File outputFile = new File("src/resources/test/output/" + outputFileName);

            outputFile.getParentFile().mkdirs();

            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                for (Token token : tokens) {
                    writer.println(token);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
