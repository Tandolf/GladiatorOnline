package se.andolf.gladiatoronline.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import se.andolf.gladiatoronline.business.SortController;
import se.andolf.gladiatoronline.model.NamedPairs;
import se.andolf.gladiatoronline.model.PropertiesModel;
import se.andolf.gladiatoronline.model.TournamentRequest;
import se.andolf.gladiatoronline.model.TournamentResponse;
import se.andolf.gladiatoronline.util.GeneralUtils;
import se.andolf.gladiatoronline.util.PropertiesLoader;

import com.google.gson.Gson;

@WebServlet(value={"","/Sort", "/Load", "/Fights"})
public class GladiatorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(GladiatorController.class.getName());
	
	private PropertiesModel properties;
       
    public GladiatorController() {
        super();
    }
    
    @Override
    public void init() {
        BasicConfigurator.configure();
    	log.info("Loading properties");
        PropertiesLoader propLoad = new PropertiesLoader();
        propLoad.loadPropertiesFromFile();
        
        properties = new PropertiesModel();       
        properties.setFemaleWeightClasses(propLoad.getFemaleWightClasses());
        properties.setMaleWeightClasses(propLoad.getMaleWeightClasses());
        properties.setMinContender(Integer.parseInt(propLoad.getMinContender()));
        properties.setMaxContender(Integer.parseInt(propLoad.getMaxContender()));
        properties.setMaxPools(Integer.parseInt(propLoad.getMaxPools()));
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String action = request.getRequestURI().replaceFirst(".*\\/", "");
		
		if("".equals(action)){
			request.getServletContext().getRequestDispatcher("/WEB-INF/views/index.html").forward(request, response);
		}
		
        if ("fights".equals(action)) {
            request.getServletContext().getRequestDispatcher("/WEB-INF/views/fights.html").forward(request, response);
        }
		
		else if ("Load".equals(action)){
			
			Gson gson = new Gson();
			String jsonPropertiesObject = gson.toJson(properties, PropertiesModel.class);
			
			response.setContentType("application/json");
			PrintWriter writer = response.getWriter();
			writer.print(jsonPropertiesObject);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
		String action = request.getRequestURI().replaceFirst(".*\\/", "");
		
		if("Sort".equals(action)){
			BufferedReader reader = request.getReader();		
			String jsonString = reader.readLine();
			
			TournamentRequest tRequest = GeneralUtils.parseJsonString(jsonString);
			
			if(tRequest == null){
			    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
			
			if(!GeneralUtils.validateRequest(tRequest)){
			    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			} else {
			    SortController sorter = new SortController();
			    
			    List<List<NamedPairs>> AllSortedPools = new ArrayList<List<NamedPairs>>();
			    
			    for (String[] fighterArray : tRequest.getContenders()) {			    
			        List<String> fighters = Arrays.asList(GeneralUtils.escapeStringsInArray(fighterArray));
			        List<NamedPairs> fighterList = sorter.newSorting(fighters);
			        AllSortedPools.add(fighterList);
			    }
			    
			    TournamentResponse tResponse = new TournamentResponse();
			    tResponse.setTournamentName(StringEscapeUtils.escapeHtml(tRequest.getTournamentName()));
			    tResponse.setGender(StringEscapeUtils.escapeHtml(tRequest.getGender()));
			    tResponse.setWeight(tRequest.getWeight());
			    tResponse.setPools(tRequest.getPools());
			    tResponse.setTotalContenders(tRequest.getTotalContenders());
			    tResponse.setFighters(AllSortedPools);
			    
			    response.setContentType("text/plain");
			    response.setCharacterEncoding("UTF-8");
			    PrintWriter writer = response.getWriter();
			    writer.print(GeneralUtils.writeJson(tResponse, TournamentResponse.class));
			}			
		};
	}
}