package graphique;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import admin.Boisson;
import admin.Menu;

/**
 * Created by Arturo on 26/04/2014.
 */
public class BoissonDialog  extends JFrame {


    private JTextField field1;
    private JTextField field2;
    private JTextField field3;
    private JTextField field4;
    private JTextField field5;
    private JTextField field6;
	
    public BoissonDialog(){
        super();

        build();//On initialise notre fenetre
        JPanel jpanel = buildContentPane();
        this.setContentPane(jpanel);
    }

    private void build(){
    	
    	
        setTitle("Creer une nouvelle boisson"); //On donne un titre a l'application
        setSize(400,200); //!#@@#! On donne une taille a notre fen�etre : tu peux mettre plus petit
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit a l'application de se fermer lors du clic sur la croix
    }


    private JPanel buildContentPane(){
    	System.out.print("Salut");
    	
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


        class NouvelleBoissonAction extends AbstractAction {
            private NouvelleBoissonAction() {

                super("New");

            }


            public void actionPerformed(ActionEvent e) {
                //!#@@#! Pour creer une nouvelle boisson : tu utilises le constructeur avec les fonctions getField1,...
                // en utilisant getBytes pour convertir les string en byte lorsque tu as besoin

            	Boisson boisson = new Boisson(field1.getText(),Byte.parseByte(field2.getText()),
            			new com.example.bump.actions.Color((byte) Integer.parseInt(field4.getText()),(byte) Integer.parseInt(field5.getText())
            					, (byte) Integer.parseInt(field6.getText())), Byte.parseByte(field3.getText()));
            	Menu.ajouteBoisson(boisson);
            }

        }

        JButton bouton = new JButton(new NouvelleBoissonAction());

        panel.add(bouton);

        return panel;
    }

    public JTextField getField1(){
        return field1;
    }

    public JTextField getField2(){
        return field2;
    }

    public JTextField getField3(){
        return field3;
    }

    public JTextField getField4(){  return field4; }

    public JTextField getField5(){
        return field5;
    }

    public JTextField getField6(){
        return field6;
    }
}

