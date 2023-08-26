
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
/* import java.io.DataOutputStream;
import java.io.ObjectOutput; */
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

/**
 * The class "ClientePildoras" creates an instance of "MarcoCliente" and sets the default close
 * operation for the frame.
 */
public class ClientePildoras{
    public static void main(String[] args) {
    MarcoCliente mimarco=new MarcoCliente();
    mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
/**
 * The class MarcoCliente is a Java JFrame that creates a window with specific dimensions and adds a
 * LaminaMarcoCliente panel to it.
 */
class MarcoCliente extends JFrame{
    // The `public MarcoCliente()` method is the constructor for the `MarcoCliente` class. It sets the
    // bounds (position and size) of the frame, creates an instance of the `LaminaMarcoCliente` panel,
    // adds the panel to the frame, and makes the frame visible.
    public MarcoCliente(){
        setBounds(600, 300, 280, 350);
        LaminaMarcoCliente milamina=new LaminaMarcoCliente();
        add(milamina);
        setVisible(true);
    }
}  
/**
 * The class LaminaMarcoCliente is a JPanel that represents a chat interface with text fields for
 * nickname, IP address, chat messages, and a button to send messages.
 */
class LaminaMarcoCliente extends JPanel implements Runnable{
    // The `public LaminaMarcoCliente()` method is the constructor for the `LaminaMarcoCliente` class.
    // It initializes and adds various components to the panel, including text fields for nickname and
    // IP address, a label for the chat, a text area for displaying chat messages, a text field for
    // entering messages, a button for sending messages, and a thread for running the chat
    // functionality.
    public LaminaMarcoCliente(){
      nick=new JTextField(5);
      add(nick);
      ip=new JTextField(8);
      add(ip);
      texto=new JLabel("Chat1");
        add(texto);
        campochat=new JTextArea(12,20);
        add(campochat);
        campo1=new JTextField(20);
        add(campo1);
        miboton=new JButton("Enviar");
        EnviaTexto mievento=new EnviaTexto();
        miboton.addActionListener(mievento);
        add(miboton);
        Thread mihilo=new Thread(this);
        mihilo.start();
    }
/**
 * The EnviaTexto class is an ActionListener that sends text input to a server using a Socket and
 * ObjectOutputStream.
 */
    private class EnviaTexto implements ActionListener{
      public void actionPerformed(ActionEvent e){
      campochat.append("\n"+campo1.getText());
      try {
        // This code is creating a socket connection to a server with the IP address "127.0.0.1" and
        // port number 9999. It then creates an instance of the `paqueteEnvio` class, sets the
        // nickname, IP address, and message content using the values entered in the text fields, and
        // sends this object over the socket connection using an `ObjectOutputStream`. Finally, it
        // closes the socket connection.
        Socket misocket = new Socket("127.0.0.1", 9999);
        paqueteEnvio datos=new paqueteEnvio();
        datos.setNick(nick.getText());
        datos.setIp(ip.getText());
        datos.setMensaje(campo1.getText());
        ObjectOutputStream paquete_datos=new ObjectOutputStream(misocket.getOutputStream());
        paquete_datos.writeObject(datos);
        misocket.close();
        
      } catch (Exception e1) {
        // TODO: handle exception
        System.out.println(e1.getMessage());
      }  
                
      }  
    }

    private JTextField campo1,nick,ip;
    private JTextArea campochat;
    private JButton miboton;
    JLabel texto;
    @Override
    public void run() {
      // TODO Auto-generated method stub
     try {
      // This code is creating a server socket on port 9090 and listening for incoming client
      // connections. Once a client connection is established, it creates an `ObjectInputStream` to
      // read the incoming data from the client. It then reads an object of type `paqueteEnvio` from
      // the input stream, which represents a package containing a nickname and a message. Finally, it
      // appends the received nickname and message to the chat text area. This code is running in a
      // loop, continuously accepting and processing incoming client connections and messages.
      ServerSocket servidor_cliente=new ServerSocket(9090);
      Socket cliente;
      paqueteEnvio paqueteRecibido;
      while (true){
        cliente=servidor_cliente.accept();
        ObjectInputStream flujoentrada=new ObjectInputStream(cliente.getInputStream());
        paqueteRecibido=(paqueteEnvio) flujoentrada.readObject();
        campochat.append("\n"+paqueteRecibido.getNick()+": "+paqueteRecibido.getMensaje());
      }
     } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.getMessage());
     }
    }
}
/**
 * The class "paqueteEnvio" is a serializable class that represents a package for sending messages with
 * a nickname, IP address, and message content.
 */

class paqueteEnvio implements Serializable{
  private String nick,ip,mensaje;

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getNick() {
    return nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  
}