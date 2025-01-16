package a03b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int width, int height) {
        logic = new LogicImpl(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*height);
        
        JPanel panel = new JPanel(new GridLayout(height,width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            for (var pos : logic.hit(this.cells.get(jb))) {
                this.cells.entrySet().stream().filter(entry -> entry.getValue().equals(pos)).findFirst().get().getKey().setText("*");
            }
            if (logic.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
            	var pos = new Position(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);

        this.cells.entrySet().stream().filter(entry -> entry.getValue().equals(logic.start())).findFirst().get().getKey().setText("o");
    }
    
}
