package qa_scooter.ru;


import java.time.LocalDateTime;
import java.util.List;

public class Order {

    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public final List<String> color;


    public Order(List<String> color) {
        this.color = color;
        this.firstName = "Лёня";
        this.lastName = "Суворов";
        this.address = "Реутов";
        this.metroStation = "Новокосино";
        this.phone = "8999111222333";
        this.rentTime = 1;
        this.deliveryDate = LocalDateTime.now().plusDays(1).toString();
        this.comment = "Lalala";

    }


    public static Order getParameters(List<String> color) {
        return new Order(color);
    }


}
