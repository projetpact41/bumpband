package admin;

import com.example.bump.actions.Transmissible;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Arturo on 07/04/2014.
 */
public class Menu implements Transmissible{
    private static ArrayList<Boisson> menu ;

    public static void ajouteBoisson(Boisson b){
        menu.add(b);
    }

    private static void retireBoisson(Boisson b){
        menu.remove(b);
    }

    @Override
    public byte[] toBytes() {
        int n=menu.size();
        int taille = 0;

        for (int i = 0; i<n; i++) {
            taille += 5+menu.get(i).getNom().length()+1;
        }
        byte[] b = new byte[taille];
        int compteur = 1;
        b[0]=7;
        for (int i=0;i<n;i++){
            b[compteur]=menu.get(i).getPrix();
            b[compteur+1]=menu.get(i).getCode().getRouge();
            b[compteur+2]=menu.get(i).getCode().getVert();
            b[compteur+3]=menu.get(i).getCode().getBleu();
            b[compteur+4]=menu.get(i).getDegre();
            compteur += 5;
            String nom = menu.get(i).getNom();
            for (int j = 0; j< nom.length(); j++) {
                b[compteur] = (byte) nom.charAt(j);
                compteur++;
            }
            b[compteur] = (byte) '|';
        }
        return b;
    }

	@Override
	public Transmissible execute(InetAddress address) {
		// TODO Auto-generated method stub
		return null;
	}
}
