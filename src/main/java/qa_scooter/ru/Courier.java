package qa_scooter.ru;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

        public final String login;
        public final String password;
        public final String firstName;

        public Courier (String login, String password, String firstName) {
            this.login = login;
            this.password = password;
            this.firstName = firstName;
        }


    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }


    public static Courier getRandomWithoutLogin(String login) {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    public static Courier getRandomWithoutPass(String password) {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    public static Courier getRandomWithoutName(String firstName) {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    public static Courier getRandomWithoutLoginAndPass(String login, String password) {
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }




}
