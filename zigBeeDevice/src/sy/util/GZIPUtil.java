package sy.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.log4j.Logger;

/**
 * @Title: GZIPUtil.java
 * @Description: gzip文件压缩和解压缩工具类
 * @author: zhuangxd 时间：2014-6-11 下午1:45:13
 */
public class GZIPUtil {

	private static final Logger logger = Logger.getLogger(GZIPUtil.class);

	/**
	 * @Title: pack
	 * @Description: 将一组文件打成tar包
	 * @param sources
	 *            要打包的原文件数组
	 * @param target
	 *            打包后的文件
	 * @return File 返回打包后的文件
	 * @throws
	 */
	public static File pack(File[] sources, File target) {
		FileOutputStream out = null;
//		try {
//			//target.createNewFile(); 创建文件
//		} catch (Exception e) {
//		}
		try {
			out = new FileOutputStream(target);
		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
			logger.info("pack", e1);
		}
		TarArchiveOutputStream os = new TarArchiveOutputStream(out);
		for (File file : sources) {
			try {
				
				TarArchiveEntry tarEn=new TarArchiveEntry(file); 
				tarEn.setName(file.getName());//此处需重置名称，默认是带全路径的，否则打包后会带全路径   
				if (file.getName().contains("modeNode-userid")) {
					tarEn.setName("Node.xml"); // 文件重命名
				}
				if (file.getName().contains("mode-userid")) {
					tarEn.setName("Mode.xml");
				}
				if (file.getName().contains("modeEpPage-userid")) {
					tarEn.setName("modeEpPage.xml");
				}
				os.putArchiveEntry(tarEn);
				IOUtils.copy(new FileInputStream(file), os);
				os.closeArchiveEntry();

			} catch (FileNotFoundException e) {
//				e.printStackTrace();
				logger.info("pack", e);
			} catch (IOException e) {
//				e.printStackTrace();
				logger.info("pack", e);
			}
		}
		if (os != null) {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
//				e.printStackTrace();
				logger.info("pack close", e);
			}
		}

		return target;
	}

	/**
	 * 
	 * @Title: compress
	 * @Description: 将文件用gzip压缩
	 * @param source
	 *            需要压缩的文件
	 * @return File 返回压缩后的文件
	 * @throws
	 */
	public static File compress(File source) {
		File target = new File(source.getName() + ".gz");
		FileInputStream in = null;
		GZIPOutputStream out = null;
		try {
			in = new FileInputStream(source);
			out = new GZIPOutputStream(new FileOutputStream(target));
			byte[] array = new byte[1024];
			int number = -1;
			while ((number = in.read(array, 0, array.length)) != -1) {
				out.write(array, 0, number);
			}
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			logger.info("compress", e);
			return null;
		} catch (IOException e) {
//			e.printStackTrace();
			logger.info("compress", e);
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
//					e.printStackTrace();
					logger.info("compress", e);
					return null;
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
//					e.printStackTrace();
					logger.info("compress", e);
					return null;
				}
			}
		}
		return target;
	}

	/**
	 * 解包tar文件
	 * 使用 java api 中的 ZipInputStream 类解压文件，但如果压缩时采用了
	 * org.apache.tools.zip.ZipOutputStream时，而不是 java 类库中的
	 * java.util.zip.ZipOutputStream时，该方法不能使用，原因就是编码方 式不一致导致，运行时会抛如下异常：
	 * java.lang.IllegalArgumentException at
	 * java.util.zip.ZipInputStream.getUTF8String(ZipInputStream.java:290)
	 * 当然，如果压缩包使用的是java类库的java.util.zip.ZipOutputStream 压缩而成是不会有问题的，但它不支持中文 @param
	 * archive 压缩包路径@param decompressDir 解压路径 @throws FileNotFoundException * @throws
	 * IOException
	 */
	/*
	 * 
	 * java 解压缩文件(tar,zip格式)
	 */
	public static List<String> readByZipInputStream(String archive,
			String decompressDir) throws FileNotFoundException, IOException {
		BufferedInputStream bi;
		List<String> list = new ArrayList<String>();
		// ----解压文件(ZIP文件的解压缩实质上就是从输入流中读取数据):
		// System.out.println("开始读压缩文件");

		// 这是解压ZIP格式的操作

		/*
		 * FileInputStream fi = new FileInputStream(archive); CheckedInputStream
		 * csumi = new CheckedInputStream(fi, new CRC32()); ZipInputStream in2 =
		 * new ZipInputStream(csumi); bi = new BufferedInputStream(in2);
		 * java.util.zip.ZipEntry ze;// 压缩文件条目
		 */
		// end

		// 这是解压tar包格式的操作

//		GZIPInputStream fi = new GZIPInputStream(new FileInputStream(archive));
		FileInputStream fi = new FileInputStream(archive);
		CheckedInputStream csumi = new CheckedInputStream(fi, new CRC32());
		TarArchiveInputStream in2 = new TarArchiveInputStream(csumi);
		bi = new BufferedInputStream(in2);
		TarArchiveEntry ze;

		// end

		// 遍历压缩包中的文件条目
		while ((ze = (TarArchiveEntry) in2.getNextEntry()) != null) {
			String entryName = ze.getName();
			if (ze.isDirectory()) {
				logger.info("正在创建解压目录 - " + entryName);
				File decompressDirFile = new File(decompressDir + "/"
						+ entryName);
				if (!decompressDirFile.exists()) {
					decompressDirFile.mkdirs();
				}
			} else {
				logger.info("正在创建解压文件 - " + entryName);
//				String destDirName = decompressDir.replaceAll("\\\\", "/");
				File decompressDirFile = new File(decompressDir);
				if (!decompressDirFile.exists()) {
					decompressDirFile.mkdirs();
				}
				/*if (dir.exists()) {  
		            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
		        }  
		        if (!destDirName.endsWith(File.separator)) {  
		            destDirName = destDirName + File.separator;  
		        }  
		        //创建目录  
		        if (dir.mkdirs()) {  
		            System.out.println("创建目录" + destDirName + "成功！");  
		        } else {  
		            System.out.println("创建目录" + destDirName + "失败！");  
		        }  */
				/*File target = new File(decompressDir + "/" + entryName);
				target.createNewFile(); //创建文件
*/				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(decompressDir + "/" + entryName));
				entryName = decompressDir + "/" + entryName;
				list.add(entryName.replaceAll("\\\\", "/"));
				byte[] buffer = new byte[1024];
				int readCount = bi.read(buffer);

				while (readCount != -1) {
					bos.write(buffer, 0, readCount);
					readCount = bi.read(buffer);
				}
				bos.close();
			}
		}
		bi.close();
//		logger.info("Checksum: " + csumi.getChecksum().getValue());
		return list;
	}	

	public static void main(String[] args) {
		File[] sources = new File[] { new File("task.xml"),
				new File("app.properties") };
		File target = new File("release_package.tar");
		compress(pack(sources, target));
	}
}
