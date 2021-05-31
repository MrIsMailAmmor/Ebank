package IHM;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EbankInterface implements ActionListener {

    private JButton login, register, quit;
    private JFrame frame;
    // private  image;

    public EbankInterface() {
        instantiate();
    }

    public void instantiate() {
        createPanel();

    }

    public void createPanel() {

        frame = new JFrame();
        JPanel panel = new JPanel(new BorderLayout());

        login = new JButton("Login");
        register = new JButton("Register");
        quit = new JButton("Quit");
        JLabel title = new JLabel("Welcome To Ebank", JLabel.CENTER);

       
        ImageIcon icon = new ImageIcon("Ebank.jpg");
        JLabel iconLabel = new JLabel(icon);
        JPanel btnPanel = new JPanel(new GridLayout(5, 1, 50, 10));
        Color btnColor = new Color(32, 32, 32);
        Color btnColorFont = new Color(255, 136, 0);
        Color bgColor = new Color(255, 180, 0);
        Font serif = new Font("Plain", Font.BOLD, 18);
//coloring
        title.setFont(serif);
        title.setForeground(btnColor);
        title.setBackground(btnColorFont);

        title.setPreferredSize(new Dimension(40,50));
        JButton btnGrp[] = { login, register, quit };
        for (int i = 0; i < btnGrp.length; i++) {
            btnPanel.add(btnGrp[i]);
            btnGrp[i].setFont(serif);
            btnGrp[i].addActionListener(this);
            btnGrp[i].setBackground(btnColor);
            btnGrp[i].setForeground(btnColorFont);

        }
        btnPanel.setLayout(new GridLayout(3,1,10,10));
        panel.setBackground(bgColor);
        btnPanel.setBackground(bgColor);


        // Frame Side
        panel.add(iconLabel,BorderLayout.CENTER);
        panel.add(title, BorderLayout.NORTH);
        panel.add(btnPanel, BorderLayout.WEST);
        frame.add(panel);

        ImageIcon logo = new ImageIcon("bank.png");
        frame.setIconImage(logo.getImage());
        frame.setVisible(true);
        frame.setSize(800, 300);
        frame.setTitle("Login");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            frame.dispose();
            new Login();
        }
        if (e.getSource() == register) {
            new CreateAccount();
            frame.dispose();
        }
        if (e.getSource() == quit) {
            frame.dispose();
        }

    }
}