package graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Arturo on 27/04/2014.
 */
public class FenetreMessage extends JFrame {

    private JTextField field1;

        public FenetreMessage() {

            super();


            setTitle("Pour taper votre message");

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            JPanel boutons = new JPanel();

            field1 = new JTextField();
            field1.setColumns(10);

            boutons.add(field1);


            boutons.add(new JButton(new SendAction()));


            getContentPane().add(boutons, BorderLayout.SOUTH);


            pack();
        }

    public JTextField getField1(){
        return field1;
    }

    public static void main(String[] args) {

        new FenetreMessage().setVisible(true);
    }

    private class SendAction extends AbstractAction {

        private SendAction() {

            super("Envoyer");

        }


        public void actionPerformed(ActionEvent e) {


            //!#@@#! Fais une fonction envoyer sacahnt que tu peux avoir la string en faisant getField1()
            }


        }
    }