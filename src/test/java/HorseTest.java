import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class HorseTest {
  @Test
  @DisplayName("Null name exception")
  public void nullNameException() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 125));
    assertEquals("Name cannot be null.", exception.getMessage());
  }

  @ParameterizedTest
  @DisplayName("Blank name exception")
  @ValueSource(strings = {"'', ' '", "'   a   ', '   a   '", "\t\t\t\t\t", "\n\n\n\n\n"})
  public void blankNameException() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> new Horse(" ", 1, 125));
    assertEquals("Name cannot be blank.", exception.getMessage());
  }

  @Test
  @DisplayName("Negative speed exception")
  public void negativeSpeedException() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> new Horse("Houdini", -1));
    assertEquals("Speed cannot be negative.", exception.getMessage());
  }

  @Test
  @DisplayName("Negative distance exception")
  public void negativeDistanceException() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> new Horse("Houdini", 1, -1));
    assertEquals("Distance cannot be negative.", exception.getMessage());
  }

  @Test
  @DisplayName("getName method")
  public void getNameMethod() throws NoSuchFieldException, IllegalAccessException {
    String horseName = "Houdini";
    Horse horse = new Horse("Houdini", 17, 20);
    Field nameField = Horse.class.getDeclaredField("name");
    nameField.setAccessible(true);
    assertEquals(horseName, nameField.get(horse));
  }

  @Test
  @DisplayName("getSpeed method")
  public void getSpeedMethod() throws NoSuchFieldException, IllegalAccessException {
    double horseSpeed = 17;
    Horse horse = new Horse("Houdini", 17, 20);
    Field speedFiled = Horse.class.getDeclaredField("speed");
    speedFiled.setAccessible(true);
    assertEquals(horseSpeed, speedFiled.get(horse));
  }

  @Test
  @DisplayName("getNonZeroDistance method")
  public void getNonZeroDistanceMethod() throws NoSuchFieldException, IllegalAccessException {
    double horseDistance = 20;
    Horse horse = new Horse("Houdini", 17, 20);
    Field speedField = Horse.class.getDeclaredField("distance");
    speedField.setAccessible(true);
    assertEquals(horseDistance, speedField.get(horse));
  }

  @Test
  @DisplayName("getZeroDistance method")
  public void getZeroDistanceMethod() throws NoSuchFieldException, IllegalAccessException {
    double horseDistance = 0;
    Horse horse = new Horse("Houdini", 17);
    Field speedField = Horse.class.getDeclaredField("distance");
    speedField.setAccessible(true);
    assertEquals(horseDistance, speedField.get(horse));
  }

  @Test
  @DisplayName("getRandomDouble method")
  public void getRandomDoubleMethod() {
    try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
      new Horse("Houdini", 17, 20).move();
      mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
    }
  }

  @ParameterizedTest
  @DisplayName("move method")
  @ValueSource(doubles = {0.2, 0.5, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9})
  public void moveMethod(double random) {
    double horseDistance = 10;
    double horseSpeed = 17;
    try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
      Horse horse = new Horse("Houdini", horseSpeed, horseDistance);
      mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
      horse.move();
      mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
      assertEquals(horseDistance + (horseSpeed * random), horse.getDistance());
    }
  }
}
