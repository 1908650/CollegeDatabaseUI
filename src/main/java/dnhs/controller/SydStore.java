package dnhs.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import dnhs.model.Model;
import dnhs.view.View;

public class SydStore {

	public static void main(String[] args) {
		Model model = new Model();
		View view = new View();
		new Controller(model, view);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// get 2/3 of the height, and 2/3 of the width
		int height = screenSize.height * 2 / 3;
		int width = screenSize.width * 2 / 3;
		view.setBounds(100, 100, width, height);
		view.setBackground(Color.PINK);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
	}

}
