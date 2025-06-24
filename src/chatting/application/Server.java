package chatting.application;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

import javax.swing.*;
import javax.swing.border.*;

import java.net.*;
import java.io.*;

public class Server implements ActionListener {
	
	JPanel t1;
	JTextField text;
	static Box vertical = Box.createVerticalBox();
	static JFrame f = new JFrame();
	static DataOutputStream dos;
	
	Server() {
		
		f.setLayout(null);
		
		JPanel p1 =new JPanel();
		p1.setBackground(new Color(12, 150, 134));
		p1.setBounds(0, 0, 450, 70);
		p1.setLayout(null);
		f.add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel back = new JLabel(i3);
		back.setBounds(5, 20, 25, 25);
		p1.add(back);
		
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked (MouseEvent ae) {
				System.exit(0);
			}
		});
		
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
		Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(40, 10, 50, 50);
		p1.add(profile);
		
		
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel call = new JLabel(i9);
		call.setBounds(300, 20, 30, 30);
		p1.add(call);
		
		
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel vdo = new JLabel(i12);
		vdo.setBounds(350, 20, 30, 30);
		p1.add(vdo);
		
		
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/option.png"));
		Image i14 = i13.getImage().getScaledInstance(15, 25, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel opt = new JLabel(i15);
		opt.setBounds(400, 22, 15, 25);
		p1.add(opt);
		
		
		JLabel name = new JLabel("Thettayi");
		name.setBounds(110, 20, 100, 25);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN-SERIF", Font.ROMAN_BASELINE, 20));
		p1.add(name);
		
		
		JLabel status = new JLabel("Online");
		status.setBounds(110, 40, 100, 25);
		status.setForeground(Color.GREEN);
		status.setFont(new Font("SAN-SERIF", Font.ITALIC, 10));
		p1.add(status);
		
		
		
		
		
		t1 = new JPanel();
		t1.setBounds(5, 75, 440, 575);
		f.add(t1);
		
		
		text = new JTextField();
		text.setBounds(5, 655, 330, 40);
		text.setFont(new Font("SAN-SERIF", Font.PLAIN, 16));
		f.add(text);
		
		
		ImageIcon i16 = new ImageIcon(ClassLoader.getSystemResource("icons/send.png"));
		Image i17 = i16.getImage().getScaledInstance(55, 40, Image.SCALE_SMOOTH);
		ImageIcon i18 = new ImageIcon(i17);
		JButton send = new JButton(i18);
		send.setBounds(390, 655, 55, 40);
		send.setBorderPainted(true);
		send.setContentAreaFilled(false);
		
		send.addActionListener(this);
		
		f.add(send);
		
		
		
		f.setUndecorated(true);
		f.setSize(450,700);
		f.setLocation(200, 50);
		f.getContentPane().setBackground(Color.gray);
		
		
		f.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent ae) {
		
		try {
			String out = text.getText();
			
			
			JPanel p2 = formatLabel(out);
			
			t1.setLayout(new BorderLayout());
			
			JPanel right = new JPanel(new BorderLayout());
			right.add(p2, BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));
			
			t1.add(vertical, BorderLayout.PAGE_START);
			
			dos.writeUTF(out);
			
			text.setText("");
			
			
			f.repaint();
			f.invalidate();
			f.validate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
	}
	
	public static JPanel formatLabel(String out) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width:150px\">" + out +"</p></html>");
		output.setFont(new Font("Tahoma", Font.PLAIN, 16));
		output.setBackground(new Color(16, 204, 60));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15, 15, 15, 50));
		
		panel.add(output);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		panel.add(time);
		
		return panel;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server();
		
		try {
			ServerSocket skt = new ServerSocket(6001);
			while(true) {
				Socket s = skt.accept();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				
				while(true) {
					String msg = dis.readUTF();
					JPanel panel = formatLabel(msg);
					
					JPanel left = new JPanel(new BorderLayout());
					left.add(panel, BorderLayout.LINE_START);
					vertical.add(left);
					f.validate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
