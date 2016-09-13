package visitorPat;

import FileIO.FileWriter;
import designpat.BaseShape;

import java.io.IOException;

/**
 * Created by HCH on 28-Aug-16.
 */
public class FileWriteVisitor implements ShapeVisitor {
    String fileString;

    public FileWriteVisitor(String fileString){
        this.fileString = fileString;
    }

    public void visit(FileWriter fileWriter){
        try {
            fileWriter.write(fileString);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void visit(BaseShape baseShape){

    }
}
