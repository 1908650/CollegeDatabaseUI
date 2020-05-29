package dnhs.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CheckoutPanel extends JPanel {
	private JPanel CheckoutPanel;
	public JTextField FirstName;
	public JTextField LastName;
	public JTextField Email;
	public JTextField Address;
	public JLabel carttotal;
	public JTextField cc;

	public CheckoutPanel(float total) {
		CheckoutPanel = new JPanel(new BorderLayout());
		CheckoutPanel.setBorder(new EmptyBorder(100, 300, 100, 300));
		CheckoutPanel.setBackground(new Color(235, 232, 217));
		JPanel form = new JPanel();
		form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
		form.add(new JLabel("TOTAL: $" + total));
		form.add(new JLabel("  "));
		form.add(new JLabel("First Name: "));
		FirstName = new JTextField();
		FirstName.setPreferredSize(new Dimension(100, 10));
		form.add(FirstName);
		FirstName.setColumns(10);
		form.add(new JLabel("Last Name: "));
		LastName = new JTextField();
		LastName.setPreferredSize(new Dimension(100, 10));
		form.add(LastName);
		LastName.setColumns(10);

		form.add(new JLabel("Email: "));
		Email = new JTextField();
		form.add(Email);
		Email.setColumns(20);

		form.add(new JLabel("Address: "));
		Address = new JTextField();
		form.add(Address);
		Address.setColumns(20);

		form.add(new JLabel("Credit Card "));
		cc = new JTextField();
		cc.setColumns(20);
		cc.setText("0000 1111 2222 3333 4444");

		form.add(cc);

		carttotal = new JLabel("");
		form.add(carttotal);

		CheckoutPanel.add(form, BorderLayout.CENTER);

	}

	public JPanel getPanel() {
		return CheckoutPanel;
	}

	public void setTotalText(float cartTotal) {
		carttotal.setText("TOTAL: " + cartTotal);
	}
}
