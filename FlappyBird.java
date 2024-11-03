package flappybird.game;

import flappybird.game.entity.Bird;
import flappybird.game.entity.Pipe;
import flappybird.interfaces.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener, GameController {
    private int boardWidth;
    private int boardHeight;

    // Images
    private Image backgroundImg;
    private Image birdImg;
    private Image topPipeImg;
    private Image bottomPipeImg;

    // Game entities
    private Bird bird;
    private ArrayList<Pipe> pipes;

    // Game variables
    private Random random = new Random();
    private Timer gameLoop;
    private Timer placePipeTimer;
    private boolean gameOver = false;
    private double score = 0;
    private double highScore = 0;

    // UI components
    private JButton startButton;
    private JButton restartButton;
    private JLabel titleLabel;
    private JTextArea instructionsArea;

    public FlappyBird(int width, int height) {
        this.boardWidth = width;
        this.boardHeight = height;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        loadImages();
        initializeGameEntities();
        initializeUIComponents();

        setLayout(null); // Use absolute positioning
        add(titleLabel);
        add(instructionsArea);
        add(startButton);
        add(restartButton);

        gameLoop = new Timer(1000 / 60, this);
    }

    private void loadImages() {
        backgroundImg = new ImageIcon(getClass().getResource("/resources/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/resources/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/resources/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/resources/bottompipe.png")).getImage();
    }

    private void initializeGameEntities() {
        int birdX = boardWidth / 8;
        int birdY = boardHeight / 2;
        int birdWidth = 34;
        int birdHeight = 24;
        bird = new Bird(birdX, birdY, birdWidth, birdHeight, birdImg);
        pipes = new ArrayList<>();
    }

    private void initializeUIComponents() {
        titleLabel = new JLabel("FLAPPY BIRD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 50, boardWidth, 60);

        instructionsArea = new JTextArea(
                "INSTRUCTIONS:\n" +
                        "1. Press Spacebar to Fly\n" +
                        "2. Avoid Obstacles\n" +
                        "3. Score Points"
        );
        instructionsArea.setEditable(false);
        instructionsArea.setOpaque(false);
        instructionsArea.setForeground(Color.WHITE);
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, 20));
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        instructionsArea.setBounds(50, 120, boardWidth - 100, 150);

        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 24));
        startButton.setBackground(new Color(0, 200, 0));
        startButton.setForeground(Color.WHITE);
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        startButton.setBounds(boardWidth / 2 - 75, boardHeight / 2 + 30, 150, 50);
        startButton.addActionListener(e -> startGame());

        restartButton = new JButton("Restart");
        restartButton.setVisible(false);
        restartButton.setFont(new Font("Arial", Font.PLAIN, 24));
        restartButton.setBackground(new Color(0, 200, 0));
        restartButton.setForeground(Color.WHITE);
        restartButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        restartButton.setBounds(boardWidth / 2 - 75, boardHeight / 2 + 50, 150, 50);
        restartButton.addActionListener(e -> restartGame());
    }

    @Override
    public void startGame() {
        bird.setY(boardHeight / 2);
        bird.setVelocityY(0);
        pipes.clear();
        score = 0;
        gameOver = false;

        titleLabel.setVisible(false);
        instructionsArea.setVisible(false);
        startButton.setVisible(false);
        restartButton.setVisible(false);

        placePipeTimer = new Timer(1000, e -> placePipes());
        placePipeTimer.start();
        gameLoop.start();
    }

    @Override
    public void restartGame() {
        startGame();
    }

    private void placePipes() {
        int pipeStartingX = boardWidth;
        int pipeY = 0;
        int pipeHeight = 512;
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;
        int pipeWidth = 64;

        Pipe topPipe = new Pipe(pipeStartingX, randomPipeY, pipeWidth, pipeHeight, topPipeImg);
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(pipeStartingX, topPipe.getY() + pipeHeight + openingSpace, pipeWidth, pipeHeight, bottomPipeImg);
        pipes.add(bottomPipe);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        bird.draw(g);
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        g.drawString("Score: " + (int) score, 10, 35);

        String highScoreText = "High Score: " + (int) highScore;
        int highScoreTextWidth = g.getFontMetrics().stringWidth(highScoreText);
        g.drawString(highScoreText, boardWidth - highScoreTextWidth - 30, 35);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 48));
            String gameOverText = "GAME OVER!";
            int gameOverTextWidth = g.getFontMetrics().stringWidth(gameOverText);
            g.drawString(gameOverText, (boardWidth - gameOverTextWidth) / 2, boardHeight / 2 - 30);

            g.setFont(new Font("Arial", Font.PLAIN, 36));
            String finalScoreText = "Score: " + (int) score;
            int finalScoreTextWidth = g.getFontMetrics().stringWidth(finalScoreText);
            g.drawString(finalScoreText, (boardWidth - finalScoreTextWidth) / 2, boardHeight / 2 + 20);
            restartButton.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            update();
        }
        repaint();
    }

    @Override
    public void update() {
        // Update bird
        bird.move();

        // Update pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.move();

            // Remove pipes that have gone off screen
            if (pipe.getX() + pipe.getWidth() < 0) {
                pipes.remove(pipe);
                i--;
                if (!pipe.isPassed()) {
                    score++;
                    pipe.setPassed(true);
                }
            }

            // Check for collisions
            if (checkCollision(bird, pipe)) {
                endGame();
            }
        }

        // Check if bird is out of bounds
        if (bird.getY() < 0 || bird.getY() + bird.getHeight() > boardHeight) {
            endGame();
        }
    }

    private boolean checkCollision(Bird bird, Pipe pipe) {
        return bird.getX() < pipe.getX() + pipe.getWidth() &&
                bird.getX() + bird.getWidth() > pipe.getX() &&
                bird.getY() < pipe.getY() + pipe.getHeight() &&
                bird.getY() + bird.getHeight() > pipe.getY();
    }

    private void endGame() {
        gameOver = true;
        highScore = Math.max(highScore, score);
        gameLoop.stop();
        placePipeTimer.stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            bird.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used but required by KeyListener interface
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used but required by KeyListener interface
    }
}