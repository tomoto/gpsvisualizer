package nmeagps.data;

import java.util.Map;

import nmeagps.util.HashMapWithDefault;
import nmeagps.util.MapBuilder;

public class Trans {
  private static <K> MapBuilder<K, String> mapBuilder(K dummyKey) {
    return new MapBuilder<>(new HashMapWithDefault<K, String>("Unknown"));
  }

  // use this for GLL's status and mode also
  public static class RMC {
    public static final Map<String, String> status = mapBuilder("")
        .add("A", "Active")
        .add("V", "Void")
        .build();

    public static final Map<String, String> mode = mapBuilder("")
        .add("A", "Autonomous")
        .add("D", "Differential")
        .add("E", "Estimated")
        .add("F", "Float real-time kinectic")
        .add("M", "Manual input")
        .add("N", "Not available")
        .add("P", "Precise")
        .add("R", "Real-time kinematic")
        .add("S", "Simulator")
        .build();

    public static final Map<String, String> navStatus = mapBuilder("")
        .add("S", "Safe")
        .add("C", "Caution")
        .add("U", "Unsafe")
        .add("V", "Void")
        .build();

  }

  public static class GGA {
    public static final Map<Integer, String> fixQuality = mapBuilder(1)
        .add(0, "Invalid")
        .add(1, "Standard position service")
        .add(2, "Differential GPS")
        .add(3, "Precise positioning service")
        .add(4, "Real-time kinematic")
        .add(5, "Float real-time kinematic")
        .add(6, "Estimated")
        .add(7, "Manual input")
        .add(8, "Simulator")
        .build();

  }

  public static class GSA {
    public static final Map<String, String> mode1 = mapBuilder("")
        .add("M", "Manual")
        .add("A", "Automatic")
        .build();

    public static final Map<Integer, String> mode2 = mapBuilder(1)
        .add(1, "Not available")
        .add(2, "2D")
        .add(3, "3D")
        .build();
  }

  public static class VTG {
    public static final Map<String, String> mode = RMC.mode;
  }
}
