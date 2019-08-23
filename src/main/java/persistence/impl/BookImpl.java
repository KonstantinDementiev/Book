package persistence.impl;

import entity.Book;

public class BookImpl extends ModelImpl {

    private String tableName = "Book";
    private ModelImpl modelImpl = new ModelImpl();

    public void findBookByName() {
        modelImpl.find(tableName, "Name");
    }

    public void findBookById() {
        modelImpl.find(tableName, "Id");
    }

    public void findAllBook() {
        modelImpl.findAll(tableName);
    }

    public void addBook() {
        Book book = new Book();
        modelImpl.add(book);
    }
    public void delBookById() {
        modelImpl.del(tableName,"Id");
    }
    public void delBookByName() {
        modelImpl.del(tableName,"Name");
    }
}