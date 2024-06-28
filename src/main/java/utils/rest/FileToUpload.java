package utils.rest;


public class FileToUpload {

    private String multipartName;
    private String filePath;


    public FileToUpload() {
    }

    public FileToUpload(String multipartName, String filePath) {
        this.multipartName = multipartName;
        this.filePath = filePath;
    }


    public String getMultipartName() {
        return multipartName;
    }

    public void setMultipartName(String multipartName) {
        this.multipartName = multipartName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
