package IHM;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Thread.SendingTime;

public class UserInterface implements ActionListener {

    private JLabel title;
    private JFrame frame;
    private JPanel panel, leftPanel, centerPanel;
    private JButton withDraw, deposit, accountHistory, sendMoney, printInfo, spareAccount, sendMoneyBtn;

    private JButton withdrawBtn, depositBtn;
    private JTextField withDrawField, depositField, destinationMoney, sendMoneyTextField;
    private JLabel balance, destinationPerson;
    private String userArg;

    private String personalData[], message;
    private boolean haveSpareAccount, canSendMoney;

    private FileReader fr;
    private BufferedReader br;
    private FileWriter fw;
    private BufferedWriter bw;

    private HashMap<String, String> mapInfo;
    private double accountBalance, depositMoney, withdrawUser, moneyAmountSent;

    private Color bgColor, bgColor2, fontColor;
    private Font serif, serif2;
    private ArrayList<String> history;

    public UserInterface(String user) throws IOException {
        userArg = user;
        instantiate();

    }

    public void instantiate() throws IOException {
        personalData = new String[7];
        System.out.println("welcome to your Account management : " + userArg.toUpperCase());
        history = new ArrayList<String>();
        haveSpareAccount = false;
        mapInfo = new HashMap<String, String>();
        fontColor = new Color(188, 108, 37);
        bgColor = new Color(0, 0, 0);
        bgColor2 = new Color(203, 223, 189);
        serif2 = new Font("monospaced", Font.BOLD, 16);
        serif = new Font("Plain", Font.BOLD, 16);
        readfile(userArg);
        accountBalance = Double.valueOf(personalData[6]);
        userInterfaceFrame();
    }

    public void userInterfaceFrame() {

        frame = new JFrame();
        leftPanel = new JPanel(new GridLayout(6, 1, 5, 10));
        centerPanel = new JPanel();
        panel = new JPanel(new BorderLayout());

        title = new JLabel("User Account : " + userArg.toUpperCase(), JLabel.CENTER);
        withDraw = new JButton("WithDraw");
        deposit = new JButton("Deposit");
        accountHistory = new JButton("Check my history");
        sendMoney = new JButton("Send Money");
        printInfo = new JButton("Print my balance");
        spareAccount = new JButton("My Spare Account");

        JButton array[] = { withDraw, deposit, accountHistory, sendMoney, printInfo, spareAccount };
        for (int i = 0; i < array.length; i++) {
            array[i].addActionListener(this);
            leftPanel.add(array[i]);
            array[i].setFont(serif);
            array[i].setBackground(bgColor);
            array[i].setForeground(fontColor);
        }

        JPanel panelGrp[] = { leftPanel, panel, centerPanel };
        for (int i = 0; i < panelGrp.length; i++) {
            panelGrp[i].setBackground(bgColor2);
        }

        title.setFont(serif);
        title.setForeground(Color.BLACK);
        title.setBorder(new EmptyBorder(15, 0, 15, 0));
        panel.add(title, BorderLayout.NORTH);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(centerPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(900, 400);
        frame.setTitle("User Interface ");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon logo = new ImageIcon("user.png");
        frame.setIconImage(logo.getImage());

    }

    public void readfile(String userArg) throws IOException {
        fr = new FileReader(userArg);
        br = new BufferedReader(fr);
        String line = br.readLine();
        int i = 0;
        try {
            while (line != null) {
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

    public void updateUser() throws IOException {
        FileWriter fw = new FileWriter(userArg);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int i = 0; i < personalData.length; i++) {
            bw.write(personalData[i]);
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    public void withDraw() {
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel withDrawPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        withDrawField = new JTextField();
        withdrawBtn = new JButton("WithDraw");
        withdrawBtn.addActionListener(this);
        balance = new JLabel("Your balance is : " + accountBalance + "€");

        withdrawBtn.setBackground(new Color(208, 0, 0));
        withdrawBtn.setForeground(Color.white);
        balance.setFont(serif2);

        withDrawPanel.add(withDrawField);
        withDrawPanel.add(withdrawBtn);
        withDrawPanel.setBackground(bgColor2);
        JPanel balancePanel = new JPanel();
        balancePanel.setBackground(bgColor2);
        balancePanel.add(balance);
        withDrawPanel.add(balancePanel);

        centerPanel.add(withDrawPanel, gbc);
        centerPanel.setBackground(bgColor2);
    }

    public void withDrawUser() throws IOException {
        withdrawUser = Double.parseDouble(withDrawField.getText());
        if (accountBalance > 0) {
            if (accountBalance >= withdrawUser) {
                accountBalance = accountBalance - withdrawUser;
                // System.out.println(accountBalance);
                balance.setText("your balance is : " + accountBalance + "€");
                refrechFrame();
                writeHistory("Withdrawn : ", withdrawUser);
            } else {
                balance.setText("Something went wrong");
                refrechFrame();
            }

        } else {
            balance.setText("you don't have enough money");
            refrechFrame();
        }
    }

    public void deposit() {
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel depositPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        depositField = new JTextField();
        depositBtn = new JButton("Deposit");
        depositBtn.addActionListener(this);
        balance = new JLabel("Your balance is : " + accountBalance + "€");
        balance.setFont(serif2);

        depositBtn.setBackground(new Color(40, 54, 24));
        depositBtn.setForeground(Color.white);

        depositPanel.add(depositField);
        depositPanel.add(depositBtn);
        depositPanel.setBackground(bgColor2);

        JPanel balancePanel = new JPanel();
        balancePanel.add(balance);
        depositPanel.add(balancePanel);

        balancePanel.setBackground(bgColor2);

        centerPanel.add(depositPanel, gbc);

    }

    public void depositUser() {
        depositMoney = Double.parseDouble(depositField.getText());
        if(depositMoney>0){
            accountBalance = accountBalance + depositMoney;
            balance.setText("Your balance is : " + accountBalance + "€");
        }else{
            balance.setText("the amount is not valid");
        }
        

        // System.out.println(depositMoney);
        refrechFrame();
    }

    public void showHistory() throws IOException {
        history.clear();
        try {
            fr = new FileReader(userArg + "History");
            br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                history.add(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            br.close();
            fr.close();
        }
        message = "your history :\n";
        int size = history.size();
        if (size > 10) {
            for (int i = size - 1; i >= size - 10; i--) {
                // System.out.println(history.get(i));
                message += history.get(i) + "\n";
            }
            JOptionPane.showMessageDialog(frame, message);
        } else {
            message = "you have less than 10 operation in your account, if you want to check your history please do at least 10 operation";
            JOptionPane.showMessageDialog(frame, message);
        }

    }

    public void cleanInterface() {
        centerPanel.removeAll();
    }

    public void refrechFrame() {
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }

    public void infoFile() throws IOException {
        fr = new FileReader(userArg);
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

    public void userTransactionFile() throws IOException {

        fw = new FileWriter(userArg + "Info");
        bw = new BufferedWriter(fw);

        mapInfo.put("Spare Account : ", String.valueOf(haveSpareAccount));
        mapInfo.put("Account Balance : ", accountBalance + "€");
        mapInfo.put("User Name : ", userArg);

        for (String i : mapInfo.keySet()) {
            bw.write("key: " + i + " value: " + mapInfo.get(i));
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    public void writeHistory(String nature, Double amount) throws IOException {
        Calendar date = Calendar.getInstance();
        FileWriter historyFile = new FileWriter(userArg + "History", true);
        BufferedWriter historyBw = new BufferedWriter(historyFile);

        historyBw.write(nature + amount + "€" + " Date : " + date.getTime());
        historyBw.newLine();
        historyBw.close();
        historyFile.close();
    }

    public void sendMoney() {
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel sendMoneyPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JPanel destinationPanel = new JPanel();

        destinationPerson = new JLabel("To : ");
        destinationMoney = new JTextField(15);
        sendMoneyTextField = new JTextField("Amount of Money to send");
        sendMoneyTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (sendMoneyTextField.getText().equals("Amount of Money to send")) {
                    sendMoneyTextField.setText("");
                    sendMoneyTextField.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (sendMoneyTextField.getText().isEmpty()) {
                    sendMoneyTextField.setForeground(Color.GRAY);
                    sendMoneyTextField.setText("Amount of Money to send");
                }
            }

        });
        sendMoneyBtn = new JButton("Send");
        sendMoneyBtn.addActionListener(this);

        balance = new JLabel("Your balance is : " + accountBalance + "€");
        balance.setFont(serif2);
        sendMoneyBtn.setFont(serif2);

        destinationPanel.setBackground(bgColor2);
        destinationPanel.setForeground(Color.white);
        sendMoneyBtn.setBackground(new Color(40, 54, 24));
        sendMoneyBtn.setForeground(Color.white);

        destinationPanel.add(destinationPerson);
        destinationPanel.add(destinationMoney);
        sendMoneyPanel.add(destinationPanel);
        sendMoneyPanel.add(sendMoneyTextField);
        sendMoneyPanel.add(sendMoneyBtn);
        sendMoneyPanel.setBackground(bgColor2);

        JPanel balancePanel = new JPanel();
        balancePanel.add(balance);
        sendMoneyPanel.add(balancePanel);

        balancePanel.setBackground(bgColor2);

        centerPanel.add(sendMoneyPanel, gbc);
    }

    public void sendMoneyUser() {

        canSendMoney = false;
        moneyAmountSent = Double.valueOf(sendMoneyTextField.getText());
        if (moneyAmountSent <= accountBalance) {
            // System.out.println("money Sent");
            JLabel timeToSend = new JLabel("Sending money : 5");
            SendingTime t = new SendingTime(5, timeToSend);
            t.start();
            JOptionPane.showMessageDialog(frame, timeToSend);
            accountBalance = accountBalance - moneyAmountSent;
            balance.setText("your balance is : " + accountBalance + "€");
            canSendMoney = true;
        } else {
            balance.setText("error, the amount is not valid");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == withDraw) {
            cleanInterface();
            // System.out.println("money Withdrawn");
            withDraw();

            refrechFrame();
        }
        if (e.getSource() == withdrawBtn) {

            try {
                withDrawUser();
                personalData[6] = String.valueOf(accountBalance);
                updateUser();
                withDrawField.setText("");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (e.getSource() == deposit) {
            cleanInterface();
            refrechFrame();
            deposit();
            refrechFrame();

            // System.out.println("deposit money ");
        }
        if (e.getSource() == depositBtn) {
            depositUser();
            try {
                writeHistory("Deposit : ", depositMoney);
                personalData[6] = String.valueOf(accountBalance);
                updateUser();
                depositField.setText("");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (e.getSource() == accountHistory) {
            try {
                showHistory();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
        if (e.getSource() == sendMoney) {
            cleanInterface();
            refrechFrame();
            sendMoney();
            refrechFrame();
        }
        if (e.getSource() == sendMoneyBtn) {
            sendMoneyUser();
            refrechFrame();
            if (canSendMoney) {
                try {
                    writeHistory("Money Sent : ", moneyAmountSent);
                    personalData[6] = String.valueOf(accountBalance);
                    updateUser();
                    sendMoneyTextField.setText("");
                    destinationMoney.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (e.getSource() == printInfo) {
            try {
                userTransactionFile();
                message = "file named " + userArg + "Info" + " has been created\nplease check your information";
                JOptionPane.showMessageDialog(frame, message);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (e.getSource() == spareAccount) {
            message = "you can't have a spare account at this moment, please check again in the next update :D";
            JOptionPane.showMessageDialog(frame, message);
        }

    }
}