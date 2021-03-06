package dnhs.view;
import dnhs.model.*;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;

public class NavPanel extends JPanel {
	private JPanel NavPanel;
	JComboBox<String> artFilter;
	private JButton btnStore;
	private JButton btnCheckout;
	private JButton searchButton; //enter button
	public JTextField searchBar;
	private Model modObj = new Model();
	
	public NavPanel() {
		NavPanel = new JPanel(new BorderLayout());

		JPanel titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(1500, 100));
		JLabel image = new JLabel();
		image.setIcon(new ImageIcon("img//title.jpg"));
		titlePanel.add(image);
		NavPanel.add(titlePanel, BorderLayout.NORTH);

		JPanel btnPanel = new JPanel(new BorderLayout());
		btnPanel.setBackground(new Color(255, 203, 164));
		//String[] artType = { "All", "Private", "Public" };

		JPanel westPanel = new JPanel();
		westPanel.setBackground(new Color(255, 203, 164));
		JLabel sort = new JLabel("Sort by:");
		westPanel.add(sort);
		//artFilter = new JComboBox<String>(artType);
		//artFilter.setSelectedIndex(0);
		//westPanel.add(artFilter);
		btnPanel.add(westPanel, BorderLayout.EAST);

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(255, 203, 164));
		btnStore = new JButton("Colleges");
		centerPanel.add(btnStore);
		btnCheckout = new JButton("My List");
		centerPanel.add(btnCheckout);
		btnPanel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(new Color (255, 203, 164));
		JLabel searchlabel = new JLabel("Search For Student (Enter Full Name):");
		searchPanel.add(searchlabel);
		searchBar = new JTextField();
		searchBar.setToolTipText("");
		searchBar.setDisabledTextColor(Color.GRAY);
		searchBar.setBounds(16, 5, 466, 55);
		searchBar.setPreferredSize(new Dimension(200,25));
		searchPanel.add(searchBar);
		searchButton = new JButton("Enter");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					modObj.getStudentInfo(searchBar.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}   
				    
			}
		});
		searchPanel.add(searchButton);
		btnPanel.add(searchPanel, BorderLayout.WEST);
		
		

		NavPanel.add(btnPanel, BorderLayout.SOUTH);
	}

	public void addFilterListener(ActionListener listener) {
		artFilter.addActionListener(listener);
	}

	public void addStoreListener(ActionListener listener) {
		btnStore.addActionListener(listener);
	}

	public void addCartListener(ActionListener listener) {
		btnCheckout.addActionListener(listener);
	}
	
	public void addEnterListener(ActionListener listener) {
		searchButton.addActionListener(listener);
	}
	public void addSearchListener(ActionListener listener) {
		searchButton.addActionListener(listener);
	}
			
			

	public JPanel getPanel() {
		return NavPanel;
	}

}
