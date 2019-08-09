package service;

import entity.Book;

import entity.Shop;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static List<Book> books = new ArrayList<Book>();
    private static List<Shop> shops = new ArrayList<Shop>();

    public static void main(String[] args) {

        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        try {
            session.beginTransaction();


            Book book = new Book();
            book.setName("Papa Karlo");
            session.save(book);

            Query query1 = session.createQuery("FROM Book");
            books = query1.list();

            Query query2 = session.createQuery("FROM Shop ");
            shops = query2.list();

            session.getTransaction().commit();
            System.out.println("Transaction successful!");
            LOG.info("Transaction successful!");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("Transaction failed!");
            LOG.error("Transaction failed!", e);
            e.printStackTrace();
        } finally {
            session.close();
            HibernateSessionFactory.shutdown();
        }

        for (Book book : books) {
            System.out.println(book.toString());
        }

        for (Shop shop : shops) {
            System.out.println(shop.toString());
        }

    }

}