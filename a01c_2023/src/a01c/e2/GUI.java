package a01c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Pair<Integer, Integer>, JButton> cells = new HashMap<>();
    private final Logic logic;
    private Integer z = 1;
    
    public GUI(int size) {
        logic = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            Pair<Integer, Integer> pos = null;
        	for (var entry : cells.entrySet()) {
                if (entry.getValue().equals(jb)) {
                    pos = entry.getKey();
                }
            }
            for (var p : logic.hit(pos)) {
                String text;
                if (cells.get(p).getText().equals(0) || cells.get(p).getText().equals("")) {
                    text = cells.get(p).getText();
                    text = "0";
                } else {
                    text = cells.get(p).getText();
                }
                cells.get(p).setText(logic.firstCells() ? z.toString() : text);
            }
            if (logic.isOver()) {
                System.exit(0);
            }
            z++;
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton();
                this.cells.put(pos, jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
