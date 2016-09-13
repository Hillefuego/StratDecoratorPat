package designpat;

import commandPat.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import FileIO.*;
import visitorPat.FileWriteVisitor;
import visitorPat.ShapeVisitor;


/**
 * @author HCH
 */
class DrawPad extends JComponent {

    private Image image;
    private CommandManager cmdMan = new CommandManager();
    private FileWriter fileWriter = new FileWriter();
    private FileReader fileReader = new FileReader();
    private Point lastPoint;
    private Point currentPoint;
    ShapeManager shapeMan = new ShapeManager();

    public DrawPad() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastPoint = new Point(e.getX(),e.getY());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentPoint = new Point(e.getX(),e.getY());
                if (toolboxControls.select == true) {
                    shapeMan.dragShapes(currentPoint, lastPoint);
                }
                if (toolboxControls.resize == true) {
                    shapeMan.resizeShape(currentPoint, lastPoint);
                }
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            @SuppressWarnings("empty-statement")
            public void mouseReleased(MouseEvent e) {
                currentPoint = new Point(e.getX(),e.getY());
                if (currentPoint.x < lastPoint.x) {
                    int tempX = currentPoint.x;
                    currentPoint.x = lastPoint.x;
                    lastPoint.x = tempX;
                }
                if (currentPoint.y < lastPoint.y) {
                    int tempY = currentPoint.y;
                    currentPoint.y = lastPoint.y;
                    lastPoint.y = tempY;
                }
                if (toolboxControls.rectangle == true) {
                    cmdMan.newCommand(new DrawRectangle(lastPoint, currentPoint, shapeMan));
                }
                if (toolboxControls.ellipse == true) {
                    cmdMan.newCommand(new DrawEllipse(lastPoint, currentPoint, shapeMan));
                }
                if (toolboxControls.select == true) {
                    cmdMan.newCommand(new SelectShapes(currentPoint, shapeMan));
                }
                if (toolboxControls.resize == true) {
                    cmdMan.newCommand(new ResizeShape(shapeMan,lastPoint, currentPoint));
                }
                repaint();
            }
        });
        Action undoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("undo");
                cmdMan.undoCommand();
                repaint();
            }
        };
        Action redoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("redo");
                cmdMan.redoCommand();
                repaint();
            }
        };

        Action makeGroupAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(toolboxControls.select == true) {
                    cmdMan.newCommand(new MakeGroup(shapeMan));
                }
                /** Move code to shapemanager **/
                if(toolboxControls.selectGroup == true && shapeMan.getSelectedGroup() != null){
                    // A group is selected
                    if(shapeMan.getParentGroup() == null && shapeMan.getChildGroup() == null){
                        // No group is set, so set the parent first
                        shapeMan.setParentGroup(shapeMan.getSelectedGroup());
                    }else if(shapeMan.getParentGroup() != null && shapeMan.getChildGroup() == null){
                        // Set the childgroup if the parent is already set
                        shapeMan.setChildGroup(shapeMan.getSelectedGroup());
                    }else if(shapeMan.getParentGroup() != null && shapeMan.getChildGroup() != null){
                        // If both are set add the child to the parent
                        cmdMan.newCommand(new AddChildGroup(shapeMan));
                        shapeMan.setParentGroup(null);
                        shapeMan.setChildGroup(null);
                    }
                }
            }
        };

        Action selectGroupAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeMan.unselectShapes();
                shapeMan.selectNextGroup();
                /*if(shapeMan.getParentGroup() != null && shapeMan.getChildGroup() != null){
                    shapeMan.setParentGroup(null);
                    shapeMan.setChildGroup(null);
                }*/
                //System.out.print(shapeMan.getSelectedGroup());
                repaint();
            }
        };

        getActionMap().put("Undo", undoAction);
        getActionMap().put("Redo", redoAction);
        getActionMap().put("MakeGroup", makeGroupAction);
        getActionMap().put("selectGroup", selectGroupAction);

        InputMap[] inputMaps = new InputMap[]{
                getInputMap(JComponent.WHEN_FOCUSED),
                getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
                getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW),
        };
        for (InputMap i : inputMaps) {
            i.put(KeyStroke.getKeyStroke("control Z"), "Undo");
            i.put(KeyStroke.getKeyStroke("control Y"), "Redo");
            i.put(KeyStroke.getKeyStroke("control G"), "MakeGroup");
            i.put(KeyStroke.getKeyStroke("control T"), "selectGroup");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g2d.setPaint(Color.white);
            g2d.fillRect(0, 0, getSize().width, getSize().height);
            g2d.setPaint(Color.black);
            clear();

        } else {
            shapeMan.drawShapes(g2d);
        }
        //g.drawImage(image, 0, 0, null);
    }

    public void clear() {
        shapeMan.clear();
        repaint();
    }

    public void save()throws IOException{
        ShapeVisitor FileWriteVisitor = new FileWriteVisitor(shapeMan.printAll());
        FileWriteVisitor.visit(fileWriter);
        System.out.print(shapeMan.printAll());
    }

    public void load()throws IOException{
        shapeMan.clear();
        shapeMan.setShapes(fileReader.readFile());
        //shapeMan.addShape(fileReader.readFile());
        System.out.print(shapeMan.printAll());
        repaint();
    }

    public void addOrnamentDialog(String side, String comment){
        shapeMan.setOrnament(side,comment);
        repaint();
    }
}
