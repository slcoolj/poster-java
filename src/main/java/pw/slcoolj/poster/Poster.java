package pw.slcoolj.poster;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Poster
 * A very basic class for sending POST data to a URL.
 * @author sl
 */
public class Poster {
    private final URL url;
    private final String parameters, proxyHost, proxyPort;
    private List<String> replyList;

    public Poster(URL url, String parameters, String proxyHost, String proxyPort) {
        this.url = url;
        this.parameters = parameters;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }

    public Poster(URL url, String parameters) {
        this(url, parameters, null, null);
    }

    public Poster(String url, String parameters) throws MalformedURLException {
        this(new URL(url), parameters, null, null);
    }

    public Poster(String url, String parameters, String proxyHost, String proxyPort) throws MalformedURLException {
        this(new URL(url), parameters, proxyHost, proxyPort);
    }

    public URL getURL() {
        return url;
    }

    public String getParameters() {
        return parameters;
    }

    public boolean isUsingProxy() {
        return proxyHost != null || proxyPort != null;
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
        if(replyList != null)
            throw new IllegalStateException("Post has already been sent.");

        if (proxyHost != null) {
            System.setProperty("http.proxyHost", proxyHost);
        }

        if (proxyPort != null) {
            System.setProperty("http.proxyPort", proxyPort);
        }

        try {
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
            outputStream.close();
            connection.disconnect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            replyList = new ArrayList<>();
            while((line = reader.readLine()) != null) {
                replyList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
