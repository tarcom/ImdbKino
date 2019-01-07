package com.skov;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImdbKinoAalborgToday {

    TreeSet<String> sortedMovies = new TreeSet<String>();

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println(new ImdbKinoAalborgToday().execute());
    }

    public String execute() throws InterruptedException, IOException {

        StringBuffer htmlOutputStringBuffer = new StringBuffer();

        String kinoUrl = "https://www.kino.dk/booking/flow/cinema-step-2?city=72";
        htmlOutputStringBuffer.append("I will collect movies from " + kinoUrl + "<br>\n<br>\n");
        long startTime = System.currentTimeMillis();

        Elements alleFilm = Jsoup.connect(kinoUrl).get().getElementsByClass("booking-flow-movie grid3  current");

        ExecutorService es = Executors.newCachedThreadPool();
        for (final Element film : alleFilm) {
            String movieTitle = film.getElementsByClass("movies-list-inner-wrap").select("h2").html();

            es.execute(new Thread(movieTitle) {
                public void run() {
                    System.out.println("Thread Running... name=" + getName());
                    FetchSingleMovie.getSingleMovie(sortedMovies, movieTitle);
                }
            });
        }

        es.shutdown();
        boolean finshed = es.awaitTermination(1, TimeUnit.MINUTES);
        // all tasks have finished or the time has been reached.

        System.out.println("");
        for (String sortedMovie : sortedMovies.descendingSet()) {
            System.out.println(sortedMovie);
            htmlOutputStringBuffer.append(sortedMovie + " <br>\n");
        }

        htmlOutputStringBuffer.append("<br>\nAll movies have been collected.");
        htmlOutputStringBuffer.append(
            "<br>\nI started out with " + alleFilm.size() + " film, and have collected IMDB info from " + sortedMovies.size() +
                " movies. <br>\n");
        htmlOutputStringBuffer.append("Time used: " + (System.currentTimeMillis() - startTime) / 1000l + " seconds.<br>\n");
        return htmlOutputStringBuffer.toString();
    }

//    private void getSingleMovie(String film) {
//        String imdbUrl = null;
//        try {
//            imdbUrl = "https://www.imdb.com/search/title?title=" + URLEncoder.encode(film, "UTF-8");
//
//            String imdbHtml = Jsoup.connect(imdbUrl).ignoreContentType(true).execute().body();
//
//            int beginIndex = imdbHtml.indexOf("ratings-imdb-rating\" name=\"ir\" data-value=\"");
//
//            String score = " N/A";
//            if (beginIndex != -1) {
//                beginIndex = beginIndex + 43;
//                String subString = imdbHtml.substring(beginIndex);
//                score = subString.substring(0, subString.indexOf("\""));
//            }
//
//            String movieHit = score + " <a href='" + imdbUrl + "'>link</a> - " + film;
//            sortedMovies.add(movieHit);
//            System.out.println(movieHit);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
