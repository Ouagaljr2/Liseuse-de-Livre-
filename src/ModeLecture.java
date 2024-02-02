import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
 
// Classe de fenêtre Swing permettant de visualiser un document (HTML ou texte)

public class ModeLecture extends JFrame implements ActionListener
{
 
	private static final long serialVersionUID = 1L;
	
	// L'editor pouir contenir le livre
	private JEditorPane editor = new JEditorPane();

	// conteneur de la partie poour l'edition des commentaires
	private JTextArea spaceComment =new JTextArea("mettez vos commentaires ici et retrouvé les dans le fichier crée");
	
	// id du livre
	private String id;
	
	/***
	 * Constructeur de fenetre de la leture du livre
	 */
	public ModeLecture () {	
		super("lecture");
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(1000, 500);
		this.setLocationRelativeTo(null);
		
  }
	/**
	 * @param url url du livre a chargé
	 * @param id_livre l'id du livre a chargé
	 * @return fenetre de la lecture du livre
	 */
	public ModeLecture chargementDulivre(String url,String id_livre) {
		this.id=id_livre;
		
		ModeLecture edit =new ModeLecture();
		
		// pour empecher la modification du contenu du livre
		editor.setEditable(false); 

		try {
			editor.setPage(url);

			}catch (IOException e) {
				editor.setContentType("text/html");
	            editor.setText("Ce livre n'est pas disponible");
	        }
		
		JScrollPane scrollPane = new JScrollPane(editor);
		
	    edit.add(scrollPane,BorderLayout.CENTER);
	    edit.add(creatComment(),BorderLayout.WEST);
	    edit.add(creatEdition(),BorderLayout.NORTH);
	    edit.add(creatNote(),BorderLayout.SOUTH);
	    
		//activation de l'anotaion du livre
	    
	    return edit;
	}
	
	// creeer les options d'affichage du text
	
	/**
	 * Créee la bar qui permet d'editer(taille ,police) l'affichage du livre 
	 * @return JToolBar qui contient les options
	 */
	private JToolBar creatEdition() {
		
		JToolBar navigateur= new JToolBar();
		navigateur.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JPanel panelNav =new JPanel();
		
		// button appliqué
		JButton apply= new JButton("Appliquer");
		
		String[] tailles= {"16","18","20"};
		JComboBox<String> Taille= new JComboBox<>(tailles);
		
		String[] police= {"serif","sans serif"};
		JComboBox<String> Police= new JComboBox<>(police);
		
		JLabel l1=new JLabel("Taille de la pollice");
		JLabel l2=new JLabel("police souhaité");
		
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int taille=Integer.parseInt(Taille.getSelectedItem().toString());
				String police=Police.getSelectedItem().toString();

				Font f = new Font(police,Font.ROMAN_BASELINE, taille);
		        editor.setFont(f);
		      	
			}
		});
		
		panelNav.add(l1);
		panelNav.add(Taille);
		panelNav.add(l2);
		panelNav.add(Police);
		panelNav.add(apply);
		
		navigateur.add(panelNav);
		
		return navigateur;
	}
	
	
	// creation de la partie commentaire
	
		/**
		 * Créee le panel qui va servir a la prise de notes lors de la lecture
		 * @return JPanel correcspond a l'endroit de la prise des notes
		 */
		private JPanel creatComment() {
			
			JPanel comment=new JPanel();
			comment.setLayout(new BorderLayout());
			
			// boutons ajout et suprression de comm
			JButton ajouter= new JButton("Generer le fichier commentaire");
			ajouter.addActionListener(this::actionPerformed);
			
			// Ajout du champ de saisi
			JScrollPane scrollComment =new JScrollPane(spaceComment);
			comment.add(scrollComment,BorderLayout.CENTER);
			comment.add(ajouter,BorderLayout.NORTH);
			comment.add(scrollComment);

			return comment;
		}
		
		
		private JPanel creatNote() {
			JToolBar champNote =new JToolBar();
			JButton noter= new JButton("Noter");
			JLabel labNote=new JLabel("Donner une note au livre");
			String[] notes= {"1","2","3","4","5"};
			JComboBox<String> Note= new JComboBox<>(notes);
			champNote.add(labNote);
			champNote.add(Note);
			champNote.add(noter);
			
			noter.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String note=Note.getSelectedItem().toString();
					addToNoteFile(note);
										
				}
			});
			
			JPanel pane=new JPanel();
			pane.add(champNote);
			return pane;
		}
		
		// action  effectuer apres le click sur ajouter un commentaire
		
		/**
		 * Ajout des commentaires dans un fichier text nommé livre"id".txt
		 * id correspond a l'id du livre
		 */
		public void actionPerformed(ActionEvent e) {
			String text=spaceComment.getText();
			
			addCommentToFile(this.id, text);
			
		}
		
		/**
		 * @param id id du livre pour identifier chaque fichier de commentaire de chaque livre
		 * @param textToadd le commentaire a ajouter dans le fichie correspondant
		 */
		private void addCommentToFile(String id,String textToadd) {
			
			try {
            	File f = new File("livre"+id+".txt");

            	if( !f.exists() ) {
                	f.createNewFile();
            }
            	
            	Files.write(Paths.get("livre"+id+".txt"), textToadd.getBytes(), StandardOpenOption.APPEND);
        	}catch (IOException e) {
        		System.out.println("Erreur dans la partie ecriture du Fichier");
        	}
		}
		
		/***
		 * Permet de creer un fichier qui va contenir les notes données aux livres
		 * @param note
		 */
		private void addToNoteFile(String note) {
			String nom="Le livre "+id+" a eu une note de "+note;
			try {
            	File f = new File("Notes.txt");

            	if( !f.exists() ) {
                	f.createNewFile();
                }
            	
            	Files.write(Paths.get("Notes.txt"), nom.getBytes(), StandardOpenOption.APPEND);
        	}catch (IOException e) {
        		System.out.println("Erreur dans la partie ecriture du Fichier");
        	}
			
		}
		
	
}