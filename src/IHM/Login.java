package IHM;

import static org.junit.Assert.assertEquals;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.junit.Test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Login implements ActionListener {
    protected String userName, passWord;
    private JButton login, quit;
    private JTextField userNamTextField, passworTextField;
    private JFrame frame;
    private FileReader fr;
    private BufferedReader br;
    private String[] personalData;
    protected String username, password,message;
    private boolean logged;

    public Login() {
        instatiate();
    }

    public void instatiate() {
        createPanel();
        personalData = new String[6];

    }

    public void createPanel() {

        frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel title = new JLabel("Login", JLabel.CENTER);
        panel.setLayout(new BorderLayout());
        panel.add(title, BorderLayout.NORTH);

        JLabel userNameLabel = new JLabel("UserName");
        userNamTextField = new JTextField();
        userNamTextField.setMaximumSize(new Dimension(10, 10));

        JLabel passwordLabel = new JLabel("Password");
        passworTextField = new JTextField();
        passworTextField.setMaximumSize(new Dimension(10, 10));

        JPanel panelCentre = new JPanel(new SpringLayout());
        panelCentre.add(userNameLabel);
        panelCentre.add(userNamTextField);
        panelCentre.add(passwordLabel);
        panelCentre.add(passworTextField);

        SpringUtilities.makeCompactGrid(panelCentre, // parent
                2, 2, 3, 3, // initX, initY
                3, 20); // xPad, yPad

        // Button Side
        login = new JButton("Login");
        quit = new JButton("Quit");
        login.addActionListener(this);
        quit.addActionListener(this);
        
        JPanel panelBtn = new JPanel();
        panelBtn.add(quit);
        panelBtn.add(login);
        panel.add(panelBtn, BorderLayout.SOUTH);
        panel.add(panelCentre, BorderLayout.CENTER);

        // Panel Design
        Font textFont = new Font("monospaced", Font.BOLD, 18);
        Color fontColor = new Color(74, 72, 71);
        Color bgColor = new Color(209, 161, 12);
        JPanel panelGrp[] = new JPanel[] { panel, panelCentre, panelBtn };

        for (int i = 0; i < panelGrp.length; i++) {
            panelGrp[i].setBackground(bgColor);
            panelGrp[i].setFont(textFont);
        }

        JLabel labelGrp[] = new JLabel[] { title, userNameLabel, passwordLabel };
        for (int i = 0; i < labelGrp.length; i++) {
            labelGrp[i].setFont(textFont);
            labelGrp[i].setForeground(fontColor);
            labelGrp[i].setBorder(new EmptyBorder(10, 10, 10, 10));

        }

        JButton btnGrp[] = new JButton[] { login, quit };
        for (int i = 0; i < btnGrp.length; i++) {
            btnGrp[i].setFont(textFont);
            btnGrp[i].setForeground(bgColor);
            btnGrp[i].setBackground(fontColor);

            btnGrp[i].setBorder(new EmptyBorder(10, 10, 10, 10));
        }
        // Frame Side
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(400, 300);
        frame.setTitle("Login");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon logo = new ImageIcon("login.png");
        frame.setIconImage(logo.getImage());

    }

    public String userCheck() throws IOException {
        username = personalData[0];
        return username;
    }

    public String passCheck() {
        password = personalData[1];
        return password;
    }

    public void readFile() throws IOException {
        fr = new FileReader(userName);
        br = new BufferedReader(fr);
        String line = br.readLine();
        int i = 0;
        try {
            while (line != null) {
                // System.out.println(line + " " + i);
                personalData[i] = line;
                line = br.readLine();
                i++;
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            br.close();
            fr.close();
        }
    }

    public boolean loginProcess() {
        userName = userNamTextField.getText();
        passWord = passworTextField.getText();
        try {
            logged = false;
            readFile();
            String user = userCheck();
            String pass = passCheck();
            // System.out.println(user);
            // System.out.println(pass);
            if (userName.equals(user) && passWord.equals(pass)) {
                System.out.println("logged Successfuly");
                System.out.println("Welcome To Your Account ! " + userName);
                logged = true;
                message = "You successfully logged in";
                JOptionPane.showMessageDialog(frame, message);
            } else {
                // System.out.println("username or password is not correct !");
                message = "username or password is not correct ! ";
                JOptionPane.showMessageDialog(frame, message);
            }
        } catch (FileNotFoundException e2) {
            message = "User not found ";
            JOptionPane.showMessageDialog(frame, message);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return logged;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == login) {
            if (loginProcess()) {
                try {
                    new UserInterface(username);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }

        }
        if (e.getSource() == quit) {
            System.out.println("Quitting ... !");
            frame.dispose();
            new EbankInterface();
        }

    }

    public int testMethode(int a, int b) {
        int c = a + b;
        return c;
    }

    @Test
    public void testLogin() throws IOException {
        assertEquals(false, loginProcess());
        assertEquals(null, passCheck());
        assertEquals(10, testMethode(5, 5));
        assertEquals(null, userCheck());

    }
}
