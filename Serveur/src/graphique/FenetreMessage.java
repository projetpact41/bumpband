package graphique;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.Destinataire;

import com.example.bump.actions.BFList;
import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Message;

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

        	System.out.println("Appui Envoyer");
        	BFList bfList = new BFList("listeBF.txt");
    		ArrayList<BumpFriend> liste = bfList.getBFliste();
    		System.out.println("Cr�ation message");
    		Message message = new Message(field1.getText(),"Admin");
    		System.out.println("Message cree "+ liste.size());
    		
    		for (BumpFriend bf : liste) { //On parcourt l'ensemble des bf pour leur faire parvenir la nouvelle
    			System.out.println("Envoie message");
    			Destinataire destinataire = new Destinataire(bf.getAdresse(),4444);
    			destinataire.envoieObjet(message);
    		}
            //!#@@#! Fais une fonction envoyer sachant que tu peux avoir la string en faisant getField1()
            }


        }
    }