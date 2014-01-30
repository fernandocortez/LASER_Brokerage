package marketDataSubsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * The main control class for accessing market data from the Internet. This
 * class uses the Yahoo URL-based API to retrieve CSV data. Data is
 * approximately 15-20 minutes behind realtime (that's what you get for free
 * data!).
 *
 * @author Thomas Savage
 * @version 201211271830
 */
public class MarketData {
	
    /**
     * The number of graphs that have been generated. BrokerageControl uses
     * this to delete the graphs during shutdown. 
     */
    private int graphNum;

    /**
     * Loads the MarketData controller.
     */
    public MarketData() {
        graphNum = 0;
    }

    /**
     * Get the current asking price of a stock.
     *
     * @param symbol The stock ticker symbol.
     * @return stockPrice The current ask price of the requested stock.
     */
    public double getStockPrice(String symbol) {
        /* Initialize stock symbol and price. The API requires an upper case 
         symbol.
         */
        String sym = symbol.toUpperCase();
        double stockPrice = 0.;

        /* Build the appropriate URL string.
         urlbase is the beginning format of all CSV data requests.
         urlvar is the format for requesting the asking price of a stock.
         urlfull is the concatenation (urlbase + sym + urlvar) to form a full
         valid URL for this type of request.
         */
        String urlbase = "http://finance.yahoo.com/d/quotes.csv?s=";
        String urlvar = "&f=b2";
        String urlfull = urlbase.concat(sym).concat(urlvar);
        String in = "";

        try {
            // Build the URL object and ready our input stream.
            URL url = new URL(urlfull);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            /* Read from the stream.
             */
            while ((in = br.readLine()) != null) {
                stockPrice = Double.parseDouble(in);
            }

            br.close();
        } /* There is a problem with the URL format. A more graceful error
         * handling will be necessary in the future.
         */ catch (MalformedURLException e) {
            System.out.println("Bad URL");
            e.printStackTrace();
            System.exit(1);
        } /* There is some sort of Internet connectivity problem, either at the
         * local machine or at Yahoo's servers.
         */ catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            e.printStackTrace();
            System.exit(1);
        } /* There is a problem with the data received from the API. A more
         * graceful error handling will be necessary in the future.
         */ catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
            System.exit(1);
        } /* The API returned something other than a number. This probably means
         * that an invalid stock ticker was used.
         */ catch (NumberFormatException e) {
            if (in.equals("N/A")) {
                System.out.println("Finance API returned junk data."
                        + " The stock ticker may be incorrect.");
            } else {
                System.out.println("NumberFormatException caused by"
                        + " string " + in);
                e.printStackTrace();
            }
        }

        return stockPrice;
    }
    
    /**
     * @param symbol The stock ticker symbol.
     * @return The current daily change value of the requested stock.
     */
    public String getStockChange(String symbol) {
        String sym = symbol.toUpperCase();
        String stockChange = "";

        /* Build the appropriate URL string.
         urlbase is the beginning format of all CSV data requests.
         urlvar is the format for requesting the asking price of a stock.
         urlfull is the concatenation (urlbase + sym + urlvar) to form a full
         valid URL for this type of request.
         */
        String urlbase = "http://finance.yahoo.com/d/quotes.csv?s=";
        String urlvar = "&f=c6";
        String urlfull = urlbase.concat(sym).concat(urlvar);
        String in = "";

        try {
            // Build the URL object and ready our input stream.
            URL url = new URL(urlfull);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            /* Read from the stream.
             */
            while ((in = br.readLine()) != null) {
                stockChange = in;
            }

            br.close();
        } /* There is a problem with the URL format. A more graceful error
         * handling will be necessary in the future.
         */ catch (MalformedURLException e) {
            System.out.println("Bad URL");
            e.printStackTrace();
            System.exit(1);
        } /* There is some sort of Internet connectivity problem, either at the
         * local machine or at Yahoo's servers.
         */ catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            e.printStackTrace();
            System.exit(1);
        } /* There is a problem with the data received from the API. A more
         * graceful error handling will be necessary in the future.
         */ catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
            System.exit(1);
        } /* The API returned something other than a number. This probably means
         * that an invalid stock ticker was used.
         */ catch (NumberFormatException e) {
            if (in.equals("N/A")) {
                System.out.println("Finance API returned junk data."
                        + " The stock ticker may be incorrect.");
            } else {
                System.out.println("NumberFormatException caused by"
                        + " string " + in);
                e.printStackTrace();
            }
        }

        return stockChange.substring(1, stockChange.length() - 1);
    }
    
    /**
     * @param symbol The stock ticker symbol.
     * @return The current daily percent change value of the requested stock.
     */
    public String getStockPercent(String symbol) {
        String sym = symbol.toUpperCase();
        String stockPercent = "";

        /* Build the appropriate URL string.
         urlbase is the beginning format of all CSV data requests.
         urlvar is the format for requesting the asking price of a stock.
         urlfull is the concatenation (urlbase + sym + urlvar) to form a full
         valid URL for this type of request.
         */
        String urlbase = "http://finance.yahoo.com/d/quotes.csv?s=";
        String urlvar = "&f=p2";
        String urlfull = urlbase.concat(sym).concat(urlvar);
        String in = "";

        try {
            // Build the URL object and ready our input stream.
            URL url = new URL(urlfull);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            /* Read from the stream.
             */
            while ((in = br.readLine()) != null) {
                stockPercent = in;
            }

            br.close();
        } /* There is a problem with the URL format. A more graceful error
         * handling will be necessary in the future.
         */ catch (MalformedURLException e) {
            System.out.println("Bad URL");
            e.printStackTrace();
            System.exit(1);
        } /* There is some sort of Internet connectivity problem, either at the
         * local machine or at Yahoo's servers.
         */ catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            e.printStackTrace();
            System.exit(1);
        } /* There is a problem with the data received from the API. A more
         * graceful error handling will be necessary in the future.
         */ catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
            System.exit(1);
        } /* The API returned something other than a number. This probably means
         * that an invalid stock ticker was used.
         */ catch (NumberFormatException e) {
            if (in.equals("N/A")) {
                System.out.println("Finance API returned junk data."
                        + " The stock ticker may be incorrect.");
            } else {
                System.out.println("NumberFormatException caused by"
                        + " string " + in);
                e.printStackTrace();
            }
        }

        return stockPercent.substring(1, stockPercent.length() - 1);
    }

    /**
     * Get the company name associated with a ticker symbol.
     *
     * @param symbol The stock ticker symbol.
     * @return stockName The company name associated with the stock ticker.
     */
    public String getStockName(String symbol) {
        /* Initialize stock symbol and price. The API requires an upper case 
         symbol.
         */
        String sym = symbol.toUpperCase();
        String stockName = "";

        /* Build the appropriate URL string.
         urlbase is the beginning format of all CSV data requests.
         urlvar is the format for requesting the asking price of a stock.
         urlfull is the concatenation (urlbase + sym + urlvar) to form a full
         valid URL for this type of request.
         */
        String urlbase = "http://finance.yahoo.com/d/quotes.csv?s=";
        String urlvar = "&f=n";
        String urlfull = urlbase.concat(sym).concat(urlvar);

        try {
            // Build the URL object and ready our input stream.
            URL url = new URL(urlfull);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            /* Read from the stream.
             */
            String in = "";
            while ((in = br.readLine()) != null) {
                stockName = in;
            }

            br.close();
        } /* There is a problem with the URL format. A more graceful error
         * handling will be necessary in the future.
         */ catch (MalformedURLException e) {
            System.out.println("Bad URL");
            e.printStackTrace();
            System.exit(1);
        } /* There is some sort of Internet connectivity problem, either at the
         * local machine or at Yahoo's servers.
         */ catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            e.printStackTrace();
            System.exit(1);
        } /* There is a problem with the data received from the API. A more
         * graceful error handling will be necessary in the future.
         */ catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
            System.exit(1);
        }

        return stockName.substring(1, stockName.length() - 1);
    }

    /**
     * @param index The ticker symbol of the requested stock index.
     * @param arg The type of data requested (ask, change, %change).
     * @return
     */
    public String updateMarketIndexData(String index, String arg) {
        String dataText = "";
        String urlvar = "";
        String urlfull = "";
        String in = "";
        
        /* Build the appropriate URL string.
        urlbase is the beginning format of all CSV data requests.
        urlvar is the format for requesting the asking price of a stock.
        urlfull is the concatenation (urlbase + sym + urlvar) to form a full
        valid URL for this type of request.
        */
        String urlbase = "http://finance.yahoo.com/d/quotes.csv?s=";
        urlvar = index.concat("&f=").concat(arg);
        urlfull = urlbase.concat(urlvar);

        try {
        	// Build the URL object and ready our input stream.
            URL url = new URL(urlfull);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            
            /* Read from the stream.
             */
            while ((in = br.readLine()) != null) {
                dataText = in;
            }

            br.close();
        } catch (MalformedURLException e) {
            System.out.println("Bad URL");
            e.printStackTrace();
            System.exit(1);
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
            System.exit(1);
        } catch (NumberFormatException e) {
            if (in.equals("N/A")) {
                System.out.println("Finance API returned junk data."
                        + " The stock ticker may be incorrect.");
            } else {
                System.out.println("NumberFormatException caused by"
                        + " string " + in);
                e.printStackTrace();
            }
        }
        
        if(arg.equals("c6") || arg.equals("p2")) {
            return dataText.substring(1, dataText.length() - 1);
        }
        else {
            return dataText;
        }
    }

    /**
     * Retrieve a graph of stock performance for the requested stock.
     * 
     * @param symbol The stock ticker symbol.
     * @return The local path for the graph image file.
     */
    public String marketGraph(String symbol) {
        String localPath = "mktgraph".concat(Integer.toString(graphNum)).concat(".png");
        String netPath = "http://chart.finance.yahoo.com/z?s=".concat(symbol);
        BufferedImage img = null;
        File imgFile = null;

        try {
            URL url = new URL(netPath);
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            imgFile = new File(localPath);
            ImageIO.write(img, "png", imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        graphNum += 1;
        return localPath;
    }

    /**
     * Check Internet connection to Yahoo Finance.
     *
     * @return true/false If Internet connection to Yahoo Finance can be
     * established.
     */
    public static Boolean isConnected() {
        try {
            // Form the Yahoo Finance URL.
            URL url = new URL("http://finance.yahoo.com");

            // Open connection to Yahoo.
            HttpURLConnection urlConnect =
                    (HttpURLConnection) url.openConnection();

            // Attempt to retrieve data from Yahoo.
            Object objData = urlConnect.getContent();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
    public int getGraphNum() {
        return graphNum;
    }
}