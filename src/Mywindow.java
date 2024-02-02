import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Mywindow extends JFrame {

	private static final long serialVersionUID = -7240552755269139643L;
	private JPanel contentPane=(JPanel) this.getContentPane();
	
	private DefaultMutableTreeNode livresLues= new DefaultMutableTreeNode("Livres lus");
	private JTree LivresTree; 
	
	/**
	 * Constructeur de la fenetre
	 * @throws Exception
	 */
	
	public Mywindow() {
		
		super("Bibliothèque");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		
		// creattion du contentPane
		contentPane.setLayout(new BorderLayout());
		
		// creationn del'arborescence
		contentPane.add(creatTree(),BorderLayout.CENTER);
		
		// creation de la barnav
		contentPane.add(creatNav(),BorderLayout.NORTH);
		
	}
	
	// cration de la bar de navigation a
	/**
	 * créee une JToolbar
	 * @return JToolBar
	 */
	private JToolBar creatNav() {
		
		JToolBar navigateur= new JToolBar();
		navigateur.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel recherche =new JPanel();
		
		// champ recherche
		JLabel l0=new JLabel("Id du livre a charger");
		JTextField rechercheField=new JTextField();
		rechercheField.setPreferredSize( new Dimension(200, 25));
		// button recherche
		JButton charger= new JButton("Charger");
		
		charger.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String id=( rechercheField.getText());
				ModeLecture livreTrouver=new ModeLecture();
				livreTrouver=lancement(id);
				livreTrouver.setVisible(true);
				DefaultMutableTreeNode newChild= new DefaultMutableTreeNode("le livre "+id);
				livresLues.add(newChild);
				
				DefaultTreeModel model=(DefaultTreeModel)LivresTree.getModel();
				model.reload();
			}
		});

		recherche.add(l0);
		recherche.add(rechercheField);
		recherche.add(charger);

		navigateur.add(recherche);
		
		return navigateur;
	}

	// creation de l'arbre
	/***
	 * créee Le jtree qui va contenir les livres
	 * @return
	 * @throws Exception
	 */
	private JScrollPane creatTree() {
		// creattion du scroll et de l'arbre
		
		DefaultMutableTreeNode livres= new DefaultMutableTreeNode("Livres");
		DefaultMutableTreeNode livresDisponnible= new DefaultMutableTreeNode("Livres Disponibles");
				
		// initialisation avec les livres du fichier livres.txt
		initTree(livresDisponnible);
		livres.add(livresDisponnible);
		livres.add(livresLues);

		JTree MesLivres=new JTree(livres); 
		this.LivresTree=MesLivres;
		
		//LivresTree.addTreeSelectionListener(this::valueChanged);	
		JScrollPane scroll= new JScrollPane(LivresTree);
		scroll.setPreferredSize(new Dimension(215,0));
		
		return scroll;
	}
		
	//lancement du livre
	/**
	 * Creation et Lecture(lancement) d'un livre selectioné
	 * @param id id du livre a lancé
	 * @return La fenetre qui permet la lecture du livre
	 */
	public ModeLecture lancement(String id) {
		
		ModeLecture viewerFrame = new ModeLecture ();
		ModeLecture scrollText;
		String url="https://www.gutenberg.org/files/"+id+"/"+id+"-h/"+id+"-h.htm";
		scrollText=viewerFrame.chargementDulivre(url,id);
		
		//contentPane.add(scrollText,BorderLayout.CENTER);
		return scrollText;	
	}
	
	
	// insertion des livres dans le noeud des livres 
	/***
	 * Charge tous les livres depuis le fichier livres.txt 
	 * @param node le noeud qui va contenir les livres
	 * @throws Exception
	 */
	private static void initTree(DefaultMutableTreeNode node)  {
		
			File lesLivres =new File("livre.txt");
			FileReader fr=null;
			try {
				fr = new FileReader(lesLivres);
			} catch (FileNotFoundException e) {
				System.out.println("Fichier Mywindow ligne 207 donner le bon chemain du fichier preferable l'absolu");
			}
			BufferedReader br = new BufferedReader(fr);
			String line;
			try {
				while((line=br.readLine())!=null) {
					DefaultMutableTreeNode item =new DefaultMutableTreeNode(line);
					node.add(item);
				}
			} catch (IOException e) {
				System.out.println("Erreur de lecture dans le fichier");
			}
			try {
				br.close();
			} catch (IOException e) {
				System.out.println("Erreur fermeture du bufferReader");
				
			}
	}
	
}





