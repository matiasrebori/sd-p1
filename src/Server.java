import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class Server {
	ArrayList<Message> list;
	Integer puertoServidor;
	DatagramSocket serverSocket;
    byte[] receiveData;
    byte[] sendData;
	
	public Server( Integer serverPort)
	{
		list = new ArrayList<Message>();
		puertoServidor = serverPort;
		try {
			serverSocket = new DatagramSocket(puertoServidor);
		}catch( SocketException e){
			System.err.println(e);
		}
		
        //2) buffer de datos a enviar y recibir
        receiveData = new byte[1024];
        sendData = new byte[1024];
	}
	
	
	public void run()
	{
		while( true )
		{
			
	        receiveData = new byte[1024];
	
	        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	
	        System.out.println("Esperando a algun cliente... ");
	
	        // 4) Receive LLAMADA BLOQUEANTE
	        try {
	        	serverSocket.receive(receivePacket);
	        }catch(IOException e){
	        	System.err.println(e);
	        }
	        
			System.out.println("________________________________________________");
	        System.out.println("Aceptamos un paquete");
	        
	        // Datos recibidos e Identificamos quien nos envio
	        InetAddress IPAddress = receivePacket.getAddress();
	        int port = receivePacket.getPort();
	        
	        Message msg = new Message();
	        String datoRecibido = new String(receivePacket.getData());
	        System.out.println(datoRecibido);
	        msg.toMessage( datoRecibido.trim() );
	        Integer operation = Integer.parseInt( msg.getOperation() );
	        
			if ( operation.equals(1) )
			{
				list.add(msg);
				msg = new Message("1");
				sendMessage( msg , IPAddress , port);
				System.out.println( "datos metereologicos recibido operacion 1 ");
				
			}
			else if ( operation.equals(2) )
			{
				String city = msg.getCity();
				Message res = new Message();
			    for(Message m : list) {
			        if ( m.getCity().equals(city) ) {
			            res = m;
			        }
			    }
			    msg = new Message("2");
			    msg.setTemp( res.getTemp() );
			    msg.setCity( res.getCity() );
			    sendMessage( msg , IPAddress, port);
			    System.out.println( "datos metereologicos enviado operacion 2 ");
			}
			else if( operation.equals(3) )
			{
				Integer temperatura = 0;
				Integer contador = 0;
				String tempPromedio;
				String date = msg.getDate();
				
			    for(Message m : list) {
			        if ( m.getDate().equals(date) ) {
			            temperatura = temperatura + Integer.parseInt( m.getTemp() );
			            contador++;
			        }
			    }
			    tempPromedio = Integer.toString( temperatura / contador );
			    
			    msg = new Message("3");
			    msg.setTemp( tempPromedio );
			    msg.setDate( date );
			    sendMessage( msg , IPAddress, port);
			    System.out.println( "datos metereologicos enviado operacion 3 ");
			}
			else
			{
				System.out.println("Error de operacion");
			}
	        
		}
	}
	
	public void sendMessage( Message msg, InetAddress IPAddress , int port )
	{
        sendData = msg.toJSON().getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        
        try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	public static void main(String[] args)
	{
		Server servidor = new Server( 6666 );
		servidor.run();
		
		
	}
}
