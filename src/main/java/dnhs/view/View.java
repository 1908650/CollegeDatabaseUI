package dnhs.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JFrame {

	public View() {
		viewRefresh();
	}

	public void addPanel(JPanel panel) {
		getContentPane().add(panel);
	}

	public void removePanel(JPanel panel) {
		getContentPane().remove(panel);
	}

	public void viewRefresh() {
		getContentPane().repaint();
		getContentPane().revalidate();
	}
}
