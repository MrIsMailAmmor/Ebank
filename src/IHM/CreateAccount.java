package IHM;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.awt.BorderLayout;

import javax.swing.*;

public class CreateAccount implements ActionListener {
    private JButton registerButton, quitter;
    private String fName, lName, eMail, sexe;
    private String phoneN, birthDate;
    private JComboBox<String> genderList;
    private JTextField firstNameTextField, lastNameTextField, ageTextField, emailTextField, numTextField;
    private JFrame frame;

    public CreateAccount() {
        Instatiate();
    }

    public void Instatiate() {
        CreateAccInterface();
    }

    public void CreateAccInterface() {
        frame = new JFrame();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Registration Form");
        JPanel titlePanel = new JPanel();
        titlePanel.add(title);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Content Panel Center
        JPanel centerPanel = new JPanel(new SpringLayout());

        JLabel firstNameLabel = new JLabel("First Name",JLabel.RIGHT);
        firstNameTextField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name",JLabel.RIGHT);
        lastNameTextField = new JTextField();

        JLabel genderLabel = new JLabel("Gendre",JLabel.RIGHT);
        String[] element = new String[] { "Male", "Female" };
        genderList = new JComboBox<String>(element);

        JLabel ageLabel = new JLabel("Birth Date",JLabel.RIGHT);
        ageTextField = new JTextField();

        JLabel emailLabel = new JLabel("Email",JLabel.RIGHT);
        emailTextField = new JTextField();

        JLabel numLabel = new JLabel("Phone",JLabel.RIGHT);
        numTextField = new JTextField();

        JLabel labelGrp[] = { firstNameLabel, lastNameLabel, emailLabel, ageLabel, genderLabel, numLabel, title };
        Font serif = new Font("monospaced", Font.BOLD, 16);
        Color bgColor = new Color(223, 154, 87);
        Color fontColor = new Color(33, 37, 41);
        Color bgBtn = new Color(94, 91, 82);
        for (int i = 0; i < labelGrp.length; i++) {
            labelGrp[i].setFont(serif);
            labelGrp[i].setForeground(fontColor);
        }

        quitter = new JButton("Quit");
        registerButton = new JButton("Register");

        JButton btnGrp[] = { quitter, registerButton };
        JPanel buttonSouth = new JPanel();
        for (int i = 0; i < btnGrp.length; i++) {
            btnGrp[i].addActionListener(this);
            buttonSouth.add(btnGrp[i]);
            btnGrp[i].setBackground(bgBtn);
            btnGrp[i].setForeground(bgColor);
            btnGrp[i].setFont(serif);
        }
        // JPanel panelGrp[] = { firstNamePanel, lastNamePanel, genderPanel, agePanel, emailPanel, numPanel };
        // for (int i = 0; i < panelGrp.length; i++) {
        //     centerPanel.add(panelGrp[i]);
        //     panelGrp[i].setBackground(bgColor);
        // }

        centerPanel.add(firstNameLabel);
        centerPanel.add(firstNameTextField);
        centerPanel.add(lastNameLabel);
        centerPanel.add(lastNameTextField);
        centerPanel.add(genderLabel);
        centerPanel.add(genderList);
        centerPanel.add(ageLabel);
        centerPanel.add(ageTextField);
        centerPanel.add(emailLabel);
        centerPanel.add(emailTextField);
        centerPanel.add(numLabel);
        centerPanel.add(numTextField);

        SpringUtilities.makeCompactGrid(centerPanel, //parent
                                6, 2,
                                20, 20,  //initX, initY
                                15, 20); //xPad, yPad
        JPanel panelGrpStyle[] = {titlePanel,centerPanel,buttonSouth};
        for(int i = 0; i< panelGrpStyle.length; i++){
            panelGrpStyle[i].setBackground(bgColor);
        }
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(buttonSouth, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setSize(400, 400);
        frame.setTitle("Register");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon logo = new ImageIcon("register.png");
        frame.setIconImage(logo.getImage());
    }

    public void infoRegistration() {

        try {
            fName = firstNameTextField.getText();
            lName = lastNameTextField.getText();
            eMail = emailTextField.getText();
            sexe = String.valueOf(genderList.getSelectedItem());
            phoneN = numTextField.getText();
            birthDate = ageTextField.getText();
            FileWriter fr = new FileWriter(fName);
            BufferedWriter bw = new BufferedWriter(fr);
            try {
                bw.write(fName);
                bw.newLine();
                bw.write(lName);
                bw.newLine();
                bw.write(eMail);
                bw.newLine();
                bw.write(sexe);
                bw.newLine();
                bw.write(phoneN);
                bw.newLine();
                bw.write(birthDate);
                bw.newLine();
                bw.write("0");

                System.out.println("file Created successfully !");

            } catch (Exception a) {
                System.out.println("error ! : " + a.getMessage());
            } finally {
                bw.close();
                fr.close();
                System.out.println("file has been closed");
            }
            System.out.println("first name : " + fName + "\nlast name : " + lName + "\nemail: " + eMail + "\nsexe  : "
                    + sexe + "\nPhone Number : " + phoneN + "\nBirth Day : " + birthDate);
            JOptionPane.showMessageDialog(frame, "enregistrement bien fait");
            frame.dispose();
        } catch (Exception a) {
            JOptionPane.showMessageDialog(frame, "error : " + a.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            infoRegistration();
        }
        if (e.getSource() == quitter) {
            System.out.println("vous avez quitter");
            frame.dispose();
            new EbankInterface();

        }
    }
}