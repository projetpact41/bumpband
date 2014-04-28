package graphique;

import javax.swing.table.AbstractTableModel;

import admin.Commande;

import java.util.ArrayList;


/**
 * Created by Arturo on 26/04/2014.
 */
public class BarModel extends AbstractTableModel {
        /**
         *
         */
        private final long serialVersionUID = 1L;

        private ArrayList<String> clients = new ArrayList<String>();
        private ArrayList<String> boissons= new ArrayList<String>();
        private ArrayList<String> etats= new ArrayList<String>();


        private final String[] entetes = {"Nom", "Boisson","Etat"};


        public BarModel() {

            super();


        }


        public int getRowCount() {
        	if (clients == null) return 0;
            return clients.size();
        }


        public int getColumnCount() {
        	if (entetes == null) return 0;
            return entetes.length;
        }


        public String getColumnName(int columnIndex) {
            return entetes[columnIndex];
        }


        public Object getValueAt(int rowIndex, int columnIndex) {

            switch(columnIndex){

                case 0:

                    return clients.get(rowIndex);

                case 1:

                    return boissons.get(rowIndex);

                case 2:

                    return etats.get(rowIndex);


                default:

                    return null; //Ne devrait jamais arriver

            }
        }

        public void add (String nclients, String nboissons, String netats) {
        	System.out.println("Ajout dans le bar");
        	
            clients.add(nclients);
            boissons.add(nboissons);
            etats.add(netats);
            
            fireTableRowsInserted(clients.size() -1, clients.size()-1);
        }
        
        public void remove (String nclients) {
        	int i = 0;
        	for (String nom : clients) {
        		if (nom.compareTo(nclients) == 0) {
        			break;
        		}
        		i++;
        	}
        	if (i<clients.size()) {
        		clients.remove(i);
        		boissons.remove(i);
        		etats.remove(i);
        		fireTableRowsDeleted(clients.size(),clients.size());
        	}
        }

        public void change(int rowIndex) {
            //!#@@#! Ecrire la fonction pour changer le i eme état à la ligne i qui est donc etats.get(i)
        	
        	Commande.prepare(clients.get(rowIndex));
        }

        /*public void supprimeCommande(int rowIndex) {

            clients.remove(rowIndex);
            boissons.remove(rowIndex);
            etats.remove(rowIndex);


            fireTableRowsDeleted(rowIndex, rowIndex);
        }*/
    }



