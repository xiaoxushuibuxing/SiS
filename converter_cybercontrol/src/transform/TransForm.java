package transform;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import SfnUI.GUI.GraphObject;
import awt.DCSDiagram;
import convter.SFDiagram;
import magus.util.PathUtils;

public class TransForm {

    static String path;
    private static final Logger logger = Logger.getLogger(TransForm.class);
    public static String imagePath = "D:/FDCS-Software/HMI/project/一期辅控网/graph/图形资源/";
	public static String nodeName="W3.FW1.";
	public static String dir="fw1";
    public static void main(String[] args) throws Exception {

        path = "D:\\CyberControl\\HMI\\project\\一期辅控网\\graph\\图形资源";
        
        //	InputStream stream = new FileInputStream(file);
        File srcPath = new File("D:\\CyberControl\\HMI\\project\\一期辅控网\\graph\\图形资源\\aaa.drw");
        //new File(path);
        if (!srcPath.exists()) {

            logger.info("File or Directory [" + srcPath + "] not exist.");
            return;
        }

        if (srcPath.isDirectory()) {
            convertDir(srcPath);
            //	System.out.println(Config.getInstance().getPointMap().size());
        } else {
            logger.info(srcPath.getPath());
            GraphObject graphObj = GraphObject.CreateGraphFromPath(srcPath.getPath());
            if (graphObj == null) {
                logger.error("File or Directory [" + srcPath + "] not exist.");
                return;
            }
            logger.error("------------------"+graphObj.GetGraphName());
            SFDiagram diagram = new SFDiagram(graphObj);
            String filePath = srcPath.getPath().replace(path, path + "_out");
            filePath = filePath.replace(".drw", ".ser").replaceAll(" ", "");
            filePath= PathUtils.replaceHashWithNumber(filePath);
            System.out.println(filePath);
            saveBinaryFile(filePath, diagram.diagram);
        }

    }

    public static void convertDir(File srcPath) {
        File[] inList = srcPath.listFiles();
        for (int i = 0; i < inList.length; i++) {
            if (inList[i].isDirectory()) {
                convertDir(inList[i]);
            } else {
                String infilename = inList[i].getPath();
                if (!infilename.endsWith("drw")) {
                    continue;
                }
                logger.info(infilename);
                GraphObject graphObj = GraphObject.CreateGraphFromPath(infilename);
                if (graphObj == null) {
                    continue;
                }
                SFDiagram diagram = new SFDiagram(graphObj);
                logger.error("------------------"+infilename);
                String filePath = infilename.replace(path, path + "_out");
                
                filePath = filePath.replace(".drw", ".ser").replaceAll(" ", "");
                filePath = PathUtils.replaceHashWithNumber(filePath);
                logger.info("------------------"+filePath);
                saveBinaryFile(filePath, diagram.diagram);
            }
        }

        //erroeList.clear();

    }

    private static int saveBinaryFile(String fileName, DCSDiagram diagram) {
        // TODO Auto-generated method stub
        File file = new File(fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists())
            parentFile.mkdirs();
        try {
            file.createNewFile();
            DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
            diagram.write(out);
            out.close();
        } catch (IOException e) {
            return -1;
        }
        return 0;
    }
}
