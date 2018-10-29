package logsys.dream.com.mx.contracts;



/**
 * Created by JUAHERNA on 2/21/2017.
 */

public class Dream_Dashboard {



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getP_sueno_24() {
        return p_sueno_24;
    }

    public void setP_sueno_24(long p_sueno_24) {
        this.p_sueno_24 = p_sueno_24;
    }

    public long getP_seno_sem() {
        return p_seno_sem;
    }

    public void setP_seno_sem(long p_seno_sem) {
        this.p_seno_sem = p_seno_sem;
    }

    private String nombre;

    private long p_sueno_24;

    private long p_sueno_48;

    private  long p_seno_sem;



    private dream_indicador i_ultimas_24;

    private dream_indicador i_ultimas_48;

    private dream_indicador i_semanal;

    public String getUltimo_sueno() {
        return ultimo_sueno;
    }

    public void setUltimo_sueno(String ultimo_sueno) {
        this.ultimo_sueno = ultimo_sueno;
    }

    public String getUltimas_horas() {
        return ultimas_horas;
    }

    public void setUltimas_horas(String ultimas_horas) {
        this.ultimas_horas = ultimas_horas;
    }

    private String ultimo_sueno;

    private String ultimas_horas;


    public float getCalificacion()
    {
        float result = i_semanal.obtenerCalificacion() + i_ultimas_24.obtenerCalificacion() + i_ultimas_48.obtenerCalificacion();
        return  result / 3;
    }



    public dream_indicador getI_ultimas_24() {
        return i_ultimas_24;
    }

    public void setI_ultimas_24(dream_indicador i_ultimas_24) {
        this.i_ultimas_24 = i_ultimas_24;
    }

    public dream_indicador getI_ultimas_48() {
        return i_ultimas_48;
    }

    public void setI_ultimas_48(dream_indicador i_ultimas_48) {
        this.i_ultimas_48 = i_ultimas_48;
    }

    public dream_indicador getI_semanal() {
        return i_semanal;
    }

    public void setI_semanal(dream_indicador i_semanal) {
        this.i_semanal = i_semanal;
    }

    public long getP_sueno_48() {
        return p_sueno_48;
    }

    public void setP_sueno_48(long p_sueno_48) {
        this.p_sueno_48 = p_sueno_48;
    }
}
