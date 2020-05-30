package dnhs.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.util.Collections;

public class Model {
	private String BAG_LOCATION = "cart.csv";
	private String PRODUCT_LOCATION = "arts.csv";
	public String filter = "All";
	public String searchedKeyWord = "";

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
			CSVReader reader = new CSVReader(new FileReader(BAG_LOCATION));
			List<University> arts = new ArrayList<University>();
			List<String[]> lines = reader.readAll();
			for (String[] item : lines) {
				if (item[0].equals("Private")) {
					arts.add(new Private(item[0], item[1], item[2]));
				} else if (item[0].equals("Public")) {
					arts.add(new Public(item[0], item[1], item[2]));
				}
			}
			reader.close();
			return arts;
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<University> retrieveProducts() {
		try {
			CSVReader reader = new CSVReader(new FileReader(PRODUCT_LOCATION));
			List<University> arts = new ArrayList<University>();
			List<String[]> lines = reader.readAll();
			for (String[] item : lines) {
				if (item[0].equals("Private") && (filter.equals("Private") || filter.equals("All"))) {
					arts.add(new Private(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("Public") && (filter.equals("Public") || filter.equals("All"))) {
					arts.add(new Public(item[0], item[1], item[2], item[3]));
				}
			}
			reader.close();
			// Collections.sort(arts);
			return arts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<University> retrieveSearch() {
		try {
			CSVReader reader = new CSVReader(new FileReader(PRODUCT_LOCATION));
			List<University> arts = new ArrayList<University>();
			List<String[]> lines = reader.readAll();
			for (String[] item : lines) {
				if (item[0].equals("Private") && item[3].toLowerCase().contains(searchedKeyWord)) {
					arts.add(new Private(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("Public") && item[3].toLowerCase().contains(searchedKeyWord)) {
					arts.add(new Public(item[0], item[1], item[2], item[3]));
				}
			}
			reader.close();

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

}
