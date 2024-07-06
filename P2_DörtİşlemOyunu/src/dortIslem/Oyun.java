package dortIslem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Oyun extends JFrame implements ActionListener {
    Runnable[] gorevler;
    JPanel[] paneller;
    JTextField[] alanlar;
    JLabel[] isimler = new JLabel[2];
    JLabel isimEtiketi;
    JButton[] butonlar;
    JButton baslaButonu;
    JLabel[] sorular;
    double cevap;
    JLabel[] sureler;
    JLabel[] canlar;
    int can[] = {100, 100};
    final int sure = 7;
    static boolean basildi = false;
    static boolean sureBitti = false;
    int sayac = 0;
    Color[] renkler = {Color.CYAN, Color.ORANGE, Color.BLACK};

    public Oyun() {
//    	Pencerenin temel hatlarıyla ayarlanması:
        super("Dört İşlem Oyunu");
        int en = 600;
        int boy = 600;
        this.setSize(en, boy);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        
//      Panellerin ayarlanması  
        paneller = new JPanel[4];
        paneller[0] = new JPanel();
        paneller[0].setBackground(Color.BLUE);
        paneller[0].setSize(new Dimension(en, boy));
        paneller[0].setLayout(new GridLayout(5, 1));
        this.add(paneller[0]);
        for (int i = 1; i < paneller.length - 1; i++) {
            paneller[i] = new JPanel();
            paneller[i].setBackground(renkler[i - 1]);
            paneller[i].setBounds((i - 1) * en / 2, 0, en / 2, boy);
            paneller[i].setLayout(new GridLayout(7, 1));
        }
        
//		İsim etiketinin ayarlanması
        isimEtiketi = new JLabel("1. OYUNCUNUN İSMİNİ GİRİNİZ:");
        etiketAyarla(isimEtiketi, 25, Color.WHITE);
        paneller[0].add(isimEtiketi);

//      Soru etiketlerinin ayarlanması  
        sorular = new JLabel[2];
        for (int i = 0; i < sorular.length; i++) {
            sorular[i] = new JLabel("             ");
            etiketAyarla(sorular[i], 16, Color.BLACK);
        }

//		İsmin girileceği ve soru cevaplarının verileceği alanların ayarlanması        
        alanlar = new JTextField[3];
        for (int i = 0; i < alanlar.length; i++) {
            alanlar[i] = new JTextField();
            if (i == 0)
                alanlar[i].setPreferredSize(new Dimension(en / 4, boy / 4));
            else
                alanlar[i].setPreferredSize(new Dimension(en / 6, boy / 6));
            alanlar[i].setBackground(Color.BLACK);
            alanlar[i].setForeground(Color.WHITE);
        }
        paneller[0].add(alanlar[0]);

//		Surelerin ayarlanması
        sureler = new JLabel[2];
        for (int i = 0; i < sureler.length; i++) {
            sureler[i] = new JLabel("Kalan Süreniz: " + sure);
            etiketAyarla(sureler[i], 20, Color.BLACK);
        }
        
//		Canların ayarlanması        
        canlar = new JLabel[2];
        for (int i = 0; i < canlar.length; i++) {
            canlar[i] = new JLabel("Kalan Canınız: " + can[i]);
            etiketAyarla(canlar[i], 20, Color.BLACK);
        }

//		Butonların ayarlanması
        butonlar = new JButton[3];
        for (int i = 0; i < butonlar.length; i++) {
            butonlar[i] = new JButton("Tamam");
            butonlar[i].addActionListener(this);
        }
        paneller[0].add(butonlar[0]);
        
//		Süre güncellemesi için görevlerin ayarlanması        
        gorevler = new Runnable[2];
        gorevler[0] = new SureAyarla(sureler[0], sure);
        gorevler[1] = new SureAyarla(sureler[1], sure);        

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object nesne = e.getSource();

        if (nesne instanceof JButton) {
            JButton basilanButon = (JButton) nesne;            
            Thread islem1 = new Thread(gorevler[0]);
            Thread islem2 = new Thread(gorevler[1]);
            if (basilanButon == butonlar[0]) {
                if (sayac == 0) {
                    isimler[0] = new JLabel(alanlar[0].getText());
                    etiketAyarla(isimler[0], 16, Color.BLACK);
                    paneller[1].add(isimler[0]);

                    alanlar[0].setText("");
                    isimEtiketi.setText("2. OYUNCUNUN İSMİNİ GİRİNİZ:");
                    sayac++;
                } else {
                    isimler[1] = new JLabel(alanlar[0].getText());
                    etiketAyarla(isimler[1], 16, Color.BLACK);
                    paneller[2].add(isimler[1]);

                    paneller[0].setVisible(false);
                    panelAyarla();
                    this.add(paneller[3]);
                }
            } else {
            	int kod = (int) (Math.random() * 4) + 1; // Kod 1-4 arasında olmalı
                int sayi1 = (int) (Math.random() * 10);
                int sayi2 = 1 + (int) (Math.random() * 9);                
                
            	if (basilanButon == baslaButonu) {
                    paneller[3].setVisible(false);

                    sorular[0].setText(new Sorular(kod, sayi1, sayi2).soru());
                    cevap = new Sorular(kod, sayi1, sayi2).cevap();
                    paneller[1].add(sorular[0]);
                    paneller[1].add(alanlar[1]);
                    paneller[1].add(butonlar[1]);
                    paneller[1].add(sureler[0]);
                    paneller[1].add(canlar[0]);
                    this.add(paneller[1]);

                    paneller[2].add(sorular[1]);
                    paneller[2].add(alanlar[2]);
                    paneller[2].add(butonlar[2]);
                    paneller[2].add(sureler[1]);
                    paneller[2].add(canlar[1]);
                    this.add(paneller[2]);
                    
                    islem1.start();
                } else if(basilanButon == butonlar[1]) { 
                	basildi=true;
                	double verilenCevap;
                	
                	try {
                		verilenCevap = Double.valueOf(alanlar[1].getText());
                	}catch(Exception ex) {
                		verilenCevap = 100;
                	}
                	
                	if(!sureBitti && verilenCevap == cevap) {
                		
                	}else {            		
                		can[0] -= 20;
                		canlar[0].setText("Kalan Canınız: " + can[0]);
                		if(can[0]==0) {
                			paneller[1].setBackground(Color.RED);
                			paneller[2].setBackground(Color.GREEN);                			
                			sorular[1].setVisible(false);
                			butonlar[1].setVisible(false);
                			butonlar[2].setVisible(false);
                			alanlar[2].setVisible(false);
                			sureler[0].setText("Oyun Bitti !");
                			sureler[1].setText("Oyun Bitti !");                			
                		}
                	}
                	if(can[0]!=0) {
	                	sureBitti = false;
	                	sorular[0].setText("             ");
	                	alanlar[1].setText("");
	                	sureler[0] = new JLabel("Kalan Süreniz: " + sure);
	            		sorular[1].setText(new Sorular(kod, sayi1, sayi2).soru());
	            		cevap = new Sorular(kod, sayi1, sayi2).cevap();
	            		islem2.start();
                	}
                } else {            	
                	basildi=true;                	
                	double verilenCevap;
                	
                	try {
                		verilenCevap = Double.valueOf(alanlar[2].getText());
                	}catch(Exception ex) {
                		verilenCevap = 100;
                	}
                	
                	if(!sureBitti && verilenCevap == cevap) {
                		
                	}else {            		
                		can[1] -= 20;
                		canlar[1].setText("Kalan Canınız: " + can[1]);
                		if(can[1]==0) {
                			paneller[2].setBackground(Color.RED);
                			paneller[1].setBackground(Color.GREEN);
                			sorular[0].setVisible(false);                			
                			butonlar[1].setVisible(false);
                			butonlar[2].setVisible(false);
                			alanlar[1].setVisible(false);
                			sureler[0].setText("Oyun Bitti !");
                			sureler[1].setText("Oyun Bitti !");
                		}
                	}
                	if(can[1]!=0) {
	                	sureBitti = false;
	                	sorular[1].setText("             ");
	                	alanlar[2].setText("");
	                	sureler[1] = new JLabel("Kalan Süreniz: " + sure);
	            		sorular[0].setText(new Sorular(kod, sayi1, sayi2).soru()); 
	            		cevap = new Sorular(kod, sayi1, sayi2).cevap();
	            		islem1.start();
                	}
                }
            }
        }
    }

    public void panelAyarla() {
        // paneller[3] ayarlaması
        paneller[3] = new JPanel();
        paneller[3].setBackground(Color.RED);
        paneller[3].setSize(new Dimension(600, 600));
        paneller[3].setLayout(new GridLayout(4, 1));
        JLabel karsilama = new JLabel("Oyunumuza Hoşgeldiniz :)");
        etiketAyarla(karsilama, 37, Color.WHITE);
        paneller[3].add(karsilama);
        JLabel hazirlayan = new JLabel("Hazırlayan: Kağan Emre Meral");
        etiketAyarla(hazirlayan, 16, Color.WHITE);
        paneller[3].add(hazirlayan);
        JLabel uyari = new JLabel("Başlamak için aşağıdaki butona basınız.");
        etiketAyarla(uyari, 16, Color.WHITE);
        paneller[3].add(uyari);
        baslaButonu = new JButton("Başla");
        baslaButonu.addActionListener(this);
        paneller[3].add(baslaButonu);
    }

    public void etiketAyarla(JLabel etiket, int boyut, Color renk) {
        etiket.setForeground(renk);
        etiket.setFont(new Font("Verdana", Font.BOLD, boyut));
        etiket.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
