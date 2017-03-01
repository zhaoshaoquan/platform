package com.stone.commons;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

/**
 * 图片处理工具类
 */
public class ImageUtil {

	public static Image loadImage(byte[] imagedata) {
		Image image = Toolkit.getDefaultToolkit().createImage(imagedata);
		return image;
	}

	public static Image loadImage(String filename) {
		return Toolkit.getDefaultToolkit().getImage(filename);
	}

	public static BufferedImage loadImage(File file) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return bufferedImage;
	}

	public static ImageReader getImageReader(InputStream is, String formatName)
			throws IOException {
		Iterator<ImageReader> readers = ImageIO
				.getImageReadersByFormatName(formatName);
		ImageReader reader = (ImageReader) readers.next();
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		reader.setInput(iis, true);
		return reader;
	}

	public static ImageReader getImageReader(File file) {
		String formatName = getFileSuffix(file);
		Iterator<ImageReader> readers = ImageIO
				.getImageReadersByFormatName(formatName);
		ImageReader reader = (ImageReader) readers.next();
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		reader.setInput(iis, true);
		return reader;
	}

	public static void cutImage(int x, int y, int width, int height, File file,
			File output) {
		String formatName = getFileSuffix(file);
		Iterator<ImageReader> readers = ImageIO
				.getImageReadersByFormatName(formatName);
		ImageReader reader = (ImageReader) readers.next();
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle sourceRegion = new Rectangle(x, y, width, height);
		param.setSourceRegion(sourceRegion);
		try {
			BufferedImage bufferedImage = reader.read(0, param);
			ImageIO.write(bufferedImage, ImageUtil.getFileSuffix(file), output);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static InputStream cutImage(int x, int y, int width, int height,
			ImageReader reader) {
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle sourceRegion = new Rectangle(x, y, width, height);
		param.setSourceRegion(sourceRegion);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedImage bufferedImage = reader.read(0, param);
			ImageIO.write(bufferedImage, "png", baos);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			return bais;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static ImageIcon getImageIcon(File file) {
		String filename = file.getAbsolutePath();
		return new ImageIcon(filename);
	}

	/**
	 * 通过流返回一个画了矩形边框的图片流对象
	 * 
	 * @param inputStream
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public static InputStream createRectangle(InputStream inputStream, int x,
			int y, int w, int h) {
		try {
			BufferedImage bimage = ImageIO.read(inputStream);
			Graphics2D g = bimage.createGraphics();
			g.setStroke(new BasicStroke(10.0f));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.4f));
			g.setColor(Color.RED);

			// 画一矩形边框
			g.drawRect(x, y, w, h);
			g.dispose();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bimage, "PNG", baos);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			return bais;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 给图片添加文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param fontName
	 *            水印的字体名称
	 * @param fontStyle
	 *            水印的字体样式
	 * @param color
	 *            水印的字体颜色
	 * @param fontSize
	 *            水印的字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressText(String pressText, String srcImageFile,
			String destImageFile, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write(image, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加图片水印
	 * 
	 * @param pressImg
	 *            水印图片
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param x
	 *            修正值。 默认在中间
	 * @param y
	 *            修正值。 默认在中间
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressImage(String pressImg, String srcImageFile,
			String destImageFile, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2,
					(height - height_biao) / 2, wideth_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算text的长度（一个中文算两个字符）
	 * 
	 * @param text
	 * @return
	 */
	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}

	/**
	 * 在一个指定的图片上画一个矩形边框
	 * 
	 * @param srcFile
	 *            原始文件
	 * @param destImageFile
	 *            输出文件路径
	 * @param x
	 * @param y
	 */
	public static void createRectangle(File srcFile, File destImageFile, int x,
			int y) {
		try {
			Image theImg = ImageIO.read(srcFile);
			int width = theImg.getWidth(null);
			int height = theImg.getHeight(null);
			BufferedImage bimage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bimage.createGraphics();
			g.setBackground(Color.WHITE);
			g.drawImage(theImg, 0, 0, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.4f));
			g.setColor(Color.GREEN);
			int w = 91;
			int h = 54;
			g.drawRect(x, y, w, h);
			g.dispose();
			ImageIO.write(bimage, "JPEG", destImageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将指定的图片转换成字节数组
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static byte[] imageToByteArray(File file) throws Exception {
		String fileSuffix = getFileSuffix(file);
		BufferedImage bufferedImage = ImageIO.read(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, fileSuffix, baos);
		return baos.toByteArray();
	}

	private static String getFileSuffix(File file) {
		String fileName = file.getName();
		int index = fileName.lastIndexOf(".");
		String formatName = fileName.substring(index + 1);
		return formatName;
	}

	/**
	 * 保存图片到指定的位置
	 * 
	 * @param data
	 * @param path
	 * @throws Exception
	 */
	public static void saveImage(byte[] data, String path) throws Exception {
		File outFile = new File(path);
		OutputStream output = null;
		try {
			output = new FileOutputStream(outFile);
			output.write(data);
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}

	public static void modifyImageSize(File file, int w, int h, File outFile) {
		String formatName = getFileSuffix(file);
		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			Image image = bufferedImage.getScaledInstance(w, h,
					BufferedImage.SCALE_SMOOTH);
			RenderedImage im = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			bufferedImage.getGraphics().drawImage(image, 0, 0, null);
			ImageIO.write(im, formatName, outFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 缩放后裁剪图片方法
	 * 
	 * @param srcImageFile
	 *            源文件
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param destWidth
	 *            最终生成的图片宽
	 * @param destHeight
	 *            最终生成的图片高
	 * @param finalWidth
	 *            缩放宽度
	 * @param finalHeight
	 *            缩放高度
	 * @throws IOException
	 */
	public static void abscut(String srcImageFile, String outImageFile, int x,
			int y, int destWidth, int destHeight, int finalWidth,
			int finalHeight) throws IOException {
		BufferedImage bi = ImageIO.read(new File(srcImageFile));
		abscut(bi, outImageFile, x, y, destWidth, destHeight, finalWidth,
				finalHeight);
	}

	
	/**
	 * 缩放后裁剪图片方法
	 * @param srcImage
	 *            File or InputStream or filePath
	 * @param outImage File or InputStream or filePath
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param destWidth
	 *            最终生成的图片宽
	 * @param destHeight
	 *            最终生成的图片高
	 * @param finalWidth
	 *            缩放宽度
	 * @param finalHeight
	 *            缩放高度
	 * @throws IOException
	 */
	public static void abscut(Object srcImage, Object outImage, int x, int y,
			int destWidth, int destHeight, int finalWidth, int finalHeight)
			throws IOException {
		BufferedImage bi = null;
		if(srcImage instanceof File){
			bi = ImageIO.read((File)srcImage);
		}else if(srcImage instanceof String){
			bi = ImageIO.read(new File(srcImage.toString()));
		}else if(srcImage instanceof InputStream){
			bi = ImageIO.read((InputStream)srcImage);
		}else{
			throw new RuntimeException("srcImage类型不对");
		}
		Image img;
		ImageFilter cropFilter;
		// 读取源图像
		int srcWidth = bi.getWidth(); // 源图宽度
		int srcHeight = bi.getHeight(); // 源图高度

		if (srcWidth >= destWidth && srcHeight >= destHeight) {
			Image image = bi.getScaledInstance(finalWidth, finalHeight,
					Image.SCALE_DEFAULT);// 获取缩放后的图片大小
			cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
			img = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(destWidth, destHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, null); // 绘制截取后的图
			g.dispose();
			if(outImage instanceof File){
				ImageIO.write(tag, "JPEG", (File)outImage);
			}else if(outImage instanceof String){
				ImageIO.write(tag, "JPEG", new File(outImage.toString()));
			}else if(outImage instanceof OutputStream){
				ImageIO.write(tag, "JPEG", (OutputStream)outImage);
			}else{
				throw new RuntimeException("outImage类型不对");
			}
		}
	}

	
}
