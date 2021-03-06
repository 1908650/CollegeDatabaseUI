package dnhs.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionListener;

public class FooterPanel extends JPanel {
	private static JPanel FooterPanel;
	private static JButton btnCheckout;
	private static JButton btnCompleteTransaction;
	private static JLabel carttotal;

	public FooterPanel() {
		FooterPanel = new JPanel();
		FooterPanel.setBackground(new Color(255, 203, 164));

		carttotal = new JLabel("");
		FooterPanel.add(carttotal);

		btnCheckout = new JButton("Clear List");
		FooterPanel.add(btnCheckout);

		btnCompleteTransaction = new JButton("Complete Order");
	}

	public void addCheckoutListener(ActionListener listener) {
		btnCheckout.addActionListener(listener);
	}

	public void completeTransactionListener(ActionListener listner) {
		btnCompleteTransaction.addActionListener(listner);
	}

	public static void addCompleteTransactionBtn(ActionListener completeTransactionListener) {
		FooterPanel.add(btnCompleteTransaction);
	}

	public void removeCompleteTransactionBtn() {
		FooterPanel.remove(btnCompleteTransaction);
	}

	public static void addCheckoutBtn() {
		FooterPanel.add(btnCheckout);
		FooterPanel.remove(btnCompleteTransaction);
	}

	public void removeCheckoutBtn() {
		FooterPanel.remove(btnCheckout);
		FooterPanel.add(btnCompleteTransaction);
	}

	public JPanel getPanel() {
		return FooterPanel;
	}

	public static void setTotalText(float cartTotal) {
		carttotal.setText("TOTAL: " + cartTotal);
	}
}
