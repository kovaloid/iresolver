package com.koval.jresolver.wizard.config;

import java.awt.*;

import javax.swing.*;


public class JiraConnectorPanel extends JPanel {

    public JiraConnectorPanel() {
        super();
        draw();
    }

    private void draw() {
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
        JButton button333 = new JButton("Another button");
        add(button333);
    }

}
