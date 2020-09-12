import org.json.simple.*;
import org.json.simple.parser.*;
public class Message {
	String operation;
	String id;
	String city;
	String humidity;
	String temp;
	String windVel;
	String date;
	String time;
	

	/**
	 * @param operation
	 * @param id
	 * @param city
	 * @param humidity
	 * @param temp
	 * @param windVel
	 * @param date
	 * @param time
	 */

	public Message() {
		this.operation = "0";
		this.id = "0";
		this.city = "0";
		this.humidity = "0";
		this.temp = "0";
		this.windVel = "0";
		this.date = "0";
		this.time = "0";
	}
	
	public Message(String operation) {
		this.operation = operation;
		this.id = "0";
		this.city = "0";
		this.humidity = "0";
		this.temp = "0";
		this.windVel = "0";
		this.date = "0";
		this.time = "0";
	}
	
	public Message(String operation, String city) {
		this.operation = operation;
		this.id = "0";
		this.city = city;
		this.humidity = "0";
		this.temp = "0";
		this.windVel = "0";
		this.date = "0";
		this.time = "0";
	}
	
	public Message(String operation, String id, String city, String humidity, String temp, String windVel, String date, String time) {
		this.operation = operation;
		this.id = id;
		this.city = city;
		this.humidity = humidity;
		this.temp = temp;
		this.windVel = windVel;
		this.date = date;
		this.time = time;
	}
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getWindVel() {
		return windVel;
	}
	public void setWindVel(String windVel) {
		this.windVel = windVel;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	/**
	 * Convierte un objeto Message a un String en formato JSON
	 * @param Mensaje 
	 * @return String JSON
	 */
	@SuppressWarnings("unchecked")
	public String toJSON( ) {
		JSONObject obj = new JSONObject();
		obj.put("operation", getOperation() );
		obj.put("id", getId() );
		obj.put("city", getCity() );
		obj.put("humidity", getHumidity() );
		obj.put("temp", getTemp() );
		obj.put("windVel", getWindVel() );
		obj.put("date", getDate() );
		obj.put("time", getTime() );


		return obj.toJSONString();
	}
	
	/**
	 * Convierto un String en notacion JSON a un objeto Message
	 * @param String 
	 * @return Objeto Message
	 * @throws ParseException
	 */
	public void toMessage(String str) {

		try {
			//parsear
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(str.trim());
			JSONObject jsonObject = (JSONObject) obj;
			//extraer variables
			String operation = (String)jsonObject.get("operation");
			String id = (String)jsonObject.get("id");
			String city = (String)jsonObject.get("city");
			String humidity = (String)jsonObject.get("humidity");
			String temp = (String)jsonObject.get("temp");
			String windVel = (String)jsonObject.get("windVel");
			String date = (String)jsonObject.get("date");
			String time = (String)jsonObject.get("time");

			//asignar variables
			this.operation = operation;
			this.id = id;
			this.city = city;
			this.humidity = humidity;
			this.temp = temp;
			this.windVel = windVel;
			this.date = date;
			this.time = time;

		}catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
