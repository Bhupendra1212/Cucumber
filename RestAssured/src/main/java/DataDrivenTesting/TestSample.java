package DataDrivenTesting;

import java.io.IOException;
import java.util.ArrayList;

public class TestSample {

	public static void main(String[] args) throws IOException {
		DataDriven d=new DataDriven();
		ArrayList data=d.DataExcel("Add Profile");
		
		System.out.println(data.get(0));
		

	}

}
