import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AdvancedStopwatch extends JFrame implements ActionListener {

    private JLabel timeLabel;
    private JButton startButton, stopButton, resetButton, lapButton;
    private JTextArea lapArea;
    private JScrollPane lapScrollPane;

    private Timer timer;
    private long startTime = 0;
    private long elapsed = 0;
    private boolean running = false;

    private ArrayList<String> laps = new ArrayList<>();

    public AdvancedStopwatch() {
        setTitle("Advanced Stopwatch");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Time Display
        timeLabel = new JLabel("00:00:00.000", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 35));
        add(timeLabel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");
        lapButton = new JButton("Lap");

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(lapButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Lap Area
        lapArea = new JTextArea();
        lapArea.setEditable(false);
        lapScrollPane = new JScrollPane(lapArea);
        lapScrollPane.setPreferredSize(new Dimension(380, 150));
        add(lapScrollPane, BorderLayout.SOUTH);

        // Timer updates every 10 milliseconds
        timer = new Timer(10, e -> updateDisplay());

        // Event listeners
        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        resetButton.addActionListener(this);
        lapButton.addActionListener(this);

        setVisible(true);
    }

    private void updateDisplay() {
        long current = System.currentTimeMillis();
        long totalElapsed = elapsed + (running ? (current - startTime) : 0);

        int hours = (int) (totalElapsed / 3600000);
        int minutes = (int) (totalElapsed / 60000) % 60;
        int seconds = (int) (totalElapsed / 1000) % 60;
        int milliseconds = (int) (totalElapsed % 1000);

        timeLabel.setText(String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds));
    }

    private void addLap() {
        laps.add(timeLabel.getText());
        lapArea.setText("");
        for (int i = 0; i < laps.size(); i++) {
            lapArea.append("Lap " + (i + 1) + ": " + laps.get(i) + "\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton && !running) {
            startTime = System.currentTimeMillis();
            timer.start();
            running = true;
        } else if (e.getSource() == stopButton && running) {
            timer.stop();
            elapsed += System.currentTimeMillis() - startTime;
            running = false;
        } else if (e.getSource() == resetButton) {
            timer.stop();
            startTime = 0;
            elapsed = 0;
            running = false;
            laps.clear();
            timeLabel.setText("00:00:00.000");
            lapArea.setText("");
        } else if (e.getSource() == lapButton && running) {
            addLap();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdvancedStopwatch::new);
    }
}
