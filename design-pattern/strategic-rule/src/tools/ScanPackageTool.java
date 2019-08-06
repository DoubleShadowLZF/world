package tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @Function 扫描包的工具
 * @author Double
 */
public class ScanPackageTool {

    /**
     * 扫包并返回包内的对象
     * @param basePackage
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static List<Object> scanRetObj(String basePackage) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<Object> retObjes = new ArrayList<>();
        List<Class> classes = scan(basePackage);
        for (Class cla : classes ) {
            Object o = cla.newInstance();
            retObjes.add(o);
        }
        return retObjes;
    }

    /**
     * 扫包
     * @param basePackage
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Class> scan(String basePackage) throws ClassNotFoundException {
        List<Class> retClasses = new ArrayList<>();
        //把包名转换为路径，首先得到项目的classpath
        String classpath = ScanPackageTool.class
                .getResource("/").getPath();
        //包名转换为路径名
        if(basePackage.indexOf(".") > -1){
            basePackage = basePackage.replaceAll("\\.", Matcher.quoteReplacement(File.separator) );
//            basePackage = basePackage.replaceAll(".", File.separator );
        }
        //合并 classpath 和 basePack
        String searchPath = classpath + basePackage;
        List<String> classPaths = doPath(new File(searchPath));
        for (String path : classPaths) {
            path = path.replace(classpath.replace("/","\\").replaceFirst("\\\\",""),"").replace("\\",".").replace(".class","");
            Class cla = Class.forName(path);
            retClasses.add(cla);
        }
        return retClasses;
    }

    /**
     * 该方法会扫描所有的类名，并返回所有类名
     * @param file
     * @return
     */
    private static List<String> doPath(File file) {
        List<String> retPaths = new ArrayList<>();
        //目录，则递归查找并添加文件
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                List<String> subPaths = doPath(files[i]);
                retPaths.addAll(subPaths);
            }
        }else{
            //文件则判断是否为class文件，是则添加
            if(file.getName().endsWith(".class")){
                retPaths.add(file.getPath());
            }
        }
        return retPaths;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        List<Class> classes = ScanPackageTool.scan("service");
        System.out.println(classes);
    }
}
