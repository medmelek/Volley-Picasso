package info.androidhive.volleyjson.app;

/**
 * Created by Med Melek on 27/11/2016.
 */

public class modeltmp {
    String valeurtmp;
    String datetmp ;
    String imagetmp ;

    public modeltmp(String valeuttmp, String imagetmp, String datetmp) {
        this.valeurtmp = valeuttmp;
        this.imagetmp = imagetmp;
        this.datetmp = datetmp;
    }

    public modeltmp() {

    }

    public String getValeurtmp() {
        return valeurtmp;
    }

    public void setValeurtmp(String valeuttmp) {
        this.valeurtmp = valeuttmp;
    }

    public String getImagetmp() {
        return imagetmp;
    }

    public void setImagetmp(String imagetmp) {
        this.imagetmp = imagetmp;
    }

    public String getDatetmp() {
        return datetmp;
    }

    public void setDatetmp(String datetmp) {
        this.datetmp = datetmp;
    }
}
