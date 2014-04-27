package graphique;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


/**
 * Created by Arturo on 26/04/2014.
 */
public class BarModel extends AbstractTableModel {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private ArrayList<String> clients;
        private ArrayList<String> boissons;
        private ArrayList<String> etats;


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

        public void actualiser(ArrayList<String> nclients, ArrayList<String> nboissons, ArrayList<String> netats) {
            clients = nclients;
            boissons = nboissons;
            etats = netats;
        }

        public void change(int rowIndex) {
            //!#@@#! Ecrire la fonction pour changer le i eme état à la ligne i qui est donc etats.get(i)
        }

        /*public void supprimeCommande(int rowIndex) {

            clients.remove(rowIndex);
            boissons.remove(rowIndex);
            etats.remove(rowIndex);


            fireTableRowsDeleted(rowIndex, rowIndex);
        }*/
    }



