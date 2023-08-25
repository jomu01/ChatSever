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

public class ClientePildoras{
    public static void main(String[] args) {
    MarcoCliente mimarco=new MarcoCliente();
    mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
class MarcoCliente extends JFrame{
    public MarcoCliente(){
        setBounds(600, 300, 280, 350);
        LaminaMarcoCliente milamina=new LaminaMarcoCliente();
        add(milamina);
        setVisible(true);
    }
}  
class LaminaMarcoCliente extends JPanel implements Runnable{
    public LaminaMarcoCliente(){
      nick=new JTextField(5);
      add(nick);
      ip=new JTextField(8);
      add(ip);
      texto=new JLabel("Chat");
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
    private class EnviaTexto implements ActionListener{
      public void actionPerformed(ActionEvent e){
      try {
        Socket misocket = new Socket("127.0.0.1", 9999);
        paqueteEnvio datos=new paqueteEnvio();
        datos.setNick(nick.getText());
        datos.setIp(ip.getText());
        datos.setMensaje(campo1.getText());
        ObjectOutputStream paquete_datos=new ObjectOutputStream(misocket.getOutputStream());
        paquete_datos.writeObject(datos);
        misocket.close();
        /* DataOutputStream flujo_salida=new DataOutputStream(misocket.getOutputStream());
        flujo_salida.writeUTF(campo1.getText());
        flujo_salida.close();
        //misocket.close(); */
      } catch (Exception e1) {
        // TODO: handle exception
        System.out.println(e1.getMessage());
      }  
                
        //System.out.println(campo1.getX());
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