package graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Arturo on 27/04/2014.
 */
public class FlashMobFenetre extends JFrame{
        private JTextField field1;


        public FlashMobFenetre(){
            super();

            build();//On initialise notre fenetre
        }

    private void build(){
        setTitle("Flashmob"); //On donne un titre de l'application
        setSize(400,200); //!#@@#!On donne une taille de notre fenetre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit a l'application de se fermer lors du clic sur la croix
        setContentPane(buildContentPane());
    }

    private JPanel buildContentPane(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.white);

        field1 = new JTextField();
        field1.setColumns(10);

        panel.add(field1);


        JButton bouton = new JButton(new FlashMobAction("Lancer le FlashMob!"));

        panel.add(bouton);



        return panel;
    }

    public class FlashMobAction extends AbstractAction {


        public FlashMobAction(String texte){
            super(texte);


        }

        public void actionPerformed(ActionEvent e) {
            //!#@@#! Action lors du clic sur le bouton
        }
    }
}
