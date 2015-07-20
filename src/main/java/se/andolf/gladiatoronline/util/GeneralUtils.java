package se.andolf.gladiatoronline.util;

import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import se.andolf.gladiatoronline.model.TournamentRequest;
import se.andolf.gladiatoronline.model.TournamentResponse;
import se.andolf.gladiatoronline.servlet.GladiatorController;

public class GeneralUtils {
    
    private static Logger log = Logger.getLogger(GladiatorController.class.getName());

    public static String[] escapeStringsInArray(String[] array){
        
        for (int i = 0; i < array.length; i++) {
            array[i] = StringEscapeUtils.escapeHtml(array[i]);
            
        }
        return array;
    }

    public static boolean validateRequest(TournamentRequest tRequest) {
        
        boolean result = false;
        
            result = checkForEmptyStringInput(tRequest.getTournamentName());
            result = checkForEmptyStringInput(tRequest.getGender());
            result = checkForEmptyIntInput(tRequest.getPools());
            result = checkForEmptyIntInput(tRequest.getWeight());
            result = checkForEmptyIntInput(tRequest.getTotalContenders());           
            result = validateTotalNumberAndInputFields(tRequest.getContenders(), tRequest.getTotalContenders());
        
        return result;
    }

    private static boolean validateTotalNumberAndInputFields(String[][] contenders, int totalContenders) {
        
        boolean result = false;
        
        int total = 0;
        for (String[] string : contenders) {
            total += string.length;
        }
        
        if(total == totalContenders){
            result = true;
        }
        return result;
    }
    
    private static boolean checkForEmptyStringInput(String input){
        
        boolean result = false;
        
        if(!"".equals(input.trim())){
            result = true;
        }
        return result;
    }
    
    private static boolean checkForEmptyIntInput(int input){
        
        boolean result = false;
        
        if(!(input <= 1)){
            result = true;
        }

        return result;
    }
    
    public static TournamentRequest parseJsonString(String jsonString){
        Gson gson = new Gson();
        
        TournamentRequest tRequest = null;
        
        try {
            tRequest = gson.fromJson(jsonString, TournamentRequest.class);              
        } catch (JsonSyntaxException e) {
            log.throwing(GeneralUtils.class.getName(), "parseJsonString", e);
        }
        return tRequest;
    }
    
    public static <T> String writeJson(TournamentResponse tResponse, Class<T> classRepresentation){
        
        Gson gson = new Gson();      
        String jsonString = gson.toJson(tResponse, classRepresentation);
        
        return jsonString;
    }
}
