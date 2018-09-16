package pw.slcoolj.poster;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Poster
 * A very basic class for sending POST data to a URL.
 * @author sl
 */
public class Poster {
    private final String url, parameters, proxyHost, proxyPort;
    private List<String> replyList = new ArrayList<>();
    private final boolean proxy;

    public Poster(String url, String parameters) {
        this.url = url;
        this.parameters = parameters;
        this.proxy = false;
        this.proxyHost = "";
        this.proxyPort = "";
    }

    public Poster(String url, String parameters, String proxyHost, String proxyPort) {
        this.url = url;
        this.parameters = parameters;
        this.proxy = true;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }

    public String getURL() {
        return url;
    }

    public String getParameters() {
        return parameters;
    }

    public boolean isUsingProxy() {
        return proxy;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public List<String> getReply() {
        return replyList;
    }

    public void sendPost() {
        if (proxy) {
            System.setProperty("http.proxyHost", proxyHost);
            System.setProperty("http.proxyPort", proxyPort);
        }
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(parameters.getBytes().length));
            connection.setUseCaches(false);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(parameters);
            outputStream.flush();
            outputStream.close();
            connection.disconnect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                replyList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
