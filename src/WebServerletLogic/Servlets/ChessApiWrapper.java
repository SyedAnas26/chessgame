package WebServerletLogic.Servlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChessApiWrapper {

    public ChessApiWrapper() throws IOException {
//        OkHttpClient client = new OkHttpClient().;
//                .build();
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        Request request = new Request.Builder()
//                .url("http://chess-api-chess.herokuapp.com/api/v1/highscores/add")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//        Response response = client.newCall(request).execute();
    }

    enum RequestType
    {
        POST, PUT, GET, DELETE
    }

    public static String callURL(String urlString, RequestType requestType)
    {
        StringBuffer stringBuffer= new StringBuffer();
        try{
            URL url =new URL(urlString);
            HttpURLConnection urlcon= (HttpURLConnection) url.openConnection();
            if(requestType == RequestType.POST) {
                urlcon.setDoOutput(true);
            }

            InputStream stream= urlcon.getInputStream();

            int i;
            while((i=stream.read())!=-1){
                stringBuffer.append((char)i);
            }
        }catch(Exception e)
        {

        }

        return stringBuffer.toString();
    }
}



