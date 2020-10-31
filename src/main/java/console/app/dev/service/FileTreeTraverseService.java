package console.app.dev.service;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class FileTreeTraverseService extends Thread {
    private final List<File> files;
    private int count;
    private final WriteToFileService writeToFile;
    private static final BlockingQueue<String> queue = new LinkedBlockingDeque<>();
    private final AtomicInteger atomicInteger;
    private final OutputFormatService outputFormatService;

    public FileTreeTraverseService(List<File> files, WriteToFileService writeToFile,
                       AtomicInteger atomicInteger, OutputFormatService outputFormatService) {
        this.outputFormatService = outputFormatService;
        this.atomicInteger = atomicInteger;
        this.writeToFile = writeToFile;
        this.files = files;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            for (File file : files) {
                int recordsId = atomicInteger.getAndIncrement();
                if (file.isDirectory()) {
                    Thread.sleep(2000);
                    List<File> newList = List.of(Objects.requireNonNull(file.listFiles()));
                    count = newList.size();
                    writeToFile.writeTo( file.getAbsolutePath() + "," + count + ";");
                    outputFormatService.printFormat(recordsId, count, file.getAbsoluteFile().toString());
                    new FileTreeTraverseService(newList, writeToFile,
                            atomicInteger, outputFormatService);
                }
                if (file.isFile()) {
                    outputFormatService.printFormat(recordsId, count, file.getAbsoluteFile().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
