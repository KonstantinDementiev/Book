package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.impl.BookImpl;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {

        BookImpl book = new BookImpl();

        book.crud();

    }
}