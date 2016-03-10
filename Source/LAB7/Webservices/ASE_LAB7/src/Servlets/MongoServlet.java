package Servlets;



import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import javax.ws.rs.Path;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;

import org.json.*;
//import com.ibm.json.java.JSON;
//import com.ibm.json.java.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;
import com.mongodb.client.FindIterable;

/**
 * Servlet implementation class UserServlet
 */
@Path("/user")
public class MongoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MongoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		MongoClientURI uri = new MongoClientURI("mongodb://naga:password@ds019648.mlab.com:19648/aselab7nag");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("ASELAB7");
		BasicDBObject query = new BasicDBObject();
//		query.put("name","naga");// request.getParameter("name"));
//		query.put("name", request.getParameter("name"));
//		query.put("password","password"); //request.getParameter("password"));
		//query.put("password",request.getParameter("password"));
//		
//		FindIterable<Document> iterable = db.getCollection("restaurants").find();
//		iterable.forEach(new Block<Document>() {
//		    @Override
//		    public void apply(final Document document) {
//		        System.out.println(document);
//		    }
//		});
		
////		MongoClient client1 = new MongoClient();
//		MongoDatabase database = client.getDatabase("aselab7nag");
//		MongoCollection<DBObject> coll = database.getCollection("ASELAB7", DBObject.class);
//		//coll.insertOne(new BasicDBObject("x", 1));
//		for (DBObject doc : coll.find()) {
//		    System.out.println(doc);
//		}
//		
		DBCursor docs = users.find(query);
		response.getWriter().write(docs.toArray().toString());
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String data = buffer.toString();
		System.out.println(data);

		JSONObject params = new JSONObject(data);
		BasicDBObject user1 = new BasicDBObject();
		
		for(Object key:params.keySet().toArray())
		{
			user1.put(key.toString(),params.get(key.toString()));
		}
		System.out.println(user1.toJson());
		
		MongoClientURI uri = new MongoClientURI("mongodb://naga:password@ds019648.mlab.com:19648/aselab7nag");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("ASELAB7");
		WriteResult result = users.insert(user1);
		System.out.println("Inserted " + user1.toJson());
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		response.getWriter().write(result.toString());
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

