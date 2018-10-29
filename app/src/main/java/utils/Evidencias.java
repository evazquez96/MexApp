package utils;

/**
 * Created by ADGARCIA on 06/03/2018.
 */

public class Evidencias {
    public String getIdEvidencia() {
        return idEvidencia;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setIdEvidencia(String idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String idEvidencia;
    private String base64Image;
    private String fileExtension;
    private String fileName;


    public Evidencias() {


    }
}