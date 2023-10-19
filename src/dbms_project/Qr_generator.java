/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbms_project;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.mysql.cj.jdbc.Blob;
import java.awt.Image;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/*
 *
 * @author User
 */
public class Qr_generator {
    public static void main(String args[])
    {
        byte[] photo=null;
        try
        {
            
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_mini_pro","root","sa123");
            Statement st=con.createStatement();
            
            
//            File file1=new File("qr1.png");
//            String content="I am the Destroyer!!!";
//            
//            // To generate QR code and strore it in PNG file
//            ByteArrayOutputStream out=QRCode.from(content).to(ImageType.PNG).stream();
//            
//            FileOutputStream fos1=new FileOutputStream(file1);
//            fos1.write(out.toByteArray());
            
            
 
            //  To generate Qr codes in loop
            System.out.println("Enter Number of tickets to be generated : ");
            Scanner sc=new Scanner(System.in);
            int n=sc.nextInt();
            sc.nextLine();
            
            File file[]=new File[n];
            ByteArrayOutputStream baos[]=new ByteArrayOutputStream[n];
            FileOutputStream fos[]=new FileOutputStream[n];
            
            for(int i=0;i<n;i++)
            {
                //System.out.println("hi");
                file[i]=new File("QR"+(i+1)+".png");
                System.out.println("Enter Content of QR "+(i+1)+" : ");
                
                String s=sc.nextLine();
                
//                System.out.println("bye");
                baos[i]=QRCode.from("Verification COde : "+s).to(ImageType.PNG).stream();
                fos[i]=new FileOutputStream(file[i]);
                fos[i].write(baos[i].toByteArray());
                
                System.out.println("QR is generated!!!");
                
                
                
                
                
                
                // To store qr code file in database
                
                
                
                //
                try{
//                    System.out.println("Hi");
                    FileInputStream fis=new FileInputStream(file[i]);
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    byte[] buf=new byte[1024];
                    
//                    System.out.println("Hello");
                    for(int readnum;(readnum=fis.read(buf))!=-1;)
                    {
                        bos.write(buf,0,readnum);
                    }
                    
                    photo=bos.toByteArray();
                    
                    System.out.println("bye");
                    
                    String str="insert into qr_codes(qr_img) values('photo')";                  
                    st.executeUpdate(str);
                    
                    System.out.println("Photo is saved!!");
                   
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
            
            
            
            // To fetch image from the images stored in database 
            ImageIcon icon=null;
                   
            
            try
            {
                String s="select qr_img from qr_codes order by rand() limit 1";
                ResultSet resset=st.executeQuery(s);
                
                if(resset.next())
                {
                    Blob b=(Blob) resset.getBlob("qr_img");
                    InputStream is=b.getBinaryStream();
                    Image image=ImageIO.read(is);
                    icon=new ImageIcon(image);
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            
            System.out.println("Image is fetched!!");
            
            
            
            
            
            
            
////              To choose random QR code and read it...

////            JFileChooser ch=new JFileChooser();
////            
////            ch.showOpenDialog(null);
////            File file3=ch.getSelectedFile();
////            
////            if(file3==null)
////            {
////                throw new Exception("Invalid QR!!!");
////            }
//            
//            






//            // How to read QR code
//            
////            BufferedImageLuminanceSource bils=new BufferedImageLuminanceSource(ImageIO.read(file3));
////            HybridBinarizer hbz=new HybridBinarizer(bils);
////            BinaryBitmap bbm=new BinaryBitmap(hbz);
////            Result rs= new MultiFormatReader().decode(bbm);
////            
////            System.out.println(rs.getText());
////            
//            
//           // To show the QR code content in message Dialog box!!
//           
////           JOptionPane.showMessageDialog(null, rs.getText());
//            
////            JOptionPane.showMessageDialog(null, rs.getText(),"QR Content!!!",JOptionPane.INFORMATION_MESSAGE, new ImageIcon(file3.getAbsolutePath()));
//            
//            
//            
//            
//            
//            
//            
//            
//            // to close the file
//            for(int i=0;i<n;i++)
//            {
//                fos[i].close();
//            }
//            
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
