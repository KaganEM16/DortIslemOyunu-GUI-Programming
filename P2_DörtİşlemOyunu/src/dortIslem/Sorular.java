package dortIslem;

public class Sorular {
    int kod;
    int sayi1;
    int sayi2;
    
    public Sorular(int kod, int sayi1, int sayi2) {
        if (kod < 1 || kod > 4) {
            throw new IllegalArgumentException("Kod 1 ile 4 arasında olmalıdır.");
        }
        this.kod = kod;
        this.sayi1 = sayi1;
        this.sayi2 = sayi2;
    }

    public String soru() {
        String soru;
        switch (kod) {
            case 1:
                soru = "" + sayi1 + " + " + sayi2 + " = ? ";
                break;
            case 2:
                soru = "" + sayi1 + " - " + sayi2 + " = ? ";
                break;
            case 3:
                soru = "" + sayi1 + " * " + sayi2 + " = ? ";
                break;
            case 4:
                soru = "" + sayi1 + " / " + sayi2 + " = ? ";
                break;
            default:
                soru = "Geçersiz işlem";
                break;
        }
        return soru;
    }

    public double cevap() {
        double cevap;
        switch (kod) {
            case 1:
                cevap = sayi1 + sayi2;
                break;
            case 2:
                cevap = sayi1 - sayi2;
                break;
            case 3:
                cevap = sayi1 * sayi2;
                break;
            case 4:
                if (sayi2 == 0) {
                    throw new ArithmeticException("Sıfıra bölme hatası");
                }
                cevap = (double) sayi1 / sayi2;
                break;
            default:
                throw new IllegalArgumentException("Geçersiz kod");
        }
        return cevap;
    }    
}
