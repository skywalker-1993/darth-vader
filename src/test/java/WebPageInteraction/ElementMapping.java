package WebPageInteraction;

import java.util.HashMap;
import java.util.Map;

public class ElementMapping {

  public static final Map<String, Integer> PRICE_SORT_TEXT = new HashMap<>() {{
    put("ascendantPrice", 4);
    put("descendantPrice", 5);
  }};

  public static final Map<String, String> CAR_TYPES = new HashMap<>(){{
    put("Saloons", "1");
    put("SUVs", "2");
    put("Estates", "3");
    put("Hatchbacks", "4");
    put("Coupés", "5");
    put("Cabriolets", "6");
    put("MPVs", "7");
  }};

  public static final Map<String, String> HATCHBACKS_MODELS = new HashMap<>() {{
    put("A-Class", "1");
    put("A-Class New", "2");
    put("B-Class", "3");
    put("B-Class New", "4");
  }};

  public static final Map<String, String> FUEL_TYPES = new HashMap<>() {{
    put("Diesel", "1");
    put("Premium", "2");
    put("Super", "3");
  }};

}
