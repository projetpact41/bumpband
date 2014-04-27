package graphique;
/**


 Created by Arturo on 27/04/2014.
 */


import javax.swing.SwingUtilities;

public class MainTest {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                FlashMobFenetre fenetre = new FlashMobFenetre();
                fenetre.setVisible(true);
            }
        });
    }
}