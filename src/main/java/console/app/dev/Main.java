package console.app.dev;

import console.app.dev.service.*;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main {

    public static void main(String[] args) throws NativeHookException {
        KeyListenerService keyListener = new KeyListenerService();
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(keyListener);
        WriteToFileService writeToFile = new WriteToFileService();
        AtomicInteger atomicInteger = new AtomicInteger(1);
        OutputFormatService formatOutput = new OutputFormatService(40);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input1 = scanner.nextLine();
            String[] inputs = input1.split(" ");
            if (inputs[0].equals("console-app")) {
                try {
                    formatOutput.printHeader();
                    String inputFile = inputs[1];
                    String outputFile = inputs[2];
                    writeToFile.setOutputFile(outputFile);
                    writeToFile.createHeader();
                    InputFileParser inputFileParser = new InputFileParser();
                    List<File> inputFiles = inputFileParser.parseInput(inputFile);
                    FileTreeTraverseService m = new FileTreeTraverseService(
                            inputFiles, writeToFile,
                            atomicInteger, formatOutput);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Unrecognized command");
            }
        }
    }
}
