package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    void myFirstTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void LoginTest() {
        System.out.println("testing");
        System.out.println("testingggggg");
        LoginManager lm = new LoginManager();
        assertEquals(true, lm.login("bob"));
        assertEquals(false, lm.login("bob"));
    }
}
