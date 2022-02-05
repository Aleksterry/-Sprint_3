package qa_scooter.ru;


import com.github.javafaker.Faker;
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


    public Order(List<String> color, String firstName, String lastName, String address, String metroStation, String phone,
                 int rentTime, String deliveryDate, String comment) {
        this.color = color;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }


    public static Order getParameters(List<String> color) {
        Faker faker = new Faker();
        String firstName = faker.name().firstName(); //Karyn
        String lastName = faker.name().lastName(); //Langosh
        String address = faker.address().streetAddress(); //00836 Solomon Parks
        String metroStation = faker.dune().planet(); //Kaitain
        String phone = faker.phoneNumber().cellPhone(); //1-218-797-8167
        int rentTime = faker.number().numberBetween(1,10); //5
        String deliveryDate = LocalDateTime.now().plusDays(1).toString(); //2022-01-08T18:07:25.021Z
        String comment = faker.dune().character(); //Remedy
        return new Order(color, firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
    }


}
