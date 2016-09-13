package FileIO;

import compositePat.CompositeGroup;
import decoratorPat.BottomOrnamentDecorator;
import decoratorPat.TopOrnamentDecorator;
import strategyPat.CEllipseDrawer;
import strategyPat.CRectangleDrawer;
import strategyPat.CombiShape;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by HCH on 18-Jun-16.
 */

public class FileReader {

    public CompositeGroup readFile() throws IOException {
        List<String> readList = new ArrayList<>();
        CompositeGroup fileGroup;

        try (Stream<String> stream = Files.lines(Paths.get("shapesFile"))) {

            readList = stream.collect(Collectors.toList());
            if(readList.size()>0);
                readList.remove(0);
            //System.out.print(stream.findFirst().filter(line->line.contains("group")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        fileGroup = parseToGroup(readList, new CompositeGroup(),2);
        return fileGroup;
    }


    /**
     * ParseToGroup recursively parses a String list into the BaseShape file structure.
     * While it reads the list until its empty it determines if the current line is still in
     * the same group by counting the whitespace. If it is, it type-tests the BaseShape in the current line
     * and adds them accordingly until done. If it isn't, the current group is done and can be returned.
     * Ellipses and rectangles are added normally but groups are called with another parseToGroup.
     **/
    public CompositeGroup parseToGroup(List<String> readList, CompositeGroup group,int depth){

        while(!readList.isEmpty()){
            String currentLine = readList.get(0);
            if(whiteSpaceCounter(currentLine)==depth){
                if(currentLine.contains("group")) {
                    if(readList.get(1) != null){
                        String s1 = readList.get(1);
                        if(s1.contains("top")) {
                            TopOrnamentDecorator topOrnamentDecorator = new TopOrnamentDecorator(new CompositeGroup());
                            topOrnamentDecorator.setFullDescription(s1.trim());
                            if(readList.get(2) != null){
                                String s2 = readList.get(2);
                                if(s2.contains("bottom")){
                                    BottomOrnamentDecorator bottomOrnamentDecorator = new BottomOrnamentDecorator(topOrnamentDecorator);
                                    bottomOrnamentDecorator.setFullDescription(s2.trim());
                                    readList.remove(2);
                                    readList.remove(1);
                                    readList.remove(currentLine);
                                    group.add(bottomOrnamentDecorator);
                                    continue;
                                }
                            }
                            readList.remove(1);
                            readList.remove(currentLine);
                            group.add(topOrnamentDecorator);
                            continue;
                        }
                        if(s1.contains("bottom")) {
                            BottomOrnamentDecorator bottomOrnamentDecorator = new BottomOrnamentDecorator(new CompositeGroup());
                            bottomOrnamentDecorator.setFullDescription(s1.trim());
                            if(readList.size()>=3){
                                String s2 = readList.get(2);
                                if(s2.contains("top")){
                                    TopOrnamentDecorator topOrnamentDecorator = new TopOrnamentDecorator(new CompositeGroup());
                                    topOrnamentDecorator.setFullDescription(s1.trim());
                                    BottomOrnamentDecorator bottomOrnamentDecorator2 = new BottomOrnamentDecorator(topOrnamentDecorator);
                                    bottomOrnamentDecorator.setFullDescription(s2.trim());
                                    readList.remove(2);
                                    readList.remove(1);
                                    readList.remove(currentLine);
                                    group.add(bottomOrnamentDecorator2);
                                    continue;
                                }
                            }
                            readList.remove(1);
                            readList.remove(currentLine);
                            group.add(bottomOrnamentDecorator);
                            continue;
                        }
                    }
                    readList.remove(currentLine);
                    group.add(parseToGroup(readList, new CompositeGroup(),depth+2));
                }
                if(currentLine.contains("rectangle")) {
                    List<String> shapeCoords = Arrays.asList(currentLine.trim().split(" "));
                    CombiShape combiShape = new CombiShape(CRectangleDrawer.getInstance(),Integer.parseInt(shapeCoords.get(1)),
                            Integer.parseInt(shapeCoords.get(2)),
                            Integer.parseInt(shapeCoords.get(3)),
                            Integer.parseInt(shapeCoords.get(4)));
                    if(readList.size()>=2){
                        String s1 = readList.get(1);
                        if(s1.contains("top")) {
                            TopOrnamentDecorator topOrnamentDecorator = new TopOrnamentDecorator(combiShape);
                            topOrnamentDecorator.setFullDescription(s1.trim());
                            if(readList.size()>=3){
                                String s2 = readList.get(2);
                                if(s2.contains("bottom")){
                                    BottomOrnamentDecorator bottomOrnamentDecorator = new BottomOrnamentDecorator(topOrnamentDecorator);
                                    bottomOrnamentDecorator.setFullDescription(s2.trim());
                                    readList.remove(2);
                                    readList.remove(1);
                                    readList.remove(currentLine);
                                    group.add(bottomOrnamentDecorator);
                                    continue;
                                }
                            }
                            readList.remove(1);
                            readList.remove(currentLine);
                            group.add(topOrnamentDecorator);
                            continue;
                        }
                        if(s1.contains("bottom")) {
                            BottomOrnamentDecorator bottomOrnamentDecorator = new BottomOrnamentDecorator(combiShape);
                            bottomOrnamentDecorator.setFullDescription(s1.trim());
                            if(readList.size()>=3){
                                String s2 = readList.get(2);
                                if(s2.contains("top")){
                                    TopOrnamentDecorator topOrnamentDecorator = new TopOrnamentDecorator(combiShape);
                                    topOrnamentDecorator.setFullDescription(s1.trim());
                                    BottomOrnamentDecorator bottomOrnamentDecorator2 = new BottomOrnamentDecorator(topOrnamentDecorator);
                                    bottomOrnamentDecorator.setFullDescription(s2.trim());
                                    readList.remove(2);
                                    readList.remove(1);
                                    readList.remove(currentLine);
                                    group.add(bottomOrnamentDecorator2);
                                    continue;
                                }
                            }
                            readList.remove(1);
                            readList.remove(currentLine);
                            group.add(bottomOrnamentDecorator);
                            continue;
                        }
                    }
                    readList.remove(currentLine);
                    group.add(combiShape);
                }
                if(currentLine.contains("ellipse")) {
                    List<String> shapeCoords = Arrays.asList(currentLine.trim().split(" "));
                    CombiShape combiShape = new CombiShape(CEllipseDrawer.getInstance(),Integer.parseInt(shapeCoords.get(1)),
                            Integer.parseInt(shapeCoords.get(2)),
                            Integer.parseInt(shapeCoords.get(3)),
                            Integer.parseInt(shapeCoords.get(4)));
                    if(readList.size()>=2){
                        String s1 = readList.get(1);
                        if(s1.contains("top")) {
                            TopOrnamentDecorator topOrnamentDecorator = new TopOrnamentDecorator(combiShape);
                            topOrnamentDecorator.setFullDescription(s1.trim());
                            if(readList.size()>=3){
                                String s2 = readList.get(2);
                                if(s2.contains("bottom")){
                                    BottomOrnamentDecorator bottomOrnamentDecorator = new BottomOrnamentDecorator(topOrnamentDecorator);
                                    bottomOrnamentDecorator.setFullDescription(s2);
                                    readList.remove(2);
                                    readList.remove(1);
                                    readList.remove(currentLine);
                                    group.add(bottomOrnamentDecorator);
                                    continue;
                                }
                            }
                            readList.remove(1);
                            readList.remove(currentLine);
                            group.add(topOrnamentDecorator);
                            continue;
                        }
                        if(s1.contains("bottom")) {
                            BottomOrnamentDecorator bottomOrnamentDecorator = new BottomOrnamentDecorator(combiShape);
                            bottomOrnamentDecorator.setFullDescription(s1.trim());
                            if(readList.size()>=3){
                                String s2 = readList.get(2);
                                if(s2.contains("top")){
                                    TopOrnamentDecorator topOrnamentDecorator = new TopOrnamentDecorator(combiShape);
                                    topOrnamentDecorator.setFullDescription(s1.trim());
                                    BottomOrnamentDecorator bottomOrnamentDecorator2 = new BottomOrnamentDecorator(topOrnamentDecorator);
                                    bottomOrnamentDecorator.setFullDescription(s2.trim());
                                    readList.remove(2);
                                    readList.remove(1);
                                    readList.remove(currentLine);
                                    group.add(bottomOrnamentDecorator2);
                                    continue;
                                }
                            }
                            readList.remove(1);
                            readList.remove(currentLine);
                            group.add(bottomOrnamentDecorator);
                            continue;
                        }
                    }
                    readList.remove(currentLine);
                    group.add(combiShape);
                }
            }else{
                return group;
            }
        }

        return group;
    }

    private int whiteSpaceCounter(String line){
        int spaceCount = 0;
        for (int i = 0; i <= line.length() && line.charAt(i) == ' '; i++) {
            spaceCount ++;
        }
        return spaceCount;
    }
}
