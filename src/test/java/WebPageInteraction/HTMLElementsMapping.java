package WebPageInteraction;

import java.util.HashMap;
import java.util.Map;


public class HTMLElementsMapping {

    public static final String savedChecklistMessage = "File exported successfully";
    public static final String importedChecklistMessage = "File loaded successfully";

    private final Map<String,String> xpathElements = new HashMap<>();
    private final Map<String,String> checklistConfigCSSElem = new HashMap<>();
    private final Map<String,String> rgbValuesTable = new HashMap<>();

    public HTMLElementsMapping(){
        createHTMLElementsXPathTable();
        createHTMLElementsCSSTable();
        createRGBValuesTable();
    }

    private void createRGBValuesTable(){
        rgbValuesTable.put("rgba(255, 255, 255, 1)", "White_1");
        rgbValuesTable.put("rgb(255, 255, 255)", "White_2");
        rgbValuesTable.put("rgba(18, 18, 18, 1)", "Dark_1");
        rgbValuesTable.put("rgb(18, 18, 18)", "Dark_2");
    }

    private void createHTMLElementsXPathTable(){

    }

    private void createHTMLElementsCSSTable(){

    }

    public String queryHTMLElementsCSSTable(String elementName){
        return checklistConfigCSSElem.get(elementName);
    }

    public String queryHTMLElementsXPathTable(String elementName){
        return xpathElements.get(elementName);
    }

    public String queryRGBValuesTable(String rgbColor){
        return rgbValuesTable.get(rgbColor);
    }
}
