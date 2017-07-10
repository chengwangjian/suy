package org.jbx.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Toby 通用工具类
 */
public class FileTest {

    /**
     * http://www.blogjava.net/toby/archive/2011/12/05/365585.html
     * @param args
     * @throws Exception
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, Exception {
        // TODO Auto-generated method stub
        File file = new File("e:/user/mms.xml");
//        System.out.println(file.renameTo(new File("e:/user/test/mms5.xml")));
//        FileUtil.copyFile(file,new File("e:/user/test/mms5.xml"));
    }

    /**
     * 获取路径下所有文件名
     * 
     */
    public static void getFile(String path) {
        File file = new File(path);
//        String[] name = file.list();
        if (!file.exists()) {
        	file.mkdir();
		}
//        return name;
    }
    public static void delFile(String path) {
    	File f = new File(path); 
    	// 输入要删除的文件位置 
    	if(f.exists()){
    		f.delete();
    	}
    }
    /**
     * 复制目录文件
     * @param sourceDirPath
     * @param targetDirPath
     * @throws IOException
     */
    public static void copyDir(String sourceDirPath, String targetDirPath) throws IOException {
        // 创建目标文件夹
        (new File(targetDirPath)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDirPath)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 复制文件
                String type = file[i].getName().substring(file[i].getName().lastIndexOf(".") + 1);

//                if (type.equalsIgnoreCase("txt"))
//                    FileUtil.copyFile(file[i], new File(targetDirPath + file[i].getName()), MTOServerConstants.CODE_UTF_8,
//                            MTOServerConstants.CODE_GBK);
//                else
//                    FileUtil.copyFile(file[i], new File(targetDirPath + file[i].getName()));
            }
            if (file[i].isDirectory()) {
                // 复制目录
                String sourceDir = sourceDirPath + File.separator + file[i].getName();
                String targetDir = targetDirPath + File.separator + file[i].getName();
//                FileUtil.copyDirectiory(sourceDir, targetDir);
            }
        }
    }

    /**
     * 读取文件中内容 
     * @param path
     * @return
     * @throws IOException
     */
    public static String readFileToString(String path) throws IOException {
        String resultStr = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            byte[] inBuf = new byte[2000];
            int len = inBuf.length;
            int off = 0;
            int ret = 0;
            while ((ret = fis.read(inBuf, off, len)) > 0) {
                off += ret;
                len -= ret;
            }
//            resultStr = new String(new String(inBuf, 0, off, MTOServerConstants.CODE_GBK).getBytes());
        } finally {
            if (fis != null)
                fis.close();
        }
        return resultStr;
    }

    /**
     * 文件转成字节数组 
     * @param path
     * @return
     * @throws IOException
     */
    public static byte[] readFileToBytes(String path) throws IOException {
        byte[] b = null;
        InputStream is = null;
        File f = new File(path);
        try {
            is = new FileInputStream(f);
            b = new byte[(int) f.length()];
            is.read(b);
        } finally {
            if (is != null)
                is.close();
        }
        return b;
    }

    /**
     * 将byte写入文件中
     * @param fileByte
     * @param filePath
     * @throws IOException
     */
    public static void byteToFile(byte[] fileByte, String filePath) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(filePath));
            os.write(fileByte);
            os.flush();
        } finally {
            if (os != null)
                os.close();
        }
    }

    /**
     * 将目录文件打包成zip
     * @param srcPathName
     * @param zipFilePath
     * @return 成功打包true 失败false
     */
    public static boolean compress(String srcPathName, String zipFilePath) {
        if (strIsNull(srcPathName) || strIsNull(zipFilePath))
            return false;

        File zipFile = new File(zipFilePath);
        File srcdir = new File(srcPathName);
        if (!srcdir.exists())
            return false;
//        Project prj = new Project();
//        Zip zip = new Zip();
//        zip.setProject(prj);
//        zip.setDestFile(zipFile);
//        FileSet fileSet = new FileSet();
//        fileSet.setProject(prj);
//        fileSet.setDir(srcdir);
//        zip.addFileset(fileSet);
//        zip.execute();
        return zipFile.exists();
    }

    /**
     * 判空字串
     * @param str
     * @return 为空true
     */
    public static boolean strIsNull(String str) {
        return str == null || str.equals("");
    }

    /**
     * 折分数组
     * @param ary
     * @param subSize
     * @return
     */
    public static List<List<Object>> splitAry(Object[] ary, int subSize) {
        int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;

        List<List<Object>> subAryList = new ArrayList<List<Object>>();

        for (int i = 0; i < count; i++) {
            int index = i * subSize;

            List<Object> list = new ArrayList<Object>();
            int j = 0;
            while (j < subSize && index < ary.length) {
                list.add(ary[index++]);
                j++;
            }

            subAryList.add(list);
        }

        return subAryList;
    }

    /**
     * 数组转字符串
     * @param mobile
     * @return
     */
    public static String ArrayToString(Object[] mobile) {
        String destId = "";
        for (Object phone : mobile) {
            destId += " " + (String) phone;
        }
        return destId.trim();
    }
}