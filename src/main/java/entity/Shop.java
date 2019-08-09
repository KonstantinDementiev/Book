package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String adress;


    @Override
    public String toString() {
        return "Shop{" + "id=" + id + ", adress='" + adress + '\'' + '}';
    }
}
