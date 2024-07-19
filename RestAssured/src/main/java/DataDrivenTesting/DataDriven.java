package DataDrivenTesting;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {

	public ArrayList<String> DataExcel(String testcaseName) throws IOException {
		ArrayList<String> arraylist = new ArrayList<String>();

		// Identify the testcases column by scanning entire 1 st row
		// Once column is identified then scan entire testcase column to identify
		// purchase testcase row
		// after you grab purchase testcase row=pull all the data of that row and feed
		// into test

		FileInputStream fis = new FileInputStream(
				"C:\\Users\\Infiwave\\OneDrive - Nemergent Solutions SL\\Desktop\\Data Driven Testing.xlsx");
		XSSFWorkbook book = new XSSFWorkbook(fis);
		int sheets = book.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (book.getSheetName(i).equalsIgnoreCase("TestData"))
				;
			XSSFSheet sheet = book.getSheetAt(i);

			// Identify the testcases column by scanning entire 1 st row

			Iterator<Row> row = sheet.rowIterator(); // Sheet is collection of row
			Row firstRow = row.next();
			Iterator<Cell> cell = firstRow.cellIterator(); // row is collection of cells
			int k = 0;
			int column = 0;
			while (cell.hasNext()) {
				Cell value = cell.next();
				if (value.getStringCellValue().equalsIgnoreCase("TestCase"))

				{
					column = k;
				}
				k++;
			}
			System.out.println(column);

			// Once column is identified then scan entire testcase column to identify

			while (row.hasNext()) {
				Row r = row.next();
				if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testcaseName)) {
					Iterator<Cell> cv = r.cellIterator();
					while (cv.hasNext()) {
						arraylist.add(cv.next().getStringCellValue());

					}
					System.out.println(arraylist);
				}
			}

		}
		return arraylist;
	}

}
