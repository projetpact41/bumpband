//The MIT License (MIT)
//
//Copyright (c) 2014 Julien ROMERO
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

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

      //  boutons.add(new JButton(new BoissonAction()));


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


    public void add (String nclients, String nboissons, String netats) {
    	modele.add(nclients, nboissons, netats);
    }
    
    public void remove (String nclients) {
    	modele.remove(nclients);
    }
    

    /*private class BoissonAction extends AbstractAction {


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

        }*/
    }


