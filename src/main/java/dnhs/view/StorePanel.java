package dnhs.view;

import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import dnhs.model.Art;

public class StorePanel extends JPanel {
	private JPanel store;
	private JPanel ProductsPanel;
	private JScrollPane scrollPane;
	private NavPanel NavPanel;
	private CheckoutPanel CheckoutPanel;
	private FooterPanel FooterPanel;
	private int storeStatus = 0;

	public StorePanel() {
		scrollPane = new JScrollPane(store);
		scrollPane.setViewportBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		createStorePanel();
		createProductsPanel();
		createNavigationPanel();
		createFooterPanel();
	}

	public void viewProducts(List<Art> arts, ActionListener[] buyNowListener) {
		storeStatus = 1;
		int count = 0;
		removeProductsFromDisplay();
		for (Art product : arts) {
			ProductPanel newproduct = new ProductPanel(product.type, product.price, product.name, product.description,
					buyNowListener[count]);
			ProductsPanel.add(newproduct.getPanel());
			count++;
		}
		scrollPane.setViewportView(ProductsPanel);
		store.add(scrollPane, BorderLayout.CENTER);
	}

	public void viewCart(List<Art> cart) {
		storeStatus = 2;
		removeProductsFromDisplay();
		if (cart == null)
			return;
		for (Art product : cart) {
			ProductPanel newproduct = new ProductPanel(product.type, product.price, product.name);
			ProductsPanel.add(newproduct.getPanel());
		}
		scrollPane.setViewportView(ProductsPanel);
		store.add(scrollPane, BorderLayout.CENTER);
	}

	public void viewCheckout(float total) {
		storeStatus = 3;
		removeProductsFromDisplay();
		CheckoutPanel = new CheckoutPanel(total);
		scrollPane.setViewportView(CheckoutPanel.getPanel());
		store.add(scrollPane, BorderLayout.CENTER);
	}

	public void removeProductsFromDisplay() {
		ProductsPanel.removeAll();
		store.remove(ProductsPanel);
	}

	public JPanel getPanel() {
		return store;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public NavPanel getNav() {
		return NavPanel;
	}

	public CheckoutPanel getCheckout() {
		return CheckoutPanel;
	}

	public FooterPanel getFooter() {
		return FooterPanel;
	}

	public void createStorePanel() {
		store = new JPanel();
		store.setLayout(new BorderLayout());
	}

	public void createProductsPanel() {
		ProductsPanel = new JPanel();
		ProductsPanel.setLayout(new WrapLayout());
		ProductsPanel.setBackground(new Color(235, 232, 217));
		ProductsPanel.setBounds(100, 100, 100, 100);

	}

	private void createNavigationPanel() {
		NavPanel = new NavPanel();
		NavPanel.setLayout(new FlowLayout(0, 10, 10));
		NavPanel.setBackground(new Color(0, 0, 0));
		store.add(NavPanel.getPanel(), BorderLayout.NORTH);
	}

	private void createFooterPanel() {
		FooterPanel = new FooterPanel();
		FooterPanel.setLayout(new FlowLayout(0, 10, 10));
		FooterPanel.setBackground(new Color(0, 0, 0));
		store.add(FooterPanel.getPanel(), BorderLayout.SOUTH);
	}

	public String getCurrentView() {
		if (storeStatus == 3)
			return "Checkout";
		else if (storeStatus == 2)
			return "Cart";
		else
			return "Store";
	}
}
