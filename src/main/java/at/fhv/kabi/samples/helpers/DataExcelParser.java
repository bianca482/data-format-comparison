package at.fhv.kabi.samples.helpers;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static at.fhv.kabi.samples.Main.BASE_PATH;

public class DataExcelParser {

    private static final File inputFolder = new File(BASE_PATH + "results");

    public static void writeSizeToExcel() {
        File[] sizeFiles = inputFolder.listFiles((dir, name) -> name.endsWith("Sizes.txt"));
        assert sizeFiles != null;
        parseAndWrite(sizeFiles, "Sizes", BASE_PATH + "results/fileSizes.xlsx");
    }

    public static void writeSpeedToExcel() {
        File[] timeFiles = inputFolder.listFiles((dir, name) -> name.endsWith("Times.txt"));
        assert timeFiles != null;
        parseAndWrite(timeFiles, "Times", BASE_PATH + "results/timeElapsed.xlsx");
    }

    public static void writeAllTimesToExcel(Map<String, Map<String, List<Long>>> data) throws RuntimeException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("SerializationSpeed");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Use Case");
        headerRow.createCell(1).setCellValue("Format");
        headerRow.createCell(2).setCellValue("Speed");

        int rowNum = 1;
        for (Map.Entry<String, Map<String, List<Long>>> result : data.entrySet()) {

            for (Map.Entry<String, List<Long>> entry : result.getValue().entrySet()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(1).setCellValue(result.getKey()); // Format
                row.createCell(0).setCellValue(entry.getKey()); // Use Case

                double[] times = entry.getValue()
                        .stream()
                        .mapToDouble(Long::doubleValue)
                        .toArray();

                double mean = StatUtils.mean(times);
                row.createCell(2).setCellValue(mean);
            }
        }

        saveWorkbook(workbook, BASE_PATH + "results/elapsedTimes.xlsx");
    }

    private static void parseAndWrite(File[] files, String valueType, String outputFile) {
        List<String[]> consolidatedData = new ArrayList<>();

        for (File file : files) {
            String testCaseName = file.getName().replace(valueType + ".txt", "");

            try {
                List<String[]> parsedData = parseDataFile(String.valueOf(file), testCaseName);
                consolidatedData.addAll(parsedData);
                writeDataToExcel(consolidatedData, outputFile, valueType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<String[]> parseDataFile(String inputFile, String name) throws IOException {
        List<String[]> dataList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        String line;

        Pattern pattern = Pattern.compile("^(.*?):\\s*(\\d+)$");

        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.matches()) {
                String format = matcher.group(1).trim();
                String value = matcher.group(2).trim();
                dataList.add(new String[]{name, format, value});
            }
        }

        reader.close();
        return dataList;
    }

    public static void writeDataToExcel(List<String[]> data, String outputFile, String valueType) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(valueType);

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Use Case");
        headerRow.createCell(1).setCellValue("Format");
        headerRow.createCell(2).setCellValue("Value");

        int rowNum = 1;
        for (String[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowData[0]);  // Test case name
            row.createCell(1).setCellValue(rowData[1]);  // Format
            row.createCell(2).setCellValue(Integer.parseInt(rowData[2]));  // Value
        }

        saveWorkbook(workbook, outputFile);
    }

    private static void saveWorkbook(Workbook workbook, String outputFile) {
        try(FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
