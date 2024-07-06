package dortIslem;

import javax.swing.*;

public class SureAyarla implements Runnable {
    JLabel sure;    
    int verilenSure;
    int saniye = 1000;
    int k=0;
    
    public SureAyarla(JLabel sure, int verilenSure) {
        this.sure = sure;
        this.verilenSure = verilenSure;                
    }

    @Override
    public void run() {
        int sayi = verilenSure;
        Oyun.basildi = false;
        while (sayi > 0) {
            sayi--;
            sure.setText("Kalan Süreniz: " + sayi);
            try {
                Thread.sleep(saniye);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if(Oyun.basildi && sayi != 0) {
            	k++;
            	break;
            }else if(Oyun.basildi && sayi == 0) {
            	break;
            }
        }
        if(sayi == 0 && k==0) {
        	sure.setText("Süre sona erdi :(");
        	Oyun.sureBitti = true;
        }else {
        	sayi=0;
        	sure.setText("Kalan Süreniz: " + sayi);
        	Oyun.sureBitti = false;
        }
    }
}

