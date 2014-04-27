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
 
