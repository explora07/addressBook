import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Operations {
	 
	public void completeTasks() {
		
		String csvFileToRead = "csv/data.csv";  
		BufferedReader br = null;  
		String line = "";  
		String splitBy = ","; 
		String strToFind = "Male";
		List<AddressBookItems> addressBookList = new ArrayList<AddressBookItems>();  
	  
		try {  
		  
		   br = new BufferedReader(new FileReader(csvFileToRead));  
		   while ((line = br.readLine()) != null) {  
		  
			   String[] addressBookItems = line.split(splitBy);  
			  
			   AddressBookItems addressBookObj = new AddressBookItems();  
			  
			   addressBookObj.setName(addressBookItems[0]);  
			   addressBookObj.setSex(addressBookItems[1]);  
			   addressBookObj.setBirthDate(stringToDate(addressBookItems[2]));  
			  
			   addressBookList.add(addressBookObj);  
		   }
		   
		   // number of males
		   countMales(addressBookList, strToFind);
		   
		   // oldest person
		   calculateOldest(addressBookList);
		   
		   // age difference in days between Bill and Paul
		   System.out.print("Number of days " + addressBookList.get(0).getName() + " is older than " + addressBookList.get(1).getName() + ": " );
		   calculateDifference(addressBookList.get(0).getBirthDate(), addressBookList.get(1).getBirthDate());
		  
		  } catch (FileNotFoundException e) {  
			  e.printStackTrace();  
		  } catch (IOException e) {  
			  e.printStackTrace();  
		  } catch (ParseException e) {
			  e.printStackTrace();
		  } finally {  
			  if (br != null) {  
			    try {  
			     br.close();  
			    } catch (IOException e) {  
			     e.printStackTrace();  
			    }  
			  }  
		  }  
	}
	 
	
	 public static int countMales(List<AddressBookItems> addressBookList, String strToFind) {
		 int occurence=0;
		 for (int i = 0; i < addressBookList.size(); i++) { 
			 if (addressBookList.get(i).getSex().contains(strToFind))
			 occurence++;
		 }
		 System.out.println("Number of males in the address book: " + occurence);
	  
		 return occurence;
	 }
		   
	 
	 public String dateToString(Date date) {
		 SimpleDateFormat sdfDestination = new SimpleDateFormat("MM-dd-yyyy");
		 
		 return sdfDestination.format(date);
	 }
	 
	 public Date stringToDate(String dateStr)  throws ParseException {
	      SimpleDateFormat sdfSource = new SimpleDateFormat("dd/MM/yy");
	      Date date = sdfSource.parse(dateStr);
	     
	      return date;
	 }
	
	 
	 public void calculateOldest(List<AddressBookItems> addressBookList) {
		 
		 AddressBookItems tempItem = new AddressBookItems();
		
		 for (AddressBookItems addressBookItems : addressBookList) {
			 if (tempItem.getBirthDate() == null) {
				 tempItem = addressBookItems;
			 }
			 else 
			 if (addressBookItems.getBirthDate().before(tempItem.getBirthDate())){
				 tempItem = addressBookItems;				 
			 }
		 }
		
		 System.out.println("Oldest person is: " + tempItem.getName() + ", " + dateToString(tempItem.getBirthDate()));
	 }
	 
	 
	 public void calculateDifference(Date date1, Date date2) {
		 Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(date1);
		 Calendar cal2 = Calendar.getInstance();
		 cal2.setTime(date2);
		 
		 // difference in milliseconds:
		 long difference = cal2.getTimeInMillis() - cal1.getTimeInMillis();
		 
		 // convert to days		 
		 long diffTimeUnit = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
		 System.out.println(diffTimeUnit);

	 }
	 
}  

