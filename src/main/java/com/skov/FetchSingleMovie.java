package com.skov;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.TreeSet;

import org.jsoup.Jsoup;

/**
 * Place description here.
 *
 * @author ALSK@nykredit.dk
 */

public class FetchSingleMovie {

    public static void main(String[] args) {
        TreeSet<String> sortedMovies = new TreeSet<String>();
        getSingleMovie(sortedMovies, "Hundemand");
    }

    public static void getSingleMovie(TreeSet<String> sortedMovies, String film) {
        String imdbUrl = null;
        try {
            
            //imdbUrl = "https://www.imdb.com/search/title?title=" + URLEncoder.encode(film, "UTF-8");
            imdbUrl = "https://kino.dk/film/" + URLEncoder.encode(film, "UTF-8");

            String imdbHtml = Jsoup.connect(imdbUrl).ignoreContentType(true).execute().body();

            int beginIndex = imdbHtml.indexOf("ratings-imdb-rating\" name=\"ir\" data-value=\"");

            String score = " N/A";
            String genre = "";
            String votes = "";
            if (beginIndex != -1) {
                beginIndex = beginIndex + 43;
                String subString = imdbHtml.substring(beginIndex);
                score = subString.substring(0, subString.indexOf("\""));
                genre = " (" + Jsoup.connect(imdbUrl).get().getElementsByClass("genre").get(0).html() + ", ";
                votes = "votes: " + Jsoup.connect(imdbUrl).get().getElementsByClass("sort-num_votes-visible").get(0).select("span").get(1).html() + ")";
            }

            String movieHit = score + " <a href='" + imdbUrl + "'>link</a> - <b>" + film + "</b>" + genre + votes;
            sortedMovies.add(movieHit);
            System.out.println(movieHit);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
