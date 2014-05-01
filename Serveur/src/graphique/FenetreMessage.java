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

import java.awt.*;
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

    public FenetreMessage(){
        super();

        build();//On initialise notre fenetre
    }

    private void build(){
        setTitle("Tapez votre message"); //On donne un titre de l'application
        setSize(400,200); //!#@@#!On donne une taille de notre fenetre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit a l'application de se fermer lors du clic sur la croix
        setContentPane(buildContentPane());
    }

    private JPanel buildContentPane(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.white);

        field1 = new JTextField();
        field1.setColumns(20);

        panel.add(field1);


        JButton bouton = new JButton(new SendAction());

        panel.add(bouton);



        return panel;
    }

    private class SendAction extends AbstractAction {

        private SendAction() {

            super("Envoyer");

        }


        public void actionPerformed(ActionEvent e) {

        	System.out.println("Appui Envoyer");
        	BFList bfList = new BFList("listeBF.txt");
    		ArrayList<BumpFriend> liste = bfList.getBFliste();
    		System.out.println("Creation message");
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