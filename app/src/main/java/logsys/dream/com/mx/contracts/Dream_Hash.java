package logsys.dream.com.mx.contracts;

import java.util.HashMap;

/**
 * Created by JUAHERNA on 2/9/2017.
 */

public class Dream_Hash {
    public HashMap<Integer, Dream_Group> getDreams() {
        return Dreams;
    }

    public HashMap<Integer, Dream_Group> getDreams1() {
        HashMap<Integer, Dream_Group> result = new HashMap<Integer, Dream_Group>();
        Dream_Group group = null;
        for (int i: Dreams.keySet()) {

             group = Dreams.get(i);
            if(group.getRegistros()>0)
                result.put(i,group);
        }

        this.Dreams = result;
        return result;
    }

    public void setDreams(HashMap<Integer, Dream_Group> dreams) {
        Dreams = dreams;
    }

    private HashMap<Integer,Dream_Group> Dreams;

    public  Dream_Hash()
    {
        //String[] dias = new String[] {"Jueves","Viernes","Sabado"};
        String[] dias = new String[] {"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
        this.Dreams = new HashMap<>();

        //int tmp =4;
        for(int i=0;i<dias.length;i++)
            this.Dreams.put(i,new Dream_Group(dias[i],i,""));
    }

    public void add_dream(Dream_Record dream, String strFecha)
    {
        try {

            Dream_Group group = this.Dreams.get(dream.getNum_dia());
            group.setFecha(strFecha);
            group.add_Dream(dream);
        }catch (Exception e)
        {

        }
    }


}
