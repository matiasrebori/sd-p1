import java.io.*;
import java.net.*;
public class Client {
	String direccionServidor;
	Integer puertoServidor;
	DatagramSocket clientSocket;
	InetAddress IPAddress;
	BufferedReader inFromUser;
	byte[] sendData;
	byte[] receiveData;
	
	public Client( String serverAddress, Integer serverPort ) {
		start( serverAddress , serverPort );
	}
    
    public void start( String serverAddress, Integer serverPort )
    {
    	try {
    	direccionServidor = serverAddress;
    	puertoServidor = serverPort;
    	clientSocket = new DatagramSocket();
    	IPAddress = InetAddress.getByName(direccionServidor);
    	inFromUser = new BufferedReader(new InputStreamReader(System.in));
    	sendData = new byte[1024];
        receiveData = new byte[1024];
    	} catch (UnknownHostException ex) {
             System.err.println(ex);
    	} catch (SocketException e) {
    		System.err.println(e);
		}
  
    }
	public void sendMessage() throws IOException
	{
		try {
			Message msg;
			System.out.println("Ingrese la operacion");
			String word = inFromUser.readLine();
			Integer operation = Integer.parseInt(word);
			if ( operation.equals(1) )
			{
				System.out.println("Ingrese id");
				String id = inFromUser.readLine();
				System.out.println("Ingrese ciudad");
				String city = inFromUser.readLine();
				System.out.println("Ingrese humedad");
				String humidity = inFromUser.readLine();
				System.out.println("Ingrese temperatura");
				String temp = inFromUser.readLine();
				System.out.println("Ingrese velocidad del viento");
				String windVel = inFromUser.readLine();
				System.out.println("Ingrese fecha");
				String date = inFromUser.readLine();
				System.out.println("Ingrese hora");
				String time = inFromUser.readLine();
				
				msg = new Message( word, id, city, humidity, temp, windVel, date, time);
				
				//enviar mensaje
		        String datoPaquete = msg.toJSON();
		        sendData = datoPaquete.getBytes();
		        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);
		        clientSocket.send(sendPacket);
			}
			else if ( operation.equals(2) )
			{
				System.out.println("Ingrese ciudad");
				String city = inFromUser.readLine();
				msg = new Message( word, city);
				
				//enviar mensaje
		        String datoPaquete = msg.toJSON();
		        sendData = datoPaquete.getBytes();
		        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);
		        clientSocket.send(sendPacket);
			}
			else if( operation.equals(3) )
			{
				msg = new Message( word);
				
				//enviar mensaje
		        String datoPaquete = msg.toJSON();
		        sendData = datoPaquete.getBytes();
		        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);
		        clientSocket.send(sendPacket);
			}
			else
			{
				System.out.println("Error de operacion");
			}
		}catch( IOException e ){
			System.err.println(e);
		}
	     
	}
	
	public void receiveMessage() throws IOException
	{
		 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
        clientSocket.setSoTimeout(10000);

        try {
        	Message msg = new Message();
            // ESPERAMOS LA RESPUESTA, BLOQUENTE
            clientSocket.receive(receivePacket);

            String respuesta = new String(receivePacket.getData());
            msg.toMessage( respuesta.trim() );
            
            Integer operation = Integer.parseInt( msg.getOperation() );
            if ( operation.equals(1) )
            {
            	System.out.println("Datos Recibidos" );
            }
            else if( operation.equals(2) )
            {
            	System.out.println("Temperatura en ciudad: " + msg.getCity() + " es igual a: " + msg.getTemp() );
            }
            else if( operation.equals(3) )
            {
            	System.out.println("Temperatura en fecha: " + msg.getDate() + " es igual a: " + msg.getTemp()  );
            }
            else
            {
            	System.out.println( "Error en mensaje recibido" );
            }
            

            

        } catch (SocketTimeoutException ste) {

            System.out.println("TimeOut: El paquete udp se asume perdido.");
        }
	}
	
	public void run() 
	{
		Boolean flag = true;
		while ( flag )
		{
			try {
				sendMessage();
				receiveMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	}
	
	public static void main(String[] args)
	{
		Client cliente = new Client("127.0.0.1" , 6666);
		cliente.run();
		
		
	}
}
