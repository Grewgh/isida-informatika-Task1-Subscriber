import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

public class Subscribers {
    JFrame frame;
    JLabel label;
    JPanel panel;
    JTextArea area;
    JScrollPane pane;
    Thread thread;
    DatagramSocket socket;

    //Создание окна
    public Subscribers() {
        frame = new JFrame("Subscriber");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.getRootPane()
                .setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        panel = new JPanel();
        panel.setLayout(null);
        area = new JTextArea();
        area.setEditable(false);
        new StartThread();
        label = new JLabel();
        label.setText("News");
        label.setBounds(10, 10, 100, 50);
        panel.add(label);
        pane = new JScrollPane(area);
        pane.setBounds(10, 60, 365, 250);
        panel.add(pane);
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Subscribers u = new Subscribers();
    }

    //Поток принимающий сообщения от издателя
    public class StartThread implements Runnable {
        StartThread() {
            thread = new Thread(this);
            thread.start();
        }

        public void run() {
            try {
                byte[] buffer = new byte[1024];
                int port = 8080;
                try {
                    socket = new DatagramSocket(port);
                    while (true) {
                        try {
                            DatagramPacket packet =
                                    new DatagramPacket(buffer, buffer.length);
                            socket.receive(packet);
                            area.append(new String(buffer) + "\n");
                        } catch (UnknownHostException ue) {
                        }
                    }
                } catch (java.net.BindException b) {
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}