import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;



public class Equity {

	private String ticker;
	private String equityName;
	private int shareCount;
	private int equityType;
	private String exchange;
	private double currentPrice;
	private double priceIncrease;
	private double dividentYield;
	private int paymentCycle;
	private double calculatedDivident;
	private double currentValue;
	private double futureValue;
	
	public Equity() {
		this.ticker = "";
		this.equityName = "";
		this.shareCount = 0;
		this.equityType = 0;
		this.exchange = "";
		this.currentPrice = 0;
		this.priceIncrease = 0;
		this.dividentYield = 0;
		this.paymentCycle = 0;
		this.currentValue = 0;
	}
	

	public Equity(String ticker, String equityName, int shareCount, int equityType, String exchange, double currentPrice, double priceIncrease, double dividentYield, int paymentCycle) {		
		this.ticker = ticker;
		this.equityName = equityName;
		this.shareCount = shareCount;
		this.equityType = equityType;
		this.exchange = exchange;
		this.currentPrice = currentPrice;
		this.priceIncrease = priceIncrease / 100.00;
		this.dividentYield = dividentYield / 100.00;
		this.paymentCycle = paymentCycle;
		this.calculatedDivident = calcDividentAmount();
		this.currentValue =  calcCurrentValue();
	}
	
	@Override
	public String toString() {
		return "Equity [ticker=" + ticker + ", equityName=" + equityName + ", shareCount=" + shareCount
				+ ", currentPrice=" + currentPrice +  ", currentValue=" + currentValue + "]";
	}
	
	
	private double calcCurrentValue() {
		return shareCount * currentPrice;
	}
	
	private double calcDividentAmount() {
		calculatedDivident = (dividentYield * currentPrice) / paymentCycle;
		return calculatedDivident;
	}
	
	public double calcFutureValue(int nYear) {
		double total = 0;
		for(int i = 1; i <= nYear; i++) 
		{
			currentPrice = currentPrice + currentPrice*priceIncrease;			
			total = total + currentPrice + calcDividentAmount();
			futureValue = total * shareCount;
		}		
		return futureValue;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public void setEquityName(String equityName) {
		this.equityName = equityName;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	public void setEquityType(int equityType) {
		this.equityType = equityType;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public void setPriceIncrease(double priceIncrease) {
		this.priceIncrease = priceIncrease;
	}
	public void setDividentYield(double dividentYield) {
		this.dividentYield = dividentYield;
	}
	public void setPaymentCycle(int paymentCycle) {
		this.paymentCycle = paymentCycle;
	}
	public void setCalculatedDivident(double calculatedDivident) {
		this.calculatedDivident = calculatedDivident;
	}
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}
	public void setFutureValue(double futureValue) {
		this.futureValue = futureValue;
	}
	
	
	public String getTicker() {
		return ticker;
	}
	public String getEquityName() {
		return equityName;
	}
	public int getShareCount() {
		return shareCount;
	}
	public String getEquityType() {
		String type = "";
		switch (equityType) {
		  case 0:
		    type = "Stock";
		    break;
		  case 1:
		    type = "ETF";
		    break;
		  case 2:
		    type = "REIT";
		    break;
		}
		return type;
	}
	public String getExchange() {
		return exchange;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public double getPriceIncrease() {
		return priceIncrease;
	}
	public double getDividentYield() {
		return dividentYield;
	}
	public String getPaymentCycle() {
		String cycle = "";
		switch (paymentCycle) {
		  case 1:
		    cycle = "Yearly";
		    break;
		  case 4:
		    cycle = "Quarterly";
		    break;
		  case 12:
		    cycle = "Monthly";
		    break;
		}
		return cycle;
		
	}
	public double getCurrentValue() {
		return currentValue;
	}
	
}

class Driver {

	public static void main(String[] args) throws IOException, NumberFormatException {
		
		final int LINES_ABOVE_DATA = 2;					// Constant that indicates the number of lines before data starts.
		
		
		String inputFileName = args[0];					// Get input file name from a command line argument.
		String outputFileName = args[1];				// Get output file name from a command line argument.
		
			
		System.out.println("Input File: " + inputFileName);			// Display input file name on console.
		System.out.println("Output File: " + outputFileName);		// Display output file name on console.
		
		File inputFile = new File(inputFileName);					// Create a File object.
		
		Scanner input = new Scanner(inputFile);						// Create a Scanner object.
		PrintWriter output = new PrintWriter(outputFileName);		// Create a PrintWriter object.
		int lineCount = lineCount(inputFileName);					// Get line count in file.
		
		String headerArray[] = new String[9];						// Create an array for header.
		String inputArray[][] = new String[lineCount - LINES_ABOVE_DATA][9];		//Create a nested array for input.

		
		writeToHeaderArr(input, headerArray);											// Write header to array.
		writeToArr(input, inputArray, lineCount(inputFileName), LINES_ABOVE_DATA);		// Write data to array.
		
		ArrayList<Equity> equityList = new ArrayList<Equity>();							// Create an Array List for Equity objects.
		
		for (int i = 0; i < lineCount-LINES_ABOVE_DATA; i++)							// Create equity objects for each line using constructor, write data from inputArray and add object to array list.
		{				
			Equity tempEquity = new Equity(inputArray[i][0],inputArray[i][1],Integer.parseInt(inputArray[i][2]), Integer.parseInt(inputArray[i][3]),inputArray[i][4],Double.parseDouble(inputArray[i][5]),Double.parseDouble(inputArray[i][6]), Double.parseDouble(inputArray[i][7]),Integer.parseInt(inputArray[i][8]));
			equityList.add(tempEquity);

		}
				
		
		createHTML(inputArray, headerArray, output, lineCount,  LINES_ABOVE_DATA, equityList);			//Call the method that creates the HTML file.
		
		System.out.println("Statistics Report Created");	// Display success message on console.
		
		input.close();		// Close scanner.		
		output.close();		// Close writer.

	}

	public static int lineCount(String file) throws FileNotFoundException, IOException			// Method that counts how many lines in the input file.
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int lines = 0;
		while (reader.readLine() != null) 
			lines++;
		reader.close();
		return lines;
	}
	
	public static void writeToHeaderArr(Scanner data, String[] arr)			// Method that writes data from the scanner to the input array.
	{
		String record = data.nextLine();	
		for(int j = 0; j < record.split(",").length; j++)
		{							
			arr[j] = record.split(",")[j];
		}
		
	}
	
	public static void writeToArr(Scanner data, String[][] arr, int lineTotal, int LINES_ABOVE_DATA)			// Method that writes data from the scanner to the input array
	{
		for(int j = 1; j < lineTotal; j++)
		{				
				String record = data.nextLine();
				if (j > LINES_ABOVE_DATA-1)
				{					
					for(int i = 0; i < 9; i++)																		
						arr[j - LINES_ABOVE_DATA][i] = record.split(",")[i];											
				}		
		}
	}
	
	public static void createHTML(String arr[][], String headerArr[], PrintWriter output, int lineTotal, int emptyLines, ArrayList<Equity> list)				// Method that creates the HTML file.
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();			// Formatter for dollar amounts.
		NumberFormat formatter2 = NumberFormat.getNumberInstance();				// Formatter for decimals.		
	    formatter2.setMinimumFractionDigits(4);
	    
		output.println("<!DOCTYPE html>");
		output.println("<html>");
		output.println("<body  bgcolor=\"#BDBDBD\" >");
		output.println("<h1 style=\"text-align:center;\">CSC-201 Project-5 </h1>");
		output.println("<h2 style=\"text-align:center;\">Enes Kalinsazlioglu</h2>");
		output.println("<br><br>");
		output.println("<h3>Equity Report</h3>");
		output.println("<br>");
		output.println("<table style=\"width:90%; margin-left:2em;\">");
		output.println("<tr>");
		
		for(int j = 0; j < headerArr.length; j++)								// Add header.
		{
			output.println("<th>" + headerArr[j] + "</th>");
		}
		output.println("<th>Current Value</th>");
		output.println("<th>Value in 5 Years</th>");
		output.println("<th>Value in 10 Years</th>");
		output.println("</tr>");
		output.println("<tr>");
		
		
		for(int i = 0; i < lineTotal - emptyLines; i++)													// Add rows.
		{			
			output.println("<td>" + list.get(i).getTicker() + "</td>");
			output.println("<td>" + list.get(i).getEquityName() + "</td>");
			output.println("<td>" + list.get(i).getShareCount() + "</td>");
			output.println("<td>" + list.get(i).getEquityType() + "</td>");			
			output.println("<td>" + list.get(i).getExchange() + "</td>");			
			output.println("<td>" + formatter.format(list.get(i).getCurrentPrice()) + "</td>");
			output.println("<td>%" + list.get(i).getPriceIncrease() + "</td>");
			output.println("<td>%" + formatter2.format(list.get(i).getDividentYield()) + "</td>");
			output.println("<td>" + list.get(i).getPaymentCycle() + "</td>");
			output.println("<td>" + formatter.format(list.get(i).getCurrentValue()) + "</td>");
			output.println("<td>" + formatter.format(list.get(i).calcFutureValue(5)) + "</td>");
			output.println("<td>" + formatter.format(list.get(i).calcFutureValue(10)) + "</td>");
			output.println("</tr><tr>");
			
		}
		
		output.println("</tr>");
		output.println("</table>");
		
		output.println("<style>table {\r\n"										// Styling for the HTML table.
				+ "  border-collapse: collapse;\r\n"
				+ "  font-size:20px;\r\n"
				+ "}\r\n"
				+ "tr {\r\n"
				+ "  border: solid #ddd;\r\n"
				+ "  border-width: 1px 0;\r\n"
				+ "  padding:15px;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "tr:nth-child(even)\r\n"
				+ "{\r\n"
				+ "  background-color: #f2f2f2;\r\n"
				+ "}\r\n"
				+ "td\r\n"
				+ "{\r\n"
				+ "    padding:5px;\r\n"
				+ "}</style>");
		
		output.println("</body>");
		output.println("</html>");
		
	}		
}
