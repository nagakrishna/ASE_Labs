import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Path("/nagaservice")
public class sample {
	@GET
	@Produces("application/json")
	public Response noUse() throws JSONException {
		 
		JSONObject jsonObject = new JSONObject();
		String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
	
	
	
	  @Path("{place1}")
	  @GET
	  @Produces("application/json")
	  public Response SentimentVenueDetails(@PathParam("place1") String place1) throws JSONException, IOException  {

		  ReplaceSpaces r = new ReplaceSpaces();
		  String newPlace =   r.getText(place1);
		  String placeUrl;
		  if(!r.verifyPlaceNotANumber(place1)){
		   placeUrl =   "https://api.foursquare.com/v2/venues/search?near=" + newPlace + "&categoryId=4bf58dd8d48988d1fa941735&client_id=EKGH2L4JAIZMXZGGRTN3KV5PH0ZQYL3VO4N3OOSU3HKYZWQF&client_secret=RAET0NWZREI3Y5NUPQUSRMECY514AYHEOUU03BFX14SFMVXT&v=20160301&limit=5";
		  }
		  else{
			  placeUrl = null;
		  }
		  URL url = new URL(placeUrl);
		  URLConnection urlc = url.openConnection();
		  urlc.setDoOutput(true);
	      urlc.setAllowUserInteraction(false);
	      PrintStream ps = new PrintStream(urlc.getOutputStream());
	      ps.close();
		  
       
        

        //use post mode
        
        //get result
        BufferedReader br = new BufferedReader(new InputStreamReader(urlc
            .getInputStream()));
        String l = null;
        StringBuilder sb = new StringBuilder();
        while ((l=br.readLine())!=null) {
        	System.out.println("API 1");
            System.out.println(l);
            sb.append(l);
        }
////        String jsonString = br.toString();
//        
        JSONObject data = new JSONObject(sb.toString());
        JSONObject response = data.getJSONObject("response");
        JSONArray venues = response.getJSONArray("venues");
        int length = venues.length();
        System.out.println("length =" + length);
        String[] name = new String[10];
        String[] venueID = new String[10];
        for(int i=0;i<length;i++){
        	JSONObject fir = venues.getJSONObject(i);
            venueID[i] = fir.getString("id"); 
            name[i] = fir.getString("name");
            System.out.println("Venue " + i + venueID[i]);
        }
//        JSONObject fir = venues.getJSONObject(0);
//        String venueID = fir.getString("id");       
        br.close();
        String[] reviewText = new String[10];
        for(int i=0;i<length;i++){
        	 String reviewURL = "https://api.foursquare.com/v2/venues/" + venueID[i] + "/tips?sort=recent&client_id=Q0ENF1YHFTNPJ31DCF13ALLENJW0P5MTH13T1SA0ZP1MUOCI&client_secret=ZH4CRZNEWBNTALAE3INIB5XG0QI12R4DT5HKAJLWKYE1LHOG&v=20160215&limit=1";
             URL url1 = new URL(reviewURL);
             URLConnection urlc1 = url1.openConnection();

             BufferedReader br1 = new BufferedReader(new InputStreamReader(urlc1
                     .getInputStream()));
                 String l1 = null;
                 StringBuilder sb1 = new StringBuilder();
                 while ((l1=br1.readLine())!=null) {
                 	System.out.println("API 2");
                     System.out.println(l1);
                     sb1.append(l1);
                 }
                 JSONObject data1 = new JSONObject(sb1.toString());
                 JSONObject response1 = data1.getJSONObject("response");
                 JSONObject tips = response1.getJSONObject("tips");
                 JSONArray items = tips.getJSONArray("items");
                 try{
                 JSONObject firstElement = items.getJSONObject(0);
                 reviewText[i] = firstElement.getString("text");
                 }
                 catch (Exception e){
                 	reviewText[i] = "no reviews";
                 }
                 br.close();
        }
        
        
        String[] score = new String[10];
        String[] type = new String[10];
        for(int i=0; i<length;i++){
        	String text1 =   reviewText[i].replace(" ", "%20");
		    URL url2 = new URL("http://gateway-a.watsonplatform.net/calls/text/TextGetTextSentiment?apikey=11853d48ab52a658cb5b2a6157021c6dfa9c3876&outputMode=json&text=" + text1);
            System.out.println("1");
            URLConnection urlc2 = url2.openConnection();
            System.out.println("2");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(urlc2
                  .getInputStream()));
            System.out.println("3");
            String l2 = null;
            StringBuilder sb2 = new StringBuilder();
            while((l2 = br2.readLine())!=null){
            	System.out.println("API 2");
            	System.out.println(l2);
            	sb2.append(l2);
            }
            System.out.println("4");
            JSONObject data2 = new JSONObject(sb2.toString());
            System.out.println("5");
            JSONObject sentiment = data2.getJSONObject("docSentiment");
            System.out.println("6");
            score[i] = sentiment.getString("score");
            System.out.println("7");
            type[i] = sentiment.getString("type");
            System.out.println("8");
        }
           
        
            JSONArray jsonArray = new JSONArray();
			
			String result = "";
			for(int i=0;i<length;i++){
//				JSONObject jsonObject = new JSONObject();
				Map obj = new LinkedHashMap();
				obj.put("SentimentScore", score[i]);
				obj.put("VenueID", venueID[i]);
				obj.put("name", name[i]);
				obj.put("Review", reviewText[i]);
				obj.put("SentimentType", type[i]); 
//				JSONObject.toJSONString(obj)
//				jsonObject = JSONObject.toJSONString(obj);
//				result = result + jsonObject + ",";
				JSONObject jsonObject = new JSONObject(obj);
				jsonArray.put(jsonObject);
				
			}
			JSONObject json2 = new JSONObject();
//			json2.put("Sentiment Analysis of Venues based on recent reviews", jsonArray);
			json2.put("data1", jsonArray);
			result = " " + json2;
			return Response.status(200).entity(result).build();
		  }
}

@Path("/user")
class MongoServelet extends HttpServlet{
	
	public MongoServelet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MongoClientURI uri = new MongoClientURI("mongodb://naga:password@ds019648.mlab.com:19648/aselab7nag");
		MongoClient client = new MongoClient(uri);
		
		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("studentrecords");
		BasicDBObject query = new BasicDBObject();
//		query.put("name", request.getParameter("name"));
		query.put("name","naga");// request.getParameter("name"));
		DBCursor docs = users.find(query);
		response.getWriter().write(docs.toArray().toString());
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
	}
	
	@Override
	protected void doOptions(HttpServletRequest arg0, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doOptions(arg0, response);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, HEAD, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
	}
	
}



