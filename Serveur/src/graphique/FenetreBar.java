package graphique;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 * Created by Arturo on 26/04/2014.
 */
public class FenetreBar extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BarModel modele = new BarModel();

    private JTable tableau;


    public FenetreBar() {

        super();


        setTitle("Admin : Commandes du bar");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        tableau = new JTable(modele);


        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);


        JPanel boutons = new JPanel();


        boutons.add(new JButton(new UpdateStateAction()));

        boutons.add(new JButton(new BoissonAction()));


        getContentPane().add(boutons, BorderLayout.SOUTH);


        pack();
    }


    public static void main(String[] args) {

        new FenetreBar().setVisible(true);
    }


    private class UpdateStateAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private UpdateStateAction() {

            super("Update Etat");

        }


        public void actionPerformed(ActionEvent e) {

            int[] selection = tableau.getSelectedRows();


            for (int i = selection.length - 1; i >= 0; i--) {

                modele.change(selection[i]);
            }


        }
    }



    private class BoissonAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private BoissonAction() {

            super("Boisson");

        }


        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    BoissonDialog fenetre = new BoissonDialog();
                    fenetre.setVisible(true);
                }
            });

            }

        }
    }


