package com.giunta.jack.blist;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jack on 6/19/2017.
 * Helper methods related to requesting and receiving book data from Google.
 */

public final class QueryUtils {
    public static final String LOG_TAG = "QueryUtils";
    private static final String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String maxResults = "&maxResults=10";
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils(){
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch(MalformedURLException e){
            Log.e(LOG_TAG, "Problem building URL object", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, return early
        if(url == null){
            Log.e(LOG_TAG, "Null URL Object Passed to makeHttpRequest()");
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG,"Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results");
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }

        return jsonResponse;

    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Book> parseJson(String bookJSON){
        // If the JSON string is empty or null, then return early.
        if(TextUtils.isEmpty(bookJSON)){
            return null;
        }
        // Create an empty ArrayList that we can start adding Books to
        List<Book> books = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject booksJOBJ = new JSONObject(bookJSON);

            // Extract the JSONArray associated with the key called "items",
            // which represents a list of items (or books).
            JSONArray booksJsonArray = booksJOBJ.getJSONArray("items");

            // For each book in the bookArray, create an {@link Book} object
            for(int i = 0; i < booksJsonArray.length();i++){

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentBook = booksJsonArray.getJSONObject(i);

                // For a given book, extract the JSONObject associated with the
                // key called "volumeInfo", which represents a list of all properties
                // for that book.
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                // Extract the value for the key called "title"
                String title = volumeInfo.getString("title");

                // Extract the value for the key called "authors"
                JSONArray authorsJsonArray = volumeInfo.getJSONArray("authors");
                List<String> authorsList = new ArrayList<>();
                for(int j=0; j < authorsJsonArray.length(); j++){
                    authorsList.add(authorsJsonArray.get(j).toString());
                }
                // Extract the value for the key called "publishedDate"
                String dateString = volumeInfo.getString("publishedDate");
                Date date = convertDate(dateString);

                // Extract the value for the key called "averageRating"
                //String averageRatingString = volumeInfo.getString("averageRating");
                Double averageRating = null;
                if(volumeInfo.has("averageRating"))
                    averageRating = volumeInfo.getDouble("averageRating");
                // Create a new {@link Book} object
                Book book = new Book(title,averageRating, date,authorsList);

                // Add the new {@link Earthquake} to the list of earthquakes.
                books.add(book);
            }

            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
        } catch (JSONException e){
            Log.e(LOG_TAG, "Could not parse JSON", e);
        }

        // Return the list of earthquakes
        return books;
    }

    private static Date convertDate(String dateString){

        if(TextUtils.isEmpty(dateString))
            return null;

        SimpleDateFormat formatter;
        Date date = null;

        if(dateString.contains("-"))
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        else
            formatter = new SimpleDateFormat("yyyy");

        try {
            date = formatter.parse(dateString);
        }catch (ParseException e){
            Log.e(LOG_TAG, "Couldn't Format Date", e);
        }

        return date;
    }

    /**
     * Query the GoogleBooks dataset and return a list of {@link Book} objects.
     */
    public static List<Book> fetchBookData(String requestKeyword){

        URL url = createUrl(baseUrl + requestKeyword + maxResults);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making HTTP request", e);
        }

        List<Book> books = parseJson(jsonResponse);

        return books;

    }
}
