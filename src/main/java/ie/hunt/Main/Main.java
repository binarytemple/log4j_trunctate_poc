package ie.hunt.Main;

import org.apache.log4j.Logger;

public class Main {


    private static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) {


        try {
            throw new Exception(new Exception("Inner Exception"));

        }
        catch(Throwable t){

            logger.error("An error",t );
        }







    }
}
