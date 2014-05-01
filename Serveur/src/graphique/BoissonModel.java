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

import admin.Boisson;
import admin.Menu;
import com.example.bump.actions.BumpFriend;

import javax.swing.table.AbstractTableModel;

/**
 * Created by Arturo on 01/05/2014.
 */
public class BoissonModel extends AbstractTableModel {


    private final String[] entetes = {"Nom", "Prix", "Degré", "Rouge", "Vert", "Bleu"};


    public BoissonModel() {

        super();


    }


    public int getRowCount() {

        return Menu.getMenu().size();
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

                return Menu.getMenu().get(rowIndex).getNom();

            case 1:

                return Menu.getMenu().get(rowIndex).getPrix();

            case 2:

                return Menu.getMenu().get(rowIndex).getDegre();

            case 3:

                return Menu.getMenu().get(rowIndex).getCode().getRouge();

            case 4:

                return Menu.getMenu().get(rowIndex).getCode().getVert();

            case 5:

                return Menu.getMenu().get(rowIndex).getCode().getBleu();


            default:

                return null; //Ne devrait jamais arriver

        }
    }

    public void addBoisson() {

        fireTableRowsInserted(Menu.getMenu().size() -1, Menu.getMenu().size() -1);
    }


    public void removeBoisson(int rowIndex) {

        Menu.retireBoisson(Menu.getMenu().get(rowIndex));

        fireTableRowsDeleted(rowIndex, rowIndex);
    }

}
