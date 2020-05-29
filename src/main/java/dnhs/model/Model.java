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
		List<Art> arts = retrieveCart();
		if (arts == null)
			arts = new ArrayList<Art>();

		if (type.equals("Pencil")) {
			arts.add(new Pencil(type, price, name));
		} else if (type.equals("Ink")) {
			arts.add(new Ink(type, price, name));
		} else if (type.equals("Acrylic")) {
			arts.add(new Acrylic(type, price, name));
		} else if (type.equals("Digital")) {
			arts.add(new Digital(type, price, name));
		} else if (type.equals("OtherMediums")) {
			arts.add(new OtherMediums(type, price, name));
		}
		writeCSV(arts, BAG_LOCATION, false);
	}

	public List<Art> retrieveCart() {
		try {
			CSVReader reader = new CSVReader(new FileReader(BAG_LOCATION));
			List<Art> arts = new ArrayList<Art>();
			List<String[]> lines = reader.readAll();
			for (String[] item : lines) {
				if (item[0].equals("Pencil")) {
					arts.add(new Pencil(item[0], item[1], item[2]));
				} else if (item[0].equals("Ink")) {
					arts.add(new Ink(item[0], item[1], item[2]));
				} else if (item[0].equals("Acrylic")) {
					arts.add(new Acrylic(item[0], item[1], item[2]));
				} else if (item[0].equals("Digital")) {
					arts.add(new Digital(item[0], item[1], item[2]));
				} else if (item[0].equals("OtherMediums")) {
					arts.add(new OtherMediums(item[0], item[1], item[2]));
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

	public List<Art> retrieveProducts() {
		try {
			CSVReader reader = new CSVReader(new FileReader(PRODUCT_LOCATION));
			List<Art> arts = new ArrayList<Art>();
			List<String[]> lines = reader.readAll();
			for (String[] item : lines) {
				if (item[0].equals("Pencil") && (filter.equals("Pencil") || filter.equals("All"))) {
					arts.add(new Pencil(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("Ink") && (filter.equals("Ink") || filter.equals("All"))) {
					arts.add(new Ink(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("Acrylic") && (filter.equals("Acrylic") || filter.equals("All"))) {
					arts.add(new Acrylic(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("Digital") && (filter.equals("Digital") || filter.equals("All"))) {
					arts.add(new Digital(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("OtherMediums") && (filter.equals("OtherMediums") || filter.equals("All"))) {
					arts.add(new OtherMediums(item[0], item[1], item[2], item[3]));
				}
			}
			reader.close();
			Collections.sort(arts);
			return arts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Art> retrieveSearch() {
		try {
			CSVReader reader = new CSVReader(new FileReader(PRODUCT_LOCATION));
			List<Art> arts = new ArrayList<Art>();
			List<String[]> lines = reader.readAll();
			for (String[] item : lines) {
				if (item[0].equals("Pencil") && item[3].toLowerCase().contains(searchedKeyWord)) {
					arts.add(new Pencil(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("Ink") && item[3].toLowerCase().contains(searchedKeyWord)) {
					arts.add(new Ink(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("Acrylic") && item[3].toLowerCase().contains(searchedKeyWord)) {
					arts.add(new Acrylic(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("Digital") && item[3].toLowerCase().contains(searchedKeyWord)) {
					arts.add(new Digital(item[0], item[1], item[2], item[3]));
				} else if (item[0].equals("OtherMediums") && item[3].toLowerCase().contains(searchedKeyWord)) {
					arts.add(new OtherMediums(item[0], item[1], item[2], item[3]));
				}
			}
			reader.close();

			return arts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeCSV(List<Art> arts, String csvlocation, boolean replace) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(csvlocation, replace));

			List<String[]> allLines = new ArrayList<String[]>();
			for (Art product : arts) {
				String[] line = new String[] { product.type, product.price, product.name, product.description };
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
		List<Art> cart = retrieveCart();
		if (cart != null) {
			for (Art product : cart) {
				total += Float.parseFloat(product.price);
			}
		}
		return total;
	}

}
