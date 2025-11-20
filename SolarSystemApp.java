import javax.swing.*;

public class SolarSystemApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Solar System Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        SolarSystemPanel solarSystemPanel = new SolarSystemPanel();
        frame.add(solarSystemPanel);
        frame.setVisible(true);

        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                solarSystemPanel.centerSolarSystem();
            }
        });
    }
}

