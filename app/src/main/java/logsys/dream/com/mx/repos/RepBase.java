package logsys.dream.com.mx.repos;

import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.startup.FrescoApplication;

/**
 * Created by paqti on 09/03/2017.
 */

public class RepBase {

    final FrescoApplication globalVariable = (FrescoApplication) LoginActivity.getAppContext();

}
