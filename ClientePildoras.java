import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
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
class LaminaMarcoCliente extends JPanel{
    public LaminaMarcoCliente(){
        texto=new JLabel("Cliente");
        add(texto);
        campo1=new JTextField(20);
        add(campo1);
        miboton=new JButton("Enviar");
        EnviaTexto mievento=new EnviaTexto();
        miboton.addActionListener(mievento);
        add(miboton);
    }
    private class EnviaTexto implements ActionListener{
      public void actionPerformed(ActionEvent e){
      try {
        Socket misocket = new Socket("192.168.100.68", 9999);
        DataOutputStream flujo_salida=new DataOutputStream(misocket.getOutputStream());
        flujo_salida.writeUTF(campo1.getText());
        flujo_salida.close();
        //misocket.close();
      } catch (Exception e1) {
        // TODO: handle exception
        System.out.println(e1.getMessage());
      }  
                
        //System.out.println(campo1.getX());
      }  
    }
    private JTextField campo1;
    private JButton miboton;
    JLabel texto;
}
