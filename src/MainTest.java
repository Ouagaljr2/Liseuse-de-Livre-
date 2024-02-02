import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * 
 * @author MOHAMED YONIS Ahmed ET MAHAMAT Ouagal
 * 
 */

public class MainTest {
	
	/**
	 * Main pour tester les fonctionnalités
	 * @param args
	 * @throws UnsupportedLookAndFeelException
	 */
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		
		// creattion de la fenetre
		Mywindow window = null;
		
		try {
			window = new Mywindow();
			window.setVisible(true);

		} catch (Exception e) {
			System.out.println("Main window non creer");
		}
	}
}
