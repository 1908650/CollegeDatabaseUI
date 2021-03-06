package dnhs.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.util.Iterator;
import java.util.HashMap;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class Model {
	private String BAG_LOCATION = "cart.csv";
	private String PRODUCT_LOCATION = "arts.csv";
	public String filter = "All";
	public String searchedKeyWord = "";

	public List<String[]> readAll() {
		try {
			List<String[]> allStudents = new ArrayList<String[]>();
			ArrayList<String> studentNames = getAllStudents();
			for (int i = 0; i < studentNames.size(); i++) {
				allStudents.add(getStudentInfo(studentNames.get(i)));
			}

			return allStudents;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public String[] getStudentInfo(String studentName) throws Exception {
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		try {
			credentialsProvider.getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/Users/johnmortensen/.aws/credentials), and is in valid format.", e);
		}
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider)
				.withRegion("us-west-2").build();
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("DNSeniors");
		HashMap<String, String> nameMap = new HashMap<String, String>();
		nameMap.put("#stu", "Student");
		HashMap<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(":st", studentName);
		QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#stu = :st").withNameMap(nameMap)
				.withValueMap(valueMap);
		ItemCollection<QueryOutcome> items = null;
		Iterator<Item> iterator = null;
		Item item = null;
		String[] studentInfo = new String[4];

		try {
			items = table.query(querySpec);
			iterator = items.iterator();

			while (iterator.hasNext()) {
				item = iterator.next();

				String type = item.getString("Type");
				String tuition = item.getString("Tuition");
				String college = item.getString("College");
				String description = "Alumni: " + item.getString("Student") + "\nMajor(s): " + item.getString("Major") + "\n Acceptance Rate: " + item.getDouble("Acceptance %")+ "\n University Tuition: "+item.getInt("Tuition") + 
						"\n Student Email: "+ item.getString(("Email")) + "\n University Type: " + item.getString("Type");
				studentInfo[0] = type;
				studentInfo[1] = tuition;
				studentInfo[2] = college;
				studentInfo[3] = description;
			}
			if (studentInfo.equals(null)) {
				studentInfo[0] = "Unable to find this student. Please check your spelling or verify that this is a Del Norte Senior.";
// for commit
			}

		}

		catch (Exception e) {
			studentInfo[0] = "Unable to find this student";
			System.err.println(e.getMessage());
		}

		return studentInfo;
	}

	public void cartAdd(String name, String type, String price) {
		List<University> arts = retrieveCart();
		if (arts == null)
			arts = new ArrayList<University>();

		if (type.equals("Private")) {
			arts.add(new Private(type, price, name));
		} else if (type.equals("Public")) {
			arts.add(new Public(type, price, name));
		}
		writeCSV(arts, BAG_LOCATION, false);
	}

	public List<University> retrieveCart() {
		try {
			List<University> arts = new ArrayList<University>();
			List<String[]> lines = this.readAll();
			for (String[] item : lines) {
				if (item[0].equals("Private")) {
					arts.add(new Private(item[0], item[1], item[2]));
				} else if (item[0].equals("Public")) {
					arts.add(new Public(item[0], item[1], item[2]));
				}
			}

			return arts;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<University> retrieveProducts() {
		try {
			List<University> arts = new ArrayList<University>();
			List<String[]> lines = this.readAll();
			for (String[] item : lines) {
				if (item[0] == null)
					continue;
				if (item[0].equals("Private") && (filter.equals("Private") || filter.equals("All"))) {
					arts.add(new Private(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("Public") && (filter.equals("Public") || filter.equals("All"))) {
					arts.add(new Public(item[0], item[1], item[2], item[3]));
				}
			}

			// Collections.sort(arts);
			return arts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<University> retrieveSearch() {
		try {
			List<University> arts = new ArrayList<University>();
			List<String[]> lines = this.readAll();
			for (String[] item : lines) {
				if (item[0].equals("Private") && item[3].toLowerCase().contains(searchedKeyWord)) {
					arts.add(new Private(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("Public") && item[3].toLowerCase().contains(searchedKeyWord)) {
					arts.add(new Public(item[0], item[1], item[2], item[3]));
				}
			}

			return arts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeCSV(List<University> arts, String csvlocation, boolean replace) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(csvlocation, replace));

			List<String[]> allLines = new ArrayList<String[]>();
			for (University product : arts) {
				String[] line = new String[] { product.type, product.tuition, product.name, product.description };
				allLines.add(line);
			}
			writer.writeAll(allLines);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearCSV() {
		try {
			FileWriter writer = new FileWriter(BAG_LOCATION);
			writer.write("");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public float getCartTotal() {
		float total = 0;
		List<University> cart = retrieveCart();
		if (cart != null) {
			for (University product : cart) {
				total += Float.parseFloat(product.tuition);
			}
		}
		return total;
	}

	public ArrayList<String> getAllStudents() {

		ArrayList<String> Students = new ArrayList<String>();
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		try {
			credentialsProvider.getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/Users/johnmortensen/.aws/credentials), and is in valid format.", e);
		}
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider)
				.withRegion("us-west-2").build();
		DynamoDB dynamoDB = new DynamoDB(client);
		// Table table = dynamoDB.getTable("Student");
		ScanRequest scanRequest = new ScanRequest().withTableName("DNSeniors");
		ScanResult result = client.scan(scanRequest);

		for (Map<String, AttributeValue> item : result.getItems()) {
			// add specification: getStudentName()
			// print the Student name only-- when clicked, goes to page with ALL of
			// student's info
			String itemString = item.get("Student").toString();
			Students.add(itemString.substring(itemString.indexOf(" ") + 1, itemString.length() - 2));
			;

		}

		return Students;

	}

}
