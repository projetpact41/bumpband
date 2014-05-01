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

import admin.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Arturo on 01/05/2014.
 */
public class MenuTableau  extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BoissonModel modele = new BoissonModel();

    private JTable tableau;

    private JTextField field1;
    private JTextField field2;
    private JTextField field3;
    private JTextField field4;
    private JTextField field5;
    private JTextField field6;


    public MenuTableau() {

        super();


        setTitle("Menu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        tableau = new JTable(modele);


        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);


        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.white);

        field1 = new JTextField();
        field1.setColumns(10);
        field1.setText("Nom");

        panel.add(field1);

        field2 = new JTextField();
        field2.setColumns(10);
        field2.setText("Prix");

        panel.add(field2);

        field3 = new JTextField();
        field3.setColumns(10);
        field3.setText("Degre Alcool");

        panel.add(field3);

        field4 = new JTextField();
        field4.setColumns(10);
        field4.setText("Rouge");

        panel.add(field4);

        field5 = new JTextField();
        field5.setColumns(10);
        field5.setText("Vert");

        panel.add(field5);

        field6 = new JTextField();
        field6.setColumns(10);
        field6.setText("Bleu");

        panel.add(field6);


        JButton bouton = new JButton(new NouvelleBoissonAction());

        JButton bouton2 = new JButton(new RemoveAction());

        panel.add(bouton);

        panel.add(bouton2);

        getContentPane().add(panel, BorderLayout.SOUTH);

        pack();

    }


    public static void main(String[] args) {

        new MenuTableau().setVisible(true);
    }

    class NouvelleBoissonAction extends AbstractAction {
        private NouvelleBoissonAction() {

            super("New");

        }


        public void actionPerformed(ActionEvent e) {
            //!#@@#! Pour creer une nouvelle boisson : tu utilises le constructeur avec les fonctions getField1,...
            // en utilisant getBytes pour convertir les string en byte lorsque tu as besoin
            System.out.print("Nouvelle boisson");
            Boisson boisson = new Boisson(field1.getText(), Byte.parseByte(field2.getText()),
                    new com.example.bump.actions.Color((byte) Integer.parseInt(field4.getText()), (byte) Integer.parseInt(field5.getText())
                            , (byte) Integer.parseInt(field6.getText())), Byte.parseByte(field3.getText())
            );
            admin.Menu.ajouteBoisson(boisson);
            modele.addBoisson();
        }

    }

    private class RemoveAction extends AbstractAction {

        private RemoveAction() {

            super("Supprimer");

        }


        public void actionPerformed(ActionEvent e) {

            int[] selection = tableau.getSelectedRows();


            for (int i = selection.length - 1; i >= 0; i--) {

                modele.removeBoisson(selection[i]);

            }

        }
    }
}



