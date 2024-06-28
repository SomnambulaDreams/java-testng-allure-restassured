package utils.data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ExcelReader {

    private final Workbook workbook;


    public ExcelReader(InputStream inputStream) {
        try {
            this.workbook = new XSSFWorkbook(inputStream);
        } catch (IOException ioex) {
            throw new RuntimeException(ioex.getMessage());
        }
    }

    public ExcelReader(String filepath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filepath);
            this.workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException ioex) {
            throw new RuntimeException(ioex.getMessage());
        }
    }

    public List<Map<String, Object>> readTestingDataFromSheet(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        List<List<Object>> content = getContent(sheet);
        return convertContentToMapList(content);
    }

    private List<List<Object>> getContent(Sheet sheet) {
        List<List<Object>> data = new LinkedList<>();
        for (Row currentRow : sheet) {
            List<Object> rowData = new LinkedList<>();
            for (Cell currentCell : currentRow) {
                switch (currentCell.getCellType()) {
                    case STRING:
                    case BLANK:
                        rowData.add(currentCell.getStringCellValue());
                        break;
                    case NUMERIC:
                        double value = currentCell.getNumericCellValue();
                        rowData.add(value);
                        break;
                    case FORMULA:
                        rowData.add(currentCell.getCellFormula());
                        break;
                    case BOOLEAN:
                        rowData.add(currentCell.getBooleanCellValue());
                        break;
                    default:
                        throw new RuntimeException("\nUnexpected value: " + currentCell.getCellType() + ".\nPlease check 'cellType' value.");
                }
            }
            data.add(rowData);
        }
        return data;
    }

    private List<Map<String, Object>> convertContentToMapList(List<List<Object>> sheetContent) {
        List<Map<String, Object>> result = new LinkedList<>();

        if (!sheetContent.isEmpty()) {
            // split headers and values of testing data
            List<String> headers = sheetContent.get(0).stream().map(i -> (String) i).collect(Collectors.toList());
            // values is a list of rows that contain values for testing purposes
            List<List<Object>> values = new LinkedList<>();
            for (int row = 1; row < sheetContent.size(); row++)
                values.add(sheetContent.get(row));

            for (List<Object> row : values) {
                Map<String, Object> map = new HashMap<>();
                for (int index = 0; index < row.size(); index++) {
                    map.put(headers.get(index), row.get(index));
                }
                result.add(map);
            }
        }
        return result;
    }
}
