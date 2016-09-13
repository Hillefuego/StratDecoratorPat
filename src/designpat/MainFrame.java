/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designpat;

import com.sun.javafx.binding.StringFormatter;
import compositePat.CompositeGroup;
import compositePat.Group;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author HCH
 */
public class MainFrame {

    JFrame frame = new JFrame("Design Patterns");

    public MainFrame() {
        frame.setSize(500, 500);
        frame.setLocation(200, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        final DrawPad drawPad = new DrawPad();
        content.add(drawPad, BorderLayout.CENTER);

        JPanel toolbox = new JPanel();
        toolbox.setPreferredSize(new Dimension(100, 10));

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPad.clear();
            }
        });

        JButton rectangleButton = new JButton("CRectangle");
        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolboxControls.ellipse = false;
                toolboxControls.rectangle = true;
                toolboxControls.select = false;
                toolboxControls.resize = false;
                drawPad.shapeMan.unselectShapes();
            }

        });

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolboxControls.select = true;
                toolboxControls.ellipse = false;
                toolboxControls.rectangle = false;
                toolboxControls.resize = false;
                toolboxControls.selectGroup = false;
            }
        });

        JButton selectGroupButton = new JButton("Select Group");
        selectGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolboxControls.select = false;
                toolboxControls.ellipse = false;
                toolboxControls.rectangle = false;
                toolboxControls.resize = false;
                toolboxControls.selectGroup = true;
                drawPad.shapeMan.unselectShapes();
            }
        });

        JButton ellipseButton = new JButton("Ellipse");
        ellipseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolboxControls.ellipse = true;
                toolboxControls.rectangle = false;
                toolboxControls.select = false;
                toolboxControls.resize = false;
                toolboxControls.selectGroup = false;
                drawPad.shapeMan.unselectShapes();
            }
        });

        JButton resizeButton = new JButton("Resize");
        resizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolboxControls.ellipse = false;
                toolboxControls.rectangle = false;
                toolboxControls.select = false;
                toolboxControls.resize = true;
                toolboxControls.selectGroup = false;
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    drawPad.save();
                }catch (IOException ioE){

                }
            }
        });

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    drawPad.load();
                }catch (IOException ioE){

                }
            }
        });

        JButton addOrnamentButton = new JButton("Add Ornament");
        addOrnamentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Icon icon = new ImageIcon("");
                Object[] possibilities = {"Top", "Bottom"};
                String side = (String)JOptionPane.showInputDialog(
                        frame,
                        "Choose top or bottom for your ornament.",
                        "Ornaments",
                        JOptionPane.PLAIN_MESSAGE,
                        icon,
                        possibilities,
                        "Top");

                String comment = (String)JOptionPane.showInputDialog(
                        frame,
                        "Enter a comment:",
                        "Comment",
                        JOptionPane.PLAIN_MESSAGE,
                        icon,
                        null,
                        "Ornament comment");


                //If a string was returned, say so.
                if ((side != null) && (side.length() > 0)) {
                    //setLabel("Green eggs and... " + s + "!");
                    drawPad.addOrnamentDialog(side,comment);
                }

            }
        });

        selectButton.setPreferredSize(new Dimension(100, 25));
        selectGroupButton.setPreferredSize(new Dimension(100, 25));
        ellipseButton.setPreferredSize(new Dimension(100, 25));
        rectangleButton.setPreferredSize(new Dimension(100, 25));
        saveButton.setPreferredSize(new Dimension(100, 25));
        loadButton.setPreferredSize(new Dimension(100, 25));
        resizeButton.setPreferredSize(new Dimension(100, 25));
        addOrnamentButton.setPreferredSize(new Dimension(100, 25));

        toolbox.add(ellipseButton);
        toolbox.add(rectangleButton);
        toolbox.add(selectButton);
        toolbox.add(selectGroupButton);
        toolbox.add(resizeButton);
        toolbox.add(saveButton);
        toolbox.add(loadButton);
        toolbox.add(clearButton);
        toolbox.add(addOrnamentButton);


        content.add(toolbox, BorderLayout.WEST);


    }
}
