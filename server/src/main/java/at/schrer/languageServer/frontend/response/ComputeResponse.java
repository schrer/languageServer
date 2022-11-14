package at.schrer.languageServer.frontend.response;

public class ComputeResponse {
    private String lang;
    private String result;



    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
