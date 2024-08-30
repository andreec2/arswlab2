package snakepackage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;


import javax.swing.*;

import enums.GridSize;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * @author jd-
 *
 */
public class SnakeApp {

    private static SnakeApp app;
    public static final int MAX_THREADS = 8;
    Snake[] snakes = new Snake[MAX_THREADS];
    private static final Cell[] spawn = {
        new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2,
        3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
        new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2,
        GridSize.GRID_HEIGHT - 2)};
    private JFrame frame;
    private static Board board;
    int nr_selected = 0;
    Thread[] thread = new Thread[MAX_THREADS];

    private JButton startButton, pauseButton, resumeButton;
    private JLabel longestSnakeLabel, worstSnakeLabel;
    private boolean gameRunning = false;
    private boolean gamePaused = false;
    private boolean gameStart = false;

    public SnakeApp() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("The Snake Race");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17,
                GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 100); // Ajusted for additional UI components
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);
        board = new Board();
        
        frame.add(board, BorderLayout.CENTER);
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        
        startButton = new JButton("Iniciar");
        pauseButton = new JButton("Pausar");
        resumeButton = new JButton("Reanudar");
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameRunning) {
                    init();
                    gameRunning = true;
                }
            }
        });
        
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameRunning && !gamePaused) {
                    pauseGame();
                    gamePaused = true;
                }
            }
        });
        
        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gamePaused) {
                    resumeGame();
                    gamePaused = false;
                }
            }
        });
    
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resumeButton);
        
        frame.add(controlPanel, BorderLayout.SOUTH);
    
        // Panel to show the longest and worst snake
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));
        longestSnakeLabel = new JLabel("Serpiente más larga: ");
        worstSnakeLabel = new JLabel("Peor serpiente: ");
        infoPanel.add(longestSnakeLabel);
        infoPanel.add(worstSnakeLabel);
        
        frame.add(infoPanel, BorderLayout.NORTH);
    
        // Ensure the frame is visible
        frame.setVisible(true);
    }
    

    public static void main(String[] args) {
        app = new SnakeApp();
        //app.init();
    }

    private void init() {
        for (int i = 0; i != MAX_THREADS; i++) {
            snakes[i] = new Snake(i + 1, spawn[i], i + 1);
            snakes[i].addObserver(board);
            thread[i] = new Thread(snakes[i]);
            thread[i].start();
        }

        new Thread(() -> {
            while (true) {
                int x = 0;
                for (int i = 0; i != MAX_THREADS; i++) {
                    if (snakes[i].isSnakeEnd() == true) {
                        x++;
                    }
                }
                if (x == MAX_THREADS) {
                    break;
                }
            }

            System.out.println("Thread (snake) status:");
            for (int i = 0; i != MAX_THREADS; i++) {
                System.out.println("[" + i + "] :" + thread[i].getState());
            }
        }).start();
    }

    private void pauseGame() {
        for (Snake snake : snakes) {
            snake.pause();
        }

        // Show longest snake and worst snake
        Snake longestSnake = null;
        Snake worstSnake = null;
        
        for (Snake snake : snakes) {
            if (!snake.isSnakeEnd()) {
                if (longestSnake == null || snake.getBody().size() > longestSnake.getBody().size()) {
                    longestSnake = snake;
                }
            } else {
                if (worstSnake == null || snake.getBody().size() < (worstSnake != null ? worstSnake.getBody().size() : Integer.MAX_VALUE)) {
                    worstSnake = snake;
                }
            }
        }

        if (longestSnake != null) {
            longestSnakeLabel.setText("Serpiente más larga: " + longestSnake.getIdt() + " (Tamaño: " + longestSnake.getBody().size() + ")");
        }
        if (worstSnake != null) {
            worstSnakeLabel.setText("Peor serpiente: " + worstSnake.getIdt() + " (Tamaño: " + worstSnake.getBody().size() + ")");
        }
    }

    public static SnakeApp getApp() {
        return app;
    }

    private void resumeGame() {
        for (Snake snake : snakes) {
            snake.resume();
        }
    }
}
