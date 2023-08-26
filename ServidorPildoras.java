
import java.awt.BorderLayout;
//import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
//import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
 * The class "ServidorPildoras" creates an instance of "MarcoServidor" and sets the default close
 * operation for the frame.
 */

public class ServidorPildoras{
    public static void main(String[] args) {
        MarcoServidor micarco=new MarcoServidor();
        micarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
/**
 * The `MarcoServidor` class is a Java class that represents a server application with a graphical user
 * interface for receiving and forwarding messages between clients.
 */

class  MarcoServidor extends JFrame implements Runnable{
    public MarcoServidor(){
        setBounds(1200 , 300, 280, 350);
        
        // This code block is creating a JPanel called "milamina" and setting its layout to
        // BorderLayout. It then creates a JTextArea called "areatexto". The JTextArea is added to the
        // JPanel using BorderLayout.CENTER, which means it will be placed in the center of the panel.
        // The JPanel is then added to the JFrame using the add() method. Finally, the JFrame is set to
        // be visible, and a new Thread is created and started using the current instance of the class
        // as the Runnable.
        JPanel milamina=new JPanel();
        milamina.setLayout(new BorderLayout());
        areatexto=new JTextArea();
        milamina.add(areatexto,BorderLayout.CENTER);
        add(milamina);
        setVisible(true);
        Thread mihilo=new Thread(this);
        mihilo.start();
    }
    private JTextArea areatexto;
    @Override
    // The `run()` method is the main method that will be executed when the thread starts. It contains
    // the logic for the server to receive and forward messages between clients.
    public void run() {
        // TODO Auto-generated method stub
        try {
            ServerSocket servidor=new ServerSocket(9999);
            String nick,ip,mensaje;
            paqueteEnvio paquete_recibido;

            // The code block inside the `while(true)` loop is the main logic for the server to receive
            // and forward messages between clients.
            while(true){
                Socket misocket=servidor.accept();
                ObjectInputStream paquete_datos=new ObjectInputStream(misocket.getInputStream());
                paquete_recibido=(paqueteEnvio)paquete_datos.readObject();
                nick=paquete_recibido.getNick();
                ip=paquete_recibido.getIp();
                mensaje=paquete_recibido.getMensaje();
                areatexto.append("\n"+nick+":"+mensaje+"para"+ip);
                Socket enviaDestinatario=new Socket(ip,9090);
                ObjectOutputStream paqueteReenvio=new ObjectOutputStream(enviaDestinatario.getOutputStream());
                paqueteReenvio.writeObject(paquete_recibido);
                paqueteReenvio.close();
                enviaDestinatario.close();
                misocket.close();
             }
        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}