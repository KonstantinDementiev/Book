package persistence.impl;

import entity.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.BookRepository;
import utils.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookImpl implements BookRepository {

    private static List<Book> books = new ArrayList<Book>();
    private static final Logger LOG = LoggerFactory.getLogger(BookImpl.class);
    private Scanner scanner;
    Session session;

    public void find() {

        session.beginTransaction();
        System.out.println("Insert name to find: ");
        scanner = new Scanner(System.in);
        String queryName = scanner.nextLine();
        Query queryFind = session.createQuery("FROM Book WHERE name = :paramName");
        queryFind.setParameter("paramName", queryName);
        books = queryFind.list();
        printBook(books);
        scanner.close();
        session.close();

    }

    public void findAll() {

        session.beginTransaction();
        Query queryFindAll = session.createQuery("FROM Book");
        books = queryFindAll.list();
        printBook(books);
        scanner.close();
        session.close();
    }

    public void add() {

        session.beginTransaction();
        System.out.println("Insert name of new book: ");
        scanner = new Scanner(System.in);
        String newName = scanner.nextLine();
        Book book = new Book();
        book.setName(newName);
        session.save(book);
        session.getTransaction().commit();
        scanner.close();
        session.close();
    }

    public void del() {

        session.beginTransaction();
        System.out.println("Insert name of book for deleting: ");
        scanner = new Scanner(System.in);
        String deleteName = scanner.nextLine();
        Query deleteQuery = session.createQuery("DELETE Book WHERE name = :paramName");
        deleteQuery.setParameter("paramName", deleteName);
        int result = deleteQuery.executeUpdate();
        if (result > 0) {
            System.out.println(result + " book(s) has been deleted!");
        }
        session.getTransaction().commit();
        scanner.close();
        session.close();
    }

    public void update() {

        session.beginTransaction();
        System.out.println("Insert name of book for updating: ");
        scanner = new Scanner(System.in);
        String updateName = scanner.nextLine();
        Query queryUpdate = session.createQuery("FROM Book WHERE name = :paramName");
        queryUpdate.setParameter("paramName", updateName);
        books = queryUpdate.list();
        System.out.println("Insert new name of book: ");
        scanner = new Scanner(System.in);
        String rename = scanner.nextLine();

        for (Book bookUp : books) {
            bookUp.setName(rename);
            session.update(bookUp);
        }
        session.getTransaction().commit();
        System.out.println(books.size() + " book(s) has been updated!");
        scanner.close();
        session.close();
    }


    public void crud() {

        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            System.out.println("Please, choose your operation: 1 - Find, 2 - Find all, 3 - Add, 4 - Delete, 5 - Update");
            scanner = new Scanner(System.in);
            int operation = scanner.nextInt();

            switch (operation) {
                case 1:
                    find();
                    break;

                case 2:
                    findAll();
                    break;

                case 3:
                    add();
                    break;

                case 4:
                    del();
                    break;

                case 5:
                    update();
                    break;

                default:
                    System.out.println("You did not enter a number from 1 to 4");
                    scanner.close();
                    session.close();
                    break;
            }

            System.out.println("Transaction successful!");
            LOG.info("Transaction successful!");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("Transaction failed!");
            LOG.error("Transaction failed!", e);
            e.printStackTrace();
        } finally {
            HibernateSessionFactory.shutdown();
        }
    }

    private void printBook(List<Book> books) {
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

}
