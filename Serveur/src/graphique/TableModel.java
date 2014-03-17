package graphique;


import javax.swing.table.AbstractTableModel;

import com.example.bump.actions.BFList;
import com.example.bump.actions.BumpFriend;

import java.net.InetAddress;
import java.net.UnknownHostException;
 
 
public class TableModel extends AbstractTableModel {
 /**
  *
  */
 private static final long serialVersionUID = 1L;
 
 private BFList amis;//modifiable
  
 
    private final String[] entetes = {"InetAdress", "Name"};
 
 
    public TableModel() {
 
        super();
 
        try {
        	amis = new BFList("BFList.txt") ;
 	 	 amis.ajoutBF(new BumpFriend("Arturo",InetAddress.getLocalHost()));//getLocalHost juste pour tester
 	 	 amis.ajoutBF(new BumpFriend("Julien",InetAddress.getLocalHost()));
 
        } catch (UnknownHostException e) {
 	 	 // TODO Auto-generated catch block
 	 	 e.printStackTrace();
 	 }
}
 
 
    public int getRowCount() {
 
        return amis.getBFliste().size();
}
 
 
    public int getColumnCount() {
 
        return entetes.length;
}
 
 
    public String getColumnName(int columnIndex) {
 
        return entetes[columnIndex];
}
 
 
    public Object getValueAt(int rowIndex, int columnIndex) {
 
        switch(columnIndex){
 
            case 0:
 
                return amis.getBFliste().get(rowIndex).getAdresse().getCanonicalHostName();
 
            case 1:
 
                return amis.getBFliste().get(rowIndex).getName();
 
 
            default:
 
                return null; //Ne devrait jamais arriver
 
        }
}
 
 
    public void addAmi(BumpFriend ami) {
 
        amis.ajoutBF(ami);  
 
 
        fireTableRowsInserted(amis.getBFliste().size() -1, amis.getBFliste().size() -1);
}
 
 
    public void removeAmi(int rowIndex) {
 
        amis.effacerBF(amis.getBFliste().get(rowIndex));
 
 
        fireTableRowsDeleted(rowIndex, rowIndex);
}
}
 
