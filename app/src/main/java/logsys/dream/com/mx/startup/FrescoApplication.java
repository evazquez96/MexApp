package logsys.dream.com.mx.startup;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

import logsys.dream.com.mx.cache.ImagePipelineConfigFactory;
import logsys.dream.com.mx.contracts.Usuario;

/**
 * Created by JUAHERNA on 2/14/2018.
 */

public class FrescoApplication extends Application {

    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("FrescoApplication",":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: onCreate");

        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));
    }

}
