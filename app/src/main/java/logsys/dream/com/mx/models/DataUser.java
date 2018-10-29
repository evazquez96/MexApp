package logsys.dream.com.mx.models;

import android.graphics.Bitmap;

public class DataUser {

    public Bitmap hojaRosa;
    public Bitmap cartaPorte;
    public Bitmap licencia;
    public Bitmap SUA;

    public DataUser() {

    }

    public Bitmap getHojaRosa() {return hojaRosa;}
    public void setHojaRosa(Bitmap hojaRosas){this.hojaRosa = hojaRosas;}

    public Bitmap getCartaPorte() {return cartaPorte;}
    public void setCartaPorte(Bitmap cartaPortes) {this.cartaPorte = cartaPortes;}

    public Bitmap getLicencia() {return  licencia;}
    public void setLicencia(Bitmap licencias) {this.licencia = licencias;}

    public Bitmap getSUA() {return SUA;}
    public void setSUA(Bitmap SUAS) {this.SUA = SUAS;}

}