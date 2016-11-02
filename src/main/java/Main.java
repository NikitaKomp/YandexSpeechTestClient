import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by User on 27.10.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://asr.yandex.net/asr_xml?uuid=01ae13cb744628b58fb536d496daa1e6&key=4b7f1185-2243-41b7-a263-df2e7d211282&topic=queries");

        // Преобразуем файл в байты
        File audioFile = new File("C:\\BLE\\speech.wav");
        InputStream targetStream = new FileInputStream(audioFile);
        byte[] bytes =  IOUtils.toByteArray(targetStream);
        
        // Добавление файла к телу POST запроса
        httpPost.setEntity(new ByteArrayEntity(bytes));

        // Установим тип содержимого
        String ctype = URLConnection.guessContentTypeFromName(audioFile.getName());
        httpPost.setHeader(HTTP.CONTENT_TYPE, ctype);

        // Отправим POST запрос
        HttpResponse response = httpClient.execute(httpPost);
        //Взять файл wav и сгладить и записать в другой
        // Выведем ответ
        BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder total = new StringBuilder();
        String line = null;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        r.close();
        httpPost.releaseConnection();
        System.out.println(total);
    }
}
