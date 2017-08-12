package ru.geekbrains.lesson6;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

public class MainClass {

    public static final Logger logger = Logger.getLogger(MainClass.class.getName());
    // OFF
    // SEVERE
    // WARNING
    // INFO
    // CONFIG
    // FINE
    // FINER
    // FINEST
    // ALL

    public static void main(String[] args) {
        LogManager logManager = LogManager.getLogManager();

        try {
            logManager.readConfiguration(new FileInputStream("src/main/resources/logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(Level.WARNING, "BAD WARNING");
    }

    public static void main3(String[] args) {
        Handler myHandler = new ConsoleHandler();

        myHandler.setFilter(record -> {
            return record.getMessage().startsWith("Java");
        });

        logger.setFilter(record -> {
            return !record.getMessage().startsWith("Java");
        });

        Logger.getLogger("").setFilter(record -> {
            return false;
        });

        logger.addHandler(myHandler);

        myHandler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);

        //logger.setUseParentHandlers(false);

        logger.log(Level.SEVERE, "sJava3");
    }

    public static void main2(String[] args) {
        Handler myHandler = new ConsoleHandler();

        Logger lg1 = Logger.getLogger("ru.geekbrains");

        //lg1.addHandler(myHandler);

        System.out.println(".." + logger.getParent().getName() + "..");

        logger.addHandler(myHandler);

        logger.log(Level.WARNING, "BAD WARNING");
    }


    public static void main1(String[] args) {

//        Logger.getLogger("").getHandlers()[0].setLevel(Level.ALL);

        Handler myHandler = new ConsoleHandler();
        myHandler.setLevel(Level.FINE);

        Handler myHandler2 = new ConsoleHandler();
        myHandler2.setLevel(Level.WARNING);

        //myHandler2.setFormatter(new XMLFormatter());
        myHandler2.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return record.getLevel() + "..." + record.getMessage() + "..." + record.getThreadID();
            }
        });

        logger.addHandler(myHandler);
        logger.addHandler(myHandler2);

        logger.setLevel(Level.ALL);

        logger.setUseParentHandlers(false);

        //logger.log(Level.FINE, "BAD FINE");
        logger.log(Level.WARNING, "BAD WARNING");

    }

}
