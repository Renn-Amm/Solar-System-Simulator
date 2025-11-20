import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;

public class SolarSystemPanel extends JPanel {
    private Planet[] planets;
    private BufferedImage backgroundImage;
    private int centerX, centerY;
    private Planet hoveredPlanet = null;
    private boolean planetStopped = false;

    public SolarSystemPanel() {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("images/starrysky.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the planets array with details including facts as HTML content
        this.planets = new Planet[]{
            new Planet("Sun", 120, "images/sun.png", 0, 0,
                "<html><body>" +
                "<h1>Sun</h1>" +
                "<ul>" +
                "<li><b>Diameter:</b> The Sun's diameter measures about 1.39 million kilometers.</li>" +
                "<li><b>History:</b>  Central to human cultures and mythologies worldwide, the Sun has been studied scientifically for centuries. Its role in sustaining life on Earth makes it a focal point of scientific inquiry.</li>" +
                "<li><b>Facts:</b> The Sun is the star at the center of our solar system. It is a nearly perfect sphere of hot plasma, radiating energy primarily as visible light.</li>" +
                "</ul>" +
                "</body></html>"
            ),
            new Planet("Mercury", 24, "images/mercury.png", 80, 0.02,
                "<html><body>" +
                "<h1>Mercury</h1>" +
                "<ul>" +
                "<li><b>Diameter:</b> With a diameter of approximately 4,880 kilometers, Mercury is the smallest planet in the solar system.</li>" +
                "<li><b>History:</b> Named after the Roman messenger god, Mercury, its fast orbit around the Sun inspired its name. Ancient civilizations observed Mercury, associating it with various deities.</li>" +
                "<li><b>Facts:</b> Mercury is the smallest planet in our solar system. Mercury's proximity to the Sun results in extreme temperature fluctuations, teaching us about the effects of proximity to a star on planetary conditions</li>" +
                "</ul>" +
                "</body></html>"
            ),
            new Planet("Venus", 56, "images/venus.png", 120, 0.015,
                "<html><body>" +
                "<h1>Venus</h1>" +
                "<ul>" +
                "<li><b>Diameter:</b> With a diameter of around 12,104 kilometers, Venus is often referred to as Earth's \"sister planet.\"</li>" +
                "<li><b>History:</b> Named after the Roman goddess of love and beauty, Venus has been observed since antiquity. Early astronomers thought it to be two separate celestial bodies, the morning and evening star.</li>" +
                "<li><b>Facts:</b> Venus is the second planet from the Sun. It has a thick, toxic atmosphere and surface temperatures hot enough to melt lead.</li>" +
                "</ul>" +
                "</body></html>"
            ),
            new Planet("Earth", 40, "images/earth.png", 160, 0.01,
                "<html><body>" +
                "<h1>Earth</h1>" +
                "<ul>" +
                "<li><b>Diameter:</b>  Earth has a diameter of approximately 12,742 kilometers.</li>" +
                "<li><b>History:</b> The only known planet to support life, Earth has a rich history spanning billions of years. Its diverse ecosystems and geological features provide insights into the evolution of life and the planet itself.</li>" +
                "<li><b>Facts:</b> Earth is the third planet from the Sun and the only astronomical object known to harbor life. It has a diverse climate and terrain.</li>" +
                "</ul>" +
                "</body></html>"
            ),
            new Planet("Mars", 38, "images/mars.png", 200, 0.008,
                "<html><body>" +
                "<h1>Mars</h1>" +
                "<ul>" +
                "<li><b>Diameter:</b> Mars has a diameter of about 6,780 kilometers.</li>" +
                "<li><b>History:</b> Named after the Roman god of war, Mars has captured human imagination for centuries. Early observations suggested the possibility of Martian canals, fueling speculation about life on the planet.</li>" +
                "<li><b>Facts:</b> Mars is the fourth planet from the Sun and is known for its reddish appearance due to iron oxide. It has the largest volcano in the solar system.</li>" +
                "</ul>" +
                "</body></html>"
            ),
            new Planet("Jupiter", 100, "images/jupiter.png", 250, 0.005,
                "<html><body>" +
                "<h1>Jupiter</h1>" +
                "<ul>" +
                "<li><b>Diameter:</b> With a diameter of roughly 139,822 kilometers, Jupiter is the largest planet in the solar system.</li>" +
                "<li><b>History:</b> Named after the king of the Roman gods, Jupiter's immense size and brightness have made it a prominent object in the night sky since antiquity.</li>" +
                "<li><b>Facts:</b> Jupiter is the largest planet in our solar system. It is a gas giant with a Great Red Spot, a giant storm that has been raging for hundreds of years. It has 79 known moons.</li>" +
                "</ul>" +
                "</body></html>"
            ),
            new Planet("Saturn", 90, "images/saturn.png", 300, 0.004,
                "<html><body>" +
                "<h1>Saturn</h1>" +
                "<ul>" +
                "<li><b>Diameter:</b> Saturn has a diameter of approximately 116,460 kilometers.</li>" +
                "<li><b>History:</b> Named after the Roman god of agriculture, Saturn's distinctive rings have fascinated observers for centuries. Galileo Galilei was the first to observe them through a telescope.</li>" +
                "<li><b>Facts:</b> Saturn is famous for its stunning ring system, which is made up of ice and rock particles. It has 82 known moons.</li>" +
                "</ul>" +
                "</body></html>"
            ),
            new Planet("Uranus", 85, "images/uranus.png", 350, 0.003,
                "<html><body>" +
                "<h1>Uranus</h1>" +
                "<ul>" +
                "<li><b>Diameter:</b> Uranus has a diameter of about 50,724 kilometers.</li>" +
                "<li><b>History:</b>  Discovered by William Herschel in 1781, Uranus was the first planet to be discovered using a telescope. Its unusual axial tilt sets it apart from other planets.</li>" +
                "<li><b>Facts:</b> Uranus is a gas giant with a distinct blue color due to methane in its atmosphere. It rotates on its side, making its axial tilt unique.</li>" +
                "</ul>" +
                "</body></html>"
            ),
            new Planet("Neptune", 90, "images/neptune.png", 400, 0.002,
                "<html><body>" +
                "<h1>Neptune</h1>" +
                "<ul>" +
                "<li><b>Diameter:</b>  Neptune has a diameter of approximately 49,244 kilometers.</li>" +
                "<li><b>History:</b> Named after the Roman god of the sea, Neptune's existence was mathematically predicted before its visual discovery in 1846.</li>" +
                "<li><b>Facts:</b> Neptune is the farthest planet from the Sun in our solar system. It is known for its deep blue color and strong winds. It has 14 known moons.</li>" +
                "</ul>" +
                "</body></html>"
            ),
            new Planet("Moon", 12, "images/moon.png", 40, 0.05,
                "<html><body>" +
                "<h1>Moon</h1>" +
                "<ul>" +
                "<li><b>Diameter:</b> The Moon has a diameter of approximately 3,474 kilometers.</li>" +
                "<li><b>History:</b> Earth's only natural satellite, the Moon has inspired awe and wonder throughout human history. It has been the subject of scientific exploration and cultural significance in various civilizations.</li>" +
                "<li><b>Facts:</b> The Moon is the fifth-largest satellite in the Solar System and has a significant impact on Earth's tides.</li>" +
                "</ul>" +
                "</body></html>"
            )
        };

        // Center the solar system
        centerSolarSystem();

        // Add mouse motion listener to detect hover
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                handleHover(e.getX(), e.getY());
            }
        });

        // Setup a timer to update planet positions and repaint the panel
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!planetStopped) {
                    updatePlanets();
                }
                repaint();
            }
        }, 0, 16); // roughly 60 FPS
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Draw the orbits
        for (Planet planet : planets) {
            planet.drawOrbit(g);
        }

        // Draw the planets
        for (Planet planet : planets) {
            planet.draw(g);
        }
    }

    public void centerSolarSystem() {
        // Calculate the center of the panel
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        // Set the sun's position and update all planets' positions
        for (Planet planet : planets) {
            planet.setSunPosition(centerX, centerY);
        }
        repaint();
    }

    private void updatePlanets() {
        // Update the position of each planet
        for (Planet planet : planets) {
            planet.updatePosition();
        }
        // Update the moon's position around Earth
        Planet earth = planets[3];
        Planet moon = planets[9];
        moon.updatePositionAround(earth.getX(), earth.getY());
    }

    private void handleHover(int x, int y) {
        // Check if any planet is hovered and show info if true
        for (Planet planet : planets) {
            if (planet.isHovered(x, y)) {
                if (hoveredPlanet == null || !hoveredPlanet.equals(planet)) {
                    hoveredPlanet = planet;
                    planetStopped = true;
                    showPlanetInfo(planet);
                }
                return;
            }
        }
        hoveredPlanet = null;
        planetStopped = false;
    }

    private void showPlanetInfo(Planet planet) {
        SwingUtilities.invokeLater(() -> {
            // Create a dialog to show planet information
            JDialog dialog = new JDialog((Frame) null, planet.getName() + " Information", true);
            dialog.setLayout(new BorderLayout());
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(this);

            JPanel contentPanel = new BackgroundPanel("images/info.jpg");
            contentPanel.setLayout(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Scale the planet image to a specific size
            Image scaledImage = planet.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            JLabel planetImageLabel = new JLabel(new ImageIcon(scaledImage));
            contentPanel.add(planetImageLabel, BorderLayout.WEST);

            // Panel for planet information with semi-transparent background
            JPanel infoPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(new Color(0, 0, 0, 000)); // Semi-transparent background
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false); // Allow background to be semi-transparent

            JLabel infoLabel = new JLabel(planet.getInfo());
            infoLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
            infoLabel.setForeground(Color.WHITE); // Set text color to white
            infoPanel.add(infoLabel);

            contentPanel.add(infoPanel, BorderLayout.CENTER);

            dialog.add(contentPanel, BorderLayout.CENTER);

            JButton okButton = new JButton("Close");
            okButton.addActionListener(e -> {
                planetStopped = false;
                dialog.dispose();
            });
            dialog.add(okButton, BorderLayout.SOUTH);

            dialog.setVisible(true);
        });
    }

    private static class Planet {
        private String name;
        private int diameter, orbitRadius;
        private double angle, speed;
        private String info;
        private int x, y, sunX, sunY;
        private BufferedImage image;

        public Planet(String name, int diameter, String imagePath, int orbitRadius, double speed, String info) {
            this.name = name;
            this.diameter = diameter;
            this.orbitRadius = orbitRadius;
            this.speed = speed;
            this.info = info;
            this.angle = 0;
            try {
                this.image = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            updatePosition();
        }

        public String getName() {
            return name;
        }

        public String getInfo() {
            return info;
        }

        public int getDiameter() {
            return diameter;
        }

        public BufferedImage getImage() {
            return image;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setSunPosition(int sunX, int sunY) {
            this.sunX = sunX;
            this.sunY = sunY;
            updatePosition();
        }

        public void updatePosition() {
            if (orbitRadius != 0) {
                angle += speed;
                x = (int) (sunX + orbitRadius * Math.cos(angle));
                y = (int) (sunY + orbitRadius * Math.sin(angle));
            } else {
                x = sunX;
                y = sunY;
            }
        }

        public void updatePositionAround(int centerX, int centerY) {
            angle += speed;
            x = (int) (centerX + orbitRadius * Math.cos(angle));
            y = (int) (centerY + orbitRadius * Math.sin(angle));
        }

        public void draw(Graphics g) {
            g.drawImage(image, x - diameter / 2, y - diameter / 2, diameter, diameter, null);
        }

        public void drawOrbit(Graphics g) {
            if (orbitRadius != 0) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 50)); // Less visible color
                g2d.setStroke(new BasicStroke(0.5f)); // Thinner stroke
                g2d.drawOval(sunX - orbitRadius, sunY - orbitRadius, 2 * orbitRadius, 2 * orbitRadius);
                g2d.dispose();
            }
        }

        public boolean isHovered(int mouseX, int mouseY) {
            int radius = diameter / 2;
            return mouseX >= x - radius && mouseX <= x + radius && mouseY >= y - radius && mouseY <= y + radius;
        }
    }

    private static class BackgroundPanel extends JPanel {
        private BufferedImage background;

        public BackgroundPanel(String imagePath) {
            try {
                background = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
