package graphique;



import javax.swing.*;

import com.example.bump.actions.BumpFriend;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.*;
import java.awt.event.ActionEvent;
 
public class FenetreTableau extends JFrame {
 /**
  *
  */
 private static final long serialVersionUID = 1L;
 private TableModel modele = new TableModel();
 
    private JTable tableau;
 
 
    public FenetreTableau() {
 
        super();
 
 
        setTitle("Admin : BumpFriend liste");
 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
 
        tableau = new JTable(modele);
 
 
        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
 
 
        JPanel boutons = new JPanel();
 
 
        boutons.add(new JButton(new AddAction()));
 
        boutons.add(new JButton(new RemoveAction()));
 
 
        getContentPane().add(boutons, BorderLayout.SOUTH);
 
 
        pack();
}
 
 
    public static void main(String[] args) {
 
        new FenetreTableau().setVisible(true);
}
 
 
    private class AddAction extends AbstractAction {
 
        /**
 	  *
 	  */
 	 private static final long serialVersionUID = 1L;
 
 	 private AddAction() {
 
            super("Ajouter");
 
        }
 
 
        public void actionPerformed(ActionEvent e) {
 
            try {
 	 	 	 modele.addAmi(new BumpFriend("Damien",InetAddress.getLocalHost()));
 	 	 } catch (UnknownHostException e1) {
 	 	 	 // TODO Auto-generated catch block
 	 	 	 e1.printStackTrace();
 	 	 }
 
        }
 
 	 
}
 
 
    private class RemoveAction extends AbstractAction {
 
        /**
 	  *
 	  */
 	 private static final long serialVersionUID = 1L;
 
 	 private RemoveAction() {
 
            super("Supprimer");
 
        }
 
 
        public void actionPerformed(ActionEvent e) {
 
            int[] selection = tableau.getSelectedRows();
 
 
            for(int i = selection.length - 1; i >= 0; i--){
 
                modele.removeAmi(selection[i]);
 
            }
 
        }
}
}
