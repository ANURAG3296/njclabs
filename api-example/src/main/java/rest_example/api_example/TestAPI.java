package rest_example.api_example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/userapi")
public class TestAPI {		
	
	    @GET 
	    @Path("users")
	    @Produces("application/json")
	    public JSONArray getData() throws ClassNotFoundException, JSONException 
	    {	    	
			
			JSONArray result = new JSONArray();
			
			try {
				Connection conn = this.getConnection();
				String sql = "SELECT * FROM users";
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while (rs.next()) {
					JSONObject obj = new JSONObject();
					obj.put("id", rs.getString(1));
					obj.put("first_name", rs.getString(2));
					obj.put("last_name", rs.getString(3));
					obj.put("city", rs.getString(4));
					result.put(obj);
				}
			}
			catch (SQLException e){
				e.printStackTrace();
			}
			return result;
	    }

	    
	    @POST
	    @Path("adduser")
	    @Consumes("application/json")
	    public String addUser(JSONObject obj) throws JSONException, ClassNotFoundException {
	    	try {
	    		Connection conn = this.getConnection();
		    	int id = obj.getInt("id");
		    	String first_name = obj.getString("first_name");
		    	String last_name = obj.getString("last_name");
		    	String city = obj.getString("city");
		    	
				String sql = "INSERT INTO USERS VALUES(" + id + ", '" + first_name + "', '" + last_name + "', '" + city +"')";
				Statement st = conn.createStatement();
				st.executeUpdate(sql);
	    	}
	    	catch(SQLException e) {
	    		return "Failure";
	    	}
	    	return "Success";
	    }
	    
	    
	    @DELETE
	    @Path("deleteuser/{id}")
	    @Consumes("application/json")
	    public String deleteUser ( @PathParam("id") int id) throws JSONException, ClassNotFoundException{
			
	    	try {
	    		Connection conn = this.getConnection();
	    		int uid = id;
	    		String sql = "DELETE FROM USERS WHERE userid =" + uid ;
	    		Statement st = conn.createStatement();
	    		st.executeUpdate(sql);
	    	}
	    	catch(SQLException e) {
	    		return "Failure";
	    	}
	    	return "Success";	
	    }

	    
	    @PUT
	    @Path("updateuser")
	    @Consumes("application/json")
	    public String updateuser (JSONObject obj) throws JSONException, ClassNotFoundException{
	    	try {		
	    		Connection conn = this.getConnection();
	    		int id = obj.getInt("id");
		    	String first_name = obj.getString("first_name");
		    	String last_name = obj.getString("last_name");
		    	String city = obj.getString("city");
		    	
				String sql = "UPDATE USERS SET userid = '"+id+"',firstname = '"+first_name+"',lastname = '"+last_name+"', city='"+city+"' WHERE userid = "+ id;     
				Statement st = conn.createStatement();
				st.executeUpdate(sql);
	    	}
	    	catch(SQLException e) {
	    		return "Failure";
	   		}
	   	return "Success";
	   }
    
	    
	    private Connection getConnection() throws ClassNotFoundException, SQLException {
			String url = "jdbc:mysql://localhost:3306/resttest";
			String username = "root";
			String password = "dbtest123@";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		}
}
