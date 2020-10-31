package console.app.dev.service;

public class OutputFormatService {
    private int recordMaxLength;

    public OutputFormatService(int recordMaxLength) {
        this.recordMaxLength = recordMaxLength;
    }

    public void printHeader() {
        String format = "|%1$-5s|%2$-5s|%3$-" + recordMaxLength + "s|\n";
        System.out.format(format, "ID", "Files", "Path");
    }

    public void printFormat(int recordId, int count, String record) {
        String format = "|%1$-5s|%2$-5s|%3$-" + recordMaxLength + "s|\n";
        int length = record.length();
        if (length > recordMaxLength) {
            int repeatCount = length / recordMaxLength + 1;
            int index = 0;
            while (repeatCount > 0) {
                if (index == 0) {
                    System.out.format(format, recordId, count, record.substring(index, index + recordMaxLength));
                    index += recordMaxLength;
                    repeatCount--;
                    continue;
                }
                System.out.format(format, "", "", record.substring(index));
                index += recordMaxLength;
                repeatCount--;
            }
        } else {
            System.out.format(format, recordId, count, record);
        }
    }
}
