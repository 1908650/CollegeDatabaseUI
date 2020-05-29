package dnhs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import dnhs.model.Model;
import dnhs.model.Art;
import dnhs.view.CheckoutPanel;
import dnhs.view.FooterPanel;
import dnhs.view.StorePanel;
import dnhs.view.View;

public class Controller {
	private Model model;
	private View view;
	private StorePanel store;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;

		store = new StorePanel();
		store.getNav().addFilterListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				getFilter(a);
			}
		});
		store.getNav().addSearchListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				displaySearch();
			}
		});
		store.getNav().addStoreListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				displayStore();
			}
		});
		store.getNav().addCartListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				displayCart();
			}
		});
		store.getFooter().addCheckoutListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				displayCheckout();
			}
		});
		store.getFooter().completeTransactionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				completeTransaction();
			}
		});

		store.viewProducts(model.retrieveProducts(), createBuyNowListeners(model.retrieveProducts().size()));
		view.addPanel(store.getPanel());

		view.viewRefresh();
	}

	public class BuyNowListener implements ActionListener {
		private String name;
		private String type;
		private String price;

		public BuyNowListener(String name, String type, String price) {
			this.name = name;
			this.type = type;
			this.price = price;
		}

		public void actionPerformed(ActionEvent e) {
			model.cartAdd(name, type, price);
			FooterPanel.setTotalText(model.getCartTotal());
			view.viewRefresh();
		}
	}

	private BuyNowListener[] createBuyNowListeners(int amount) {
		BuyNowListener[] buynowlistenerarray = new BuyNowListener[amount];
		if (amount <= 0)
			return null;
		else {
			int count = 0;
			for (Art product : model.retrieveProducts()) {
				buynowlistenerarray[count] = new BuyNowListener(product.name, product.type, product.price);
				++count;
			}
			return buynowlistenerarray;
		}
	}

	public void getFilter(ActionEvent e) {
		JComboBox<String> cb = (JComboBox<String>) e.getSource();
		String category = (String) cb.getSelectedItem();
		model.filter = category;
		displayStore();
		System.out.println("Category: " + category);
	}

	public void displaySearch() {
		String searchFor = store.getNav().searchBar.getText();
		model.searchedKeyWord = searchFor.toLowerCase();
		FooterPanel.setTotalText(model.getCartTotal());
		store.getPanel().add(store);
		view.add(store.getPanel());
		store.removeProductsFromDisplay();
		store.viewProducts(model.retrieveSearch(), createBuyNowListeners(model.retrieveProducts().size()));
		FooterPanel.addCheckoutBtn();
		view.viewRefresh();
	}

	public void displayStore() {
		FooterPanel.setTotalText(model.getCartTotal());
		store.getPanel().add(store);
		view.add(store.getPanel());
		store.removeProductsFromDisplay();
		store.viewProducts(model.retrieveProducts(), createBuyNowListeners(model.retrieveProducts().size()));
		FooterPanel.addCheckoutBtn();
		view.viewRefresh();
	}

	public void displayCart() {
		if (store.getCurrentView().equals("Cart")) {
			System.out.println("You're already on the cart page.");
		} else {
			store.viewCart(model.retrieveCart());
			FooterPanel.addCheckoutBtn();
			view.addPanel(store.getPanel());
			view.viewRefresh();
		}
	}

	public void displayCheckout() {
		store.removeProductsFromDisplay();
		store.viewCheckout(model.getCartTotal());
		CheckoutPanel checkoutPanel = store.getCheckout();
		checkoutPanel.setTotalText(model.getCartTotal());
		FooterPanel.addCompleteTransactionBtn(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				completeTransaction();
			}
		});
		store.getFooter().removeCheckoutBtn();
		store.viewCheckout(model.getCartTotal());
		view.viewRefresh();
	}

	public void completeTransaction() {
		model.clearCSV();
		store.removeProductsFromDisplay();
		store.viewProducts(model.retrieveProducts(), createBuyNowListeners(model.retrieveProducts().size()));
		FooterPanel.addCheckoutBtn();
		FooterPanel.setTotalText(model.getCartTotal());
		view.viewRefresh();
	}

}
