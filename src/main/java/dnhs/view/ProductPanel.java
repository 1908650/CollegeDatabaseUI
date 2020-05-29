package dnhs.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ProductPanel extends JPanel {
	private JPanel product;
	private String type;
	private String name;
	private String description;
	private float price;

	private ActionListener BuyNowListener;

	public ProductPanel(String type, String price, String name, String description, ActionListener buyNowListener) {
		this.type = type;
		this.price = Float.parseFloat(price);
		this.name = name;
		this.description = description;
		BuyNowListener = buyNowListener;
		createStoreItem();
	}

	public ProductPanel(String type, String price, String name) {
		this.type = type;
		this.name = name;
		this.price = Float.parseFloat(price);
		createCheckoutComponents();
	}

	public JPanel getPanel() {
		return product;
	}

	public JLabel prodImage(String imageLocation) {
		JLabel image = new JLabel();
		image.setIcon(new ImageIcon(imageLocation));
		Border b1 = new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY);
		Border b2 = new LineBorder(Color.GRAY, 12);
		Border b3 = new BevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY);
		Border bTemp = new CompoundBorder(b1, b2);
		Border b = new CompoundBorder(bTemp, b3);
		image.setBorder(b);
		return image;
	}

	private void createCheckoutComponents() {
		product = new JPanel(new BorderLayout());
		product.setName(name);
		product.setBackground(new Color(235, 232, 217));
		product.setBorder(new EmptyBorder(10, 5, 10, 5));

		product.add(prodImage("img//" + name + ".png"), BorderLayout.WEST);
		product.add(new JLabel(name), BorderLayout.NORTH);

		JPanel checkout = new JPanel(new BorderLayout());
		JPanel checkoutButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel checkoutInfo = new JPanel(new FlowLayout());
		checkoutInfo.add(new JLabel("$" + price));
		checkout.add(checkoutInfo, BorderLayout.NORTH);
		checkout.add(checkoutButtons, BorderLayout.SOUTH);
		product.setBackground(new Color(235, 232, 217));
		product.add(checkout, BorderLayout.EAST);
	}

	private void createStoreItem() {
		product = new JPanel(new BorderLayout());
		product.setName(name);
		product.setBackground(new Color(235, 232, 217));
		product.setBorder(new EmptyBorder(10, 5, 10, 5));

		JLabel artIcon = prodImage("img//" + name + ".png");
		product.add(artIcon, BorderLayout.WEST);
		product.add(new JLabel(name), BorderLayout.NORTH);

		JPanel tag = new JPanel();

		JButton details = new JButton("details");
		details.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog mydialog = new JDialog();
				mydialog.setTitle(name);
				mydialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
				JPanel content = new JPanel(new FlowLayout());
				content.add(prodImage("img//" + name + " BIG.png"));
				JTextArea des = new JTextArea((description));
				des.setPreferredSize(new Dimension(200, 490));
				des.setLineWrap(true);
				des.setWrapStyleWord(true);
				content.add(des);
				mydialog.add(content);
				mydialog.pack();
				mydialog.setLocationByPlatform(true);
				mydialog.setVisible(true);
			}
		});
		tag.add(details);

		JPanel priceInfo = new JPanel(new FlowLayout());
		priceInfo.add(new JLabel("$" + price));
		tag.add(priceInfo);

		JButton buynow = new JButton("Order");
		buynow.addActionListener(BuyNowListener);
		tag.add(buynow);

		product.add(tag, BorderLayout.SOUTH);
	}

}
