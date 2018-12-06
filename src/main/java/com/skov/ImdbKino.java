package com.skov;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Place description here.
 *
 * @author ALSK@nykredit.dk
 */

public class ImdbKino {

    static TreeSet<String> sortedMovies = new TreeSet<String>();

    public static void main(String[] args) throws IOException {
        System.out.println(execute());
    }

    public static String execute() throws IOException {

        String kinoUrl = "https://www.kino.dk/booking/flow/movie-step-1";
        System.out.println("welcome. I will collect movies from " + kinoUrl);

        Elements alleFilm = Jsoup.connect(kinoUrl).get().select("optgroup").get(0).children();
        for (Element film : alleFilm) {
            String imdbUrl = "https://www.imdb.com/search/title?title=" + URLEncoder.encode(film.html().trim(), "UTF-8");

            String imdbHtml = Jsoup.connect(imdbUrl).ignoreContentType(true).execute().body();

            int beginIndex = imdbHtml.indexOf("ratings-imdb-rating\" name=\"ir\" data-value=\"");

            String score = "N/A";
            if (beginIndex != -1) {
                beginIndex = beginIndex + 43;
                String subString = imdbHtml.substring(beginIndex);
                score = subString.substring(0, subString.indexOf("\""));
            }

            String movieHit = score + " - " + film.html() + " - " + imdbUrl;
            sortedMovies.add(movieHit);
            System.out.println(movieHit);

        }


        System.out.println("");
        StringBuffer sb = new StringBuffer();
        for (String sortedMovie : sortedMovies) {
            System.out.println(sortedMovie);
            sb.append(sortedMovie + " <br>");
        }

        System.out.println("bye.");
        return sb.toString();

    }
}
