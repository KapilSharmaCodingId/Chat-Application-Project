import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.DeflaterOutputStream;
public class User2  implements ActionListener{
    
    JTextField text;
    static JPanel  a1 ;
    static Box vertical = Box.createVerticalBox();   // to show our masseges in verticaly one upon one
    static DataOutputStream send;
    static  JFrame f = new JFrame();
        public User2(){
            f.setLayout(null);    // it is used to layouts

            JPanel p1 = new JPanel();          //jpnael represent the uper part of frame
            p1.setBackground(new Color(7 , 94 ,84));   //setBackground is used to set the background color
            p1.setBounds(0,0,450,70);      // set the any contant in anywhere
            p1.setLayout(null);
            f.add(p1);         // add is used to add component in frame


             ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/ka.png"));  //to take the image from file 
             Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
             ImageIcon i3 = new ImageIcon(i2);
            JLabel back = new JLabel(i3);        
            back.setBounds(5,20,25,25);
            p1.add(back);                //to add image in panel

            back.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent ae){
                    System.exit(0);
                }
            });

            ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("images/2.png"));  //to take the image from file 
             Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
             ImageIcon i6 = new ImageIcon(i5);
            JLabel profile = new JLabel(i6);        
            profile.setBounds(40,10,50,50);
            p1.add(profile); 

            ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("images/video.png"));  //to take the image from file 
             Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
             ImageIcon i9 = new ImageIcon(i8);
            JLabel video = new JLabel(i9);        
            video.setBounds(300,20,30,30);
            p1.add(video); 

            ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("images/phone.png"));  //to take the image from file 
             Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
             ImageIcon i12 = new ImageIcon(i11);
            JLabel phone = new JLabel(i12);        
            phone.setBounds(360,20,30,30);
            p1.add(phone); 

            ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("images/3icon.png"));  //to take the image from file 
            Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
            ImageIcon i15 = new ImageIcon(i14);
           JLabel option = new JLabel(i15);        
           option.setBounds(420,20,10,25);
           p1.add(option); 
 
           JLabel name = new JLabel("Raman");    //add any name or content in out frame 
           name.setBounds(110,15,100,25);
           name.setForeground(Color.white);       //set the text color
           name.setFont(new Font("SAN_SERIF",Font.BOLD,20));  // increase the font size and also wrint font family
           p1.add(name);

           JLabel status = new JLabel("Active now");
           status.setBounds(110,35,100,25);
           status.setForeground(Color.white);
           name.setFont(new Font("SAN_SERIF",Font.BOLD,14));
           p1.add(status);

             a1 = new JPanel();
        //    new JPanel();
           a1.setBounds(5,75,440,490);
           f.add(a1);

        //    new JTextField();
            text =  new JTextField();                //to create the textField
            text.setBounds(5,565,350,35);
            text.setFont(new Font("SAN_SERIF" , Font.PLAIN,16));
           f. add(text);
            
            
           
            JButton send = new JButton("Send");
            send.setBounds(350,565,110,35);
            send.setBackground(new Color(7,94,84));
            send.setFont(new Font("SAN_SERIF" , Font.PLAIN,16));
            send.addActionListener(this);
            send.setForeground(Color.WHITE);
            f.add(send);


            f.setSize(450,600);   //set the frame size
            f.setLocation(800,50);          // to set the location of frame
            f.setUndecorated(true);  // revmove the heder
            f.getContentPane().setBackground(Color.WHITE); // getcontentpane is to select the frame

            f.setVisible(true);            //frame is bydefault hidden of we use setvisible for visibility
        }

        public void actionPerformed(ActionEvent ae){
            try{

                String out = text.getText();
                
                JPanel p2 = formatLable(out);
                
                
                a1.setLayout(new BorderLayout());
                
                JPanel right = new JPanel(new BorderLayout());
                right.add(p2,BorderLayout.LINE_END);
                
                vertical.add(right);
                vertical.add(Box.createVerticalStrut(12));
                a1.add(vertical,BorderLayout.PAGE_START);
                send.writeUTF(out);
                text.setText("");
                f.repaint();
                f.invalidate();
                f.validate();
                
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public static JPanel formatLable(String out){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel output = new JLabel(out);
            output.setFont(new Font("Tahoma",Font.PLAIN,18));
            output.setBackground(new Color(37,211,102));
            output.setOpaque(true);
            output.setBorder(new EmptyBorder(5,9,5,20));
            panel.add(output);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            JLabel time = new JLabel();
            time.setText(sdf.format(cal.getTime()));

            panel.add(time);

           return panel;
        }
    public static void main(String[] args) {
         new User2();

         try{
            Socket s = new Socket("127.0.01",12084);
            DataInputStream recive = new DataInputStream(s.getInputStream());  // messege recive
            send = new DataOutputStream(s.getOutputStream()); //send the messege

            while(true){
                a1.setLayout(new BorderLayout());
                String msg = recive.readUTF();
                JPanel panel = formatLable(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(12));
                a1.add(vertical,BorderLayout.PAGE_START);
                f.validate();
            }
         }catch(Exception e){
            e.printStackTrace();
         }
    }
}
