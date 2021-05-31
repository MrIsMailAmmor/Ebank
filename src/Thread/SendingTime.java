package Thread;

import javax.swing.JLabel;

public class SendingTime extends Thread {
    private JLabel textfield;
    private int time;

    public SendingTime(int time, JLabel text) {
        this.time = time;
        this.textfield = text;
    }

    public void run() {
        while (time > 0) {
            try {
                Thread.sleep(1000);
                time--;
                textfield.setText("Sending money : " + String.valueOf(time));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (time == 0) {
                textfield.setText("Transaction Finished");
            }
        }
    }
}