package com.comntool.util.utility;


import java.awt.GraphicsEnvironment;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.*;
import java.lang.reflect.Method;
import java.net.*;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;
import org.apache.http.*;
import com.alibaba.fastjson.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfStamper;
import com.comntool.constcla.comn.ComnConst;
import java.lang.management.ManagementFactory;

import java.text.SimpleDateFormat;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;




import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.*;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;

public class Comn {

	public static final String KEY_ALGORITHM_AES= "AES";
	public static final String ALGORITHM_AES="AES/CBC/PKCS5Padding";
	public static final String KEY_ALGORITHM_DES="DES";
	public static final String ALGORITHM_DES="DES/CBC/PKCS5PADDING";
	public static final String KEY_ALGORITHM_3DES="DESede";
	public static final String ALGORITHM_3DES="DESede/CBC/PKCS5PADDING";

	public static final String KEY_ALGORITHM_RSA= "RSA";
	public static final String ALGORITHM_RSA = "RSA/ECB/PKCS1Padding";
    public static final String RSA_SIGNATURE_ALGORITHM = "MD5withRSA";
    public static final String RSA_PUBLIC_KEY = "RSAPublicKey";
    public static final String RSA_PRIVATE_KEY = "RSAPrivateKey";
    
	public static final String[] PAD_BLANK = {""," ","  ","   ","    ","     ","      ","       "};
	
	public static final String CHARCODE = "UTF-8";
	public static final String NUM_REG="[-]{0,1}[\\d]+";
	public static final String FLAG_COMMA = ",";
	public static final String MONGO_LONGSTR = "$numberLong";
	public static final String FILE_PROTOCOL__CLASSPATH="classpath:";
	public static final String dateFormat_sqlDate = "yyyyMMddHHmmss";
	public static final String dateFormat_dayTime = "ddHHmmss";
	public static final String dateFormat_dayDate = "yyyyMMdd";
	public static final String dateFormat_dateSeeStr = "yyyy-MM-dd";
	public static final String dateFormat_dateStr = "yyyy-MM-dd HH:mm:ss";
	public static final String dateFormat_dateStrMilli = "yyyy-MM-dd HH:mm:ss.SSS";
	
	private static Logger log = LoggerFactory.getLogger(Comn.class);
	public static void main(String[] args) {
		// testSplit();
		// test_coltReduce();
		
		// testJson2();
		// testFastjson();
		// test_mapTransmit();
		test_time();
	}
	

	public static void test_time(){
		pl(getDateStrMilli());
	}

	public static void test_mapTransmit(){
		List<Map<Object, Object>> list = new ArrayList<>();

		Map<Object, Object> map1 = new HashMap<>();
		map1.put("123", "456");
		map1.put("abc", "def");
		map1.put("甲乙丙", "丁厂");
		map1.put("aaa", "456v");
		map1.put("bbb", "bbb");
		Map<Object, Object> map2 = new HashMap<>();
		map2.put("456", "456v");
		map2.put("def", "defv");
		map2.put("丁厂", "丁厂v");
		Map<Object, Object> map3 = new HashMap<>();
		map3.put("456v", "456-3");
		map3.put("defv", "def-3");
		map3.put("丁厂v", "丁厂-3");
		

		Map<Object, Object> map4 = new HashMap<>();
		map4.put("456-3", 222L);
		map4.put("def-3", 111L);
		map4.put("丁厂-3", 333L);
		
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		
		Map<Object, Object> retn = mapTransmit(list);
		if(MapUtils.isNotEmpty(retn)) {
			for(Entry<Object, Object> enty : retn.entrySet()) {
				pl(enty.getKey()+","+enty.getValue());
			}
		}
		
	}


	public static void testAES16(){
		String orgStr = "7C8664159B381A204F40C78A386D404CBF1F441CDC2C27E630DE0B4FBB4C2363CF483D257EEE69D0052237A46885A8EFA8F627CE57C3B96633D9D42C2256B485C37BC25DA8D6D0386C8917C030D49AE820E81F34DF40399FE91EF0B82B268679FF60BDF47AD529E262921CBD282DC3A44DE1BA1682AA7AD31AFD354C498F605666095B7402406878A8F573CC8BD6CCE04E9D1721544444F7FC07F7531BE76D7702C96E48529828CEBB32DCF2B652558FD8EBBC0C4069489A92447FE5189E94D8589136C71A2251C39E3D89AAA22D51420B92CA0D07A2D8E7B05154E16AF5402C19D1EB5C82681BF4C332C4C91DD1524F88D777E3ABCDE29CBC1E6039475D0224C01407AA10640694D2D5A28C6FBCD8C6A407DDC4F51DC9983F084DD48966F372";
		String password="b085e26255804668b03a1682a6019df7";
		//String plaintext = Comn.aesDecryptStr16(orgStr, password);
		//pl(plaintext);
		String aa ="{\"count\":0,\"dynamicPwd\":null,\"entityInfo\":null,\"entityInfos\":null,\"ids\":null,\"intactStr\":null,\"objMap\":null,\"queryReqStruct\":{\"batchMapParam\":null,\"limit\":10,\"orderByMap\":null,\"pojoBatch\":null,\"pojoBlur\":null,\"pojoExact\":null,\"queryStr\":null,\"queryType\":\"advert\",\"start\":0},\"txPwd\":null}";
		String password2="925845a473bc4fb69cecdabdd5b0041c";
		String bb = Comn.aesEncryptStr16(aa, password2);
		pl(bb);
		
		// aa= "CA95ACB84AC9E7BD259767BB8C624EB3525ECD936112A5AA18B945EEEE9710F313A79EE93BA3A400757E1F4C54B1DC62E0A446E68A90D6C7F652FAA94ABAB55567BC90DD8DF012F02CE545EF4E8AF430714EE5B082B9C8AA8E0874482D4CDC95B0B5AABFE267FC7ADF56F4905A89D742FAAC74CF6587B2A7702296C0266E859D38229827BF7D2FCADF4A6457CE0FFD703FCC5734433A57CBD5F755469746A979C89F3F0510A6582B9E852CD3F0EB67F4BDF8FF55B15D9E610F39EC509093A7CDDB76419A89DACA9BD7923C66CA8E5536AEE0B08342A092F50DCE046C8F4BA0CBE9FFCCC152DE05AD6BE7E46C7CB363ECA99E0079333A5F7406C42644F9DAD3C698877E65FF57A4CF5C0A6ABC822F8B7D4F23D91820BCDBBC4B4B4A0EB3B4CC1A";
		// password2="af3f7d24bbe84e27befcd2fc13631fd6";
		bb = Comn.aesDecryptStr16(bb, password2);
		pl(bb);
	}
	

	
	public static void testLamb(){
		List<String> list = new ArrayList<>();
		list.add("sadfsdf");
		list.add("1234sd");
		list.add("5431");
		list.forEach(a->{
			pl(a);
		});
		pl("12341234");
	}

	public static void testSplit(){
		String aa = "23-";
		String[] aas = aa.split("-");
		pArr(aas);
	}
	public static void test_coltReduce(){
		List<String> list1=  Arrays.asList(new String[]{"123123","123","123","321","31","321","asd","aaa","bbb","bbb"});
		List<String> list2=  Arrays.asList(new String[]{"123","31","321","asd","asd"});
		Collection<String> list3 = coltReduce(list1, list2);
		pColt(list3);
	}

	public static void testJson2(){
		List<String> list = new ArrayList<>();
		list.add("sadfsdf");
		pl(JSONObject.toJSONString(list));
	}
	public static void testJson(){
		String json = file2String("D:\\datum\\data\\conf\\testData.txt");
		Map map = JSONObject.parseObject(json, Map.class);
		pl(map);
	}
	
	public static void t() {
		boolean input = true;
		int[] aaa = new int[3];
		int i=0;
		Scanner scan =  new Scanner(System.in);
		while(input) {
			String user = scan.next();
			if("end".equals(user)) {
				break;
			}
			
			aaa[i]=Integer.valueOf(user);
			i++;
		}
		scan.close();
		int high=0, total=0, ave=0;
		for(int bb : aaa) {
			if(bb>high) {
				high=bb;
			}
			total += bb;
		}
		
		ave = total/aaa.length;
		
		System.out.println("high="+high+", total="+total+", ave="+ave);
	}
	
	public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
	    double[] bd_lat_lon = new double[2];
	    double PI = 3.14159265358979324 * 3000.0 / 180.0;
	    double x = gd_lon, y = gd_lat;
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
	    bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
	    bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
	    return bd_lat_lon;
	}
	
	public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
	    double[] gd_lat_lon = new double[2];
	    double PI = 3.14159265358979324 * 3000.0 / 180.0;
	    double x = bd_lon - 0.0065, y = bd_lat - 0.006;
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
	    gd_lat_lon[0] = z * Math.cos(theta);
	    gd_lat_lon[1] = z * Math.sin(theta);
	    return gd_lat_lon;
	 }

	public static void p(Object o){
		System.out.print(o);
	}
	public static void p(String... cmds) {
        StringBuilder text = new StringBuilder("$ ");
        for (String cmd : cmds) {
            text.append(cmd).append(" ");
        }
        System.out.println(text.toString());
    }
	public static void pl(Object o){
		System.out.println(o);
	}
	public static void pl(){
		System.out.println();
	}

	
	public static String removeStart65279(String str){
		char cha=65279;
		while(str.startsWith(cha+"")){
			if(str.length()==1){
				return "";
			}
			str=str.substring(1, str.length());
		}
		return str;
	}
	
	
	/**
	 * @param conf 值范围对应的目标。例：值为0_50:A,0_80:B另一条数据：值为0_50:C,0_80:D
	 * @param num 要查找的值
	 * @return
	 */
	public static String rangeParse(String conf, Integer num){
		if(StringUtils.isBlank(conf) || num==null){
			return null;
		}
		String[] strs = conf.split(ComnConst.COMMA);
		String aimStr = null;
		for(String str : strs){// 得到形如："0-50:A"结构的数据
			if(StringUtils.isBlank(str)){
				continue;
			}
			String[] sts = str.split(ComnConst.CLN);
			if(sts.length<2){
				continue;
			}
			if(StringUtils.isBlank(sts[0])){
				continue;
			}
			
			String aim = sts[1];// 要判断的目标数据
			String[] ss = sts[0].split(ComnConst.UNDERSCORE);// 
			if(StringUtils.isBlank(ss[0])){
				continue;
			}
			int int1 = Integer.valueOf(ss[0]);
			if(ss.length>1){
				int int2 = Integer.valueOf(ss[1]);
				if(int1 <= num && int2 > num){ // 如果油的得分在这个范围内，则固定了油的范围
					aimStr = aim;
					break;
				}
			}else{
				if(int1 <= num){ // 如果油的得分在这个范围内，则固定了油的范围
					aimStr = aim;
					break;
				}
			}
		}
		
		return aimStr;
	}
	

	public static String getDateStr() {
		String date = getDateStr(new Date(), dateFormat_dateStr);
		return date;
	}
	public static String getDateStrMilli() {
		String date = getDateStr(new Date(), dateFormat_dateStrMilli);
		return date;
	}

	public static String getDateStr(Date dateParam, String format) {
		if(dateParam == null){
			return null;
		}
		SimpleDateFormat formatDate = new SimpleDateFormat(format);
		String date = formatDate.format(dateParam);
		return date;
	}
	
	/**
	 * 将显示的时间字符串转换成时间对象
	 * @param dateStr
	 * @return
	 */
	public static Date getDateFromDis(String dateStr, String dateFormatStr) {
		Date dateObj = null;
		try{
			dateObj = StringUtils.isBlank(dateStr) ? null : DateUtils.parseDateStrictly(
					dateStr, new String[]{dateFormatStr});
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return dateObj;
	}


	/**
	 * 判断是是否包含指定字符串中的一个
	 * @param s
	 * @param ss
	 * @return
	 */
	public static boolean trimContainsAny(String s, String ss){
		if(s==null && ss == null){
			return true;
		}
		return trimContainsAny(s, ss.split(","));
	}

	/**
	 * 判断是是否包含指定字符串中的一个
	 * @param s
	 * @param ss
	 * @return
	 */
	public static boolean trimContainsAny(String s, String[] ss){
		if(s==null && ss == null){
			return true;
		}
		if(StringUtils.isBlank(s) || ArrayUtils.isEmpty(ss)){
			return false;
		}
		for(String cs:ss){
			if(cs==null){
				continue;
			}
			if(s.contains(cs.trim())){
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是是否包含指定字符串中的一个
	 */
	public static boolean containsAny(String s, String[] col){
		return containsAny(s, Arrays.asList(col));
	}
	
	/**
	 * 判断是是否包含指定字符串中的一个
	 */
	public static boolean containsAny(String s, Collection<String> col){
		if(s==null && col == null){
			return true;
		}
		if(StringUtils.isBlank(s) || CollectionUtils.isEmpty(col)){
			return false;
		}
		for(String cs:col){
			if(s.contains(cs));{
				return true;
			}
		}
		return false;
	}


	/**
	 * 将源数组original中的数据从索引from(包括)到to(不包括)考贝到要返回的数组中
	 * 警告：to和from必须在0到original.length（包括）之间，to必须大于from，否则抛出异常
	 * 
	 * @param original
	 * @param from
	 * @param to
	 * @return
	 * @throws Exception
	 */
	public static byte[] copyOfRange(byte[] original, int from, int to)
			throws Exception {
		byte[] target = new byte[to - from];

		if (original == null) {
			throw new NullPointerException();
		}
		if (from < 0 || from > original.length) {
			throw new ArrayIndexOutOfBoundsException();
		}
		if (from > to) {
			throw new IllegalArgumentException();
		}
		for (int i = 0, j = from; i < to - from; i++, j++) {
			target[i] = original[j];
		}

		return target;

	}

	public static boolean writeFileAndCreate(String writeStr, String fileFullPath){
		File file=new File(fileFullPath);
		if(file.exists()){
			return false;
		}else{
			try{
				file.createNewFile();
				writeFile(writeStr, file);
			}catch(Exception e){
				log.error(e.getMessage(), e);
			}
		}
		return true;
	}

	public static boolean writeFileReplace(String writeStr, String fileFullPath){
		File file=new File(fileFullPath);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(Exception e){
				log.error(e.getMessage(), e);
			}
		}
		writeFile(writeStr, file);
		return true;
	}

	public static boolean writeFile(String writeStr, File infoFile){
		Writer writer = null;
		BufferedWriter fileWriter = null;
		try {
			writer = new FileWriter(infoFile, true);
			fileWriter = new BufferedWriter(writer);
			fileWriter.append(writeStr);
			fileWriter.newLine();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return true;
	}
	
    /** 
     * 按UTF-8格式读取文件为一个内存字符串,保持文件原有的换行格式 
     * @param filePath 文件路径 
     * @return 文件内容的字符串 
     */ 
	public static String file2HEX(String filePath) {
		StringBuffer sb = new StringBuffer();
		BufferedInputStream in = null;
		int bytesRead = 0;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buffer = new byte[1024];
			while ((bytesRead = in.read(buffer)) != -1) {
		        byte[] bs = new byte[bytesRead];
		        System.arraycopy(buffer, 0, bs, 0, bytesRead);
				sb.append(parseByte2HexStr(bs));
			}
		} catch (FileNotFoundException e) {
			log.error("读取文件为一个内存字符串失败，失败原因所给的文件" + filePath + "不存在！", e);
			return null;
		} catch (IOException e) {
			log.error("读取文件为一个内存字符串失败，失败原因是读取文件异常！", e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				log.error("关闭文件异常！", e);
			}
		}
		return sb.toString();
	}
	
    /** 
     * 按UTF-8格式读取文件为一个内存字符串,保持文件原有的换行格式 
     * @param filePath 文件路径 
     * @return 文件内容的字符串 
     */ 
	public static String file2String(String filePath) {
		String str = file2String(filePath, "UTF-8");
		return str;
	}
	
    /** 
     * 读取文件为一个内存字符串,保持文件原有的换行格式 
     * @param filePath 文件路径 
     * @param charset 文件字符集编码 
     * @return 文件内容的字符串 
     */ 
	public static String file2String(String filePath, String charset) {
		File fileObj = new File(filePath);
		if(fileObj.exists()== Boolean.FALSE){
			return null;
		}
		String str = file2String(fileObj, charset);
		return str;
	}
	
    /** 
     * 读取文件为一个内存字符串,保持文件原有的换行格式 
     * @param file 文件对象 
     * @param charset 文件字符集编码 
     * @return 文件内容的字符串 
     */ 
	public static String file2String(File file, String charset) {
		StringBuffer sb = new StringBuffer();
		LineNumberReader reader = null;
		try {
			reader = new LineNumberReader(new InputStreamReader(
					new FileInputStream(file), charset));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			}
		} catch (UnsupportedEncodingException e) {
			log.error("读取文件为一个内存字符串失败，失败原因是使用了不支持的字符编码" + charset, e);
			return null;
		} catch (FileNotFoundException e) {
			log.error("读取文件为一个内存字符串失败，失败原因所给的文件" + file + "不存在！", e);
			return null;
		} catch (IOException e) {
			log.error("读取文件为一个内存字符串失败，失败原因是读取文件异常！", e);
			return null;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				log.error("关闭文件异常！", e);
			}
		}
		return sb.toString();
	}


	/**
	 * 读取文件到字节数组。最大只支持512M，超过的情况下返回null
	 * @param file 文件对象
	 * @return 文件内容的字节数组
	 */
	public static boolean byte2file(File file, byte[] fileByte) {
		if(file==null || fileByte==null){
			return false;
		}
		if(file.exists()== Boolean.FALSE){
			boolean createFileStatus = createFile(file.getAbsolutePath());
			if(createFileStatus == Boolean.FALSE){
				return createFileStatus;
			}
		}
		OutputStream bos = null;
		try {
			bos = new FileOutputStream(file);
			bos.write(fileByte);
			return true;
		} catch (Exception e) {
			log.error("read file fail", e);
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				log.error("file close fail", e);
			}
		}
		return false;
	}

    /** 
     * 将内存字符串按照utf-8格式写入到磁盘的文件中 
     * @param str 要写入的字符串 
     * @param file 文件对象 
     * @param charset 文件字符集编码 
     * @return 文件内容的字符串 
     */ 
	public static void str2file(String str, String filePath) {
		str2file(str, new File(filePath), "UTF-8");
	}
	
    /** 
     * 将内存字符串写入到磁盘的文件中 
     * @param str 要写入的字符串 
     * @param file 文件对象 
     * @param charset 文件字符集编码 
     * @return 文件内容的字符串 
     */ 
	public static void str2file(String str, File file, String charset) {
		BufferedWriter writer = null;
		Writer writerStream = null;
		OutputStream out = null;
		try {
			if(!file.exists()){
				createDir(file.getParent());
				if(!file.exists()){
					file.createNewFile();
				}
			}
			out = new FileOutputStream(file);
			writerStream = new OutputStreamWriter(out, charset);
			writer = new BufferedWriter(writerStream);
			writer.write(str);
		} catch (UnsupportedEncodingException e) {
			log.error("读取文件为一个内存字符串失败，失败原因是使用了不支持的字符编码" + charset, e);
		} catch (FileNotFoundException e) {
			log.error("读取文件为一个内存字符串失败，失败原因所给的文件" + file + "不存在！", e);
		} catch (IOException e) {
			log.error("读取文件为一个内存字符串失败，失败原因是读取文件异常！", e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (writerStream != null) {
					writerStream.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				log.error("关闭文件异常！", e);
			}
		}
	}


	public static List<Integer> str2intList(List<String> list){
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		List<Integer> elems = new ArrayList<>();
		for(String elem : list){
			elems.add(Integer.valueOf(elem));
		}
		return elems;
	}

	public static List<Integer> long2intList(List<Long> list){
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		List<Integer> elems = new ArrayList<>();
		for(Long elem : list){
			elems.add(elem.intValue());
		}
		return elems;
	}

	public static List<Long> int2longList(List<Integer> list){
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		List<Long> elems = new ArrayList<>();
		for(Integer elem : list){
			elems.add(elem.longValue());
		}
		return elems;
	}
	

	/**
	 * 将多个文件合并成一个
	 * @param srcStr 源文件夹
	 * @param destStr 目标文件夹
	 * @param specifySuffix 文件扩展名。如果不为空，符合扩展名的才合并；如果不为空，所有的文件都合并
	 * @return 执行结果
	 */
	public static String merge(String srcStr, String destStr, String specifySuffix){
		return merge(srcStr, destStr, "merge.txt", specifySuffix);
	}
	/**
	 * 将多个文件合并成一个
	 * @param srcStr 源文件夹
	 * @param destStr 目标文件夹
	 * @param specifySuffix 文件扩展名。如果不为空，符合扩展名的才合并；如果不为空，所有的文件都合并
	 * @return 执行结果
	 */
	public static String merge(String srcStr, String destStr, String aimFileName, String specifySuffix){
		if(StringUtils.isBlank(srcStr) || StringUtils.isBlank(destStr)){
			return "源文件夹或者目标文件夹/文件夹为空";
		}
		if(!srcStr.endsWith(File.separator)){
			srcStr+=File.separator;
		}
		if(!destStr.endsWith(File.separator)){
			destStr+=File.separator;
		}
		
		
		File srcDir = new File(srcStr);
		createDir(destStr);
		File destFile = new File(destStr);
		return merge(srcDir, destFile, aimFileName, specifySuffix);
		
	}
	

	/**
	 * 将多个文件合并成一个
	 * @param srcDir 源文件夹
	 * @param destFile 目标文件夹
	 * @param specifySuffix 文件扩展名。如果不为空，符合扩展名的才合并；如果不为空，所有的文件都合并
	 * @return 执行结果
	 */
	public static String merge(File srcDir, File destFile, String aimFileName, final String specifySuffix){
		if(srcDir == null || !srcDir.exists() || !srcDir.isDirectory()){
			return "选择的源文件夹不存在或者无效";
		}
		if(destFile == null || !destFile.exists()){
			return "选择的目标文件或者文件夹不存在或者无效";
		}
		if(StringUtils.contains(destFile.getAbsolutePath()+File.separator, srcDir.getAbsolutePath()+File.separator)){
			return "选择的目标文件夹不能存在于源文件夹中";
		}
		File destFileR = null;
		if(destFile.isDirectory()){
			destFileR = new File(destFile.getAbsolutePath() +File.separator+ aimFileName);
			try{
				destFileR.createNewFile();
			}catch(Exception e){
				e.printStackTrace();
				return "创建文件错误："+destFileR.getAbsolutePath();
			}
		}
		
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(destFileR));
			mergeOnly(srcDir, bw, specifySuffix);
		}catch(Exception e){
			e.printStackTrace();
			return "写文件错误："+destFile.getAbsolutePath();
		}finally{
			try{
				if(bw != null){
					bw.flush();
					bw.close();
				}
			}catch(Exception e){
				return "关闭文件错误："+destFile.getAbsolutePath();
			}
		}
		
		return "操作完成";
	}
	
	/**
	 * 将多个文件合并成一个
	 * @param srcDir 源文件夹
	 * @param bw 目标文件
	 * @param specifySuffix 文件扩展名。如果不为空，符合扩展名的才合并；如果不为空，所有的文件都合并
	 * @return 执行结果
	 */
	public static String mergeOnly(File srcDir, BufferedWriter bw, final String specifySuffix){
		if(bw == null){
			return "输出流为空";
		}
		 
		File[] currFiles = null;
		if(StringUtils.isNotBlank(specifySuffix)){
			currFiles = srcDir.listFiles(new FileFilter(){
				@Override
				public boolean accept(File pathname){
					try{
						return pathname.getAbsolutePath().endsWith(specifySuffix);
					}catch(Exception e){
						e.printStackTrace();
					}
					return false;
				}
			});
		}else{
			currFiles = srcDir.listFiles();
		}
		
		for(File f : currFiles){
			if(f.isFile()){// 合并到目标文件
				BufferedReader br = null;
				try{
					br = new BufferedReader(new FileReader(f));
					String t = null;
					while((t=br.readLine())!=null){
						bw.write("\n");
						bw.write(t);
					}
				}catch(Exception e){
					return "读取文件错误："+f.getAbsolutePath();
				}finally{
					try{
						if(br != null){
							br.close();
						}
					}catch(Exception e){
						return "关闭文件错误："+f.getAbsolutePath();
					}
				}
			}else{
				mergeOnly(f, bw, specifySuffix);
			}
		}
		return null;
	}


	//  String[] strs = word.split(ComnConst.GRAVE_ACCENT);
	public static boolean mergePDFByStamper(String srcFilePath, String targFileaPath,  List<List<String>> words) {
		if(CollectionUtils.isEmpty(words)){
			log.info("words is empty targFileaPath={}", targFileaPath);
			return false;
		}

		PdfStamper stamper =null;
		try{
			BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
			Font font = new Font(baseFont);
			com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(new FileInputStream(srcFilePath));
			stamper = new PdfStamper(reader, new FileOutputStream(targFileaPath));
			int llx =0, lly=0, urxOff= 0, uryOff=0;


			for(List<String> word : words) {
				// word示例： 张三,12,1,20,30,200。依次为：填入的文本,文本字体尺寸,页码（从1开始）,横坐标,纵坐标,宽度
				if(word.size()<5){
					log.info("element less than 3, word size={}", word.size());
					continue;
				}
				String sentence = word.get(0);
				int fontSize = Integer.parseInt(StringUtils.isBlank(word.get(1))? "12" : word.get(1));
				//页数从1开始
				int pageNumber = Integer.parseInt(word.get(2).trim());
				//单位应该是px
				float left = Integer.parseInt(word.get(3).trim());;
				float bottom = Integer.parseInt(word.get(4).trim());;
				float width = word.size()<6 ? 200 : Integer.parseInt(word.get(5).trim());


				PdfContentByte over = stamper.getOverContent(pageNumber);
				ColumnText columnText = new ColumnText(over);
				columnText = new ColumnText(over);
				columnText.setSimpleColumn(left, bottom, left+width, bottom+40);
				com.itextpdf.text.Paragraph element = new com.itextpdf.text.Paragraph(sentence, font);
				// 设置字体，如果不设置添加的中文将无法显示
				element.setFont(font);
				columnText.addElement(element);
				columnText.go();
			}
			log.info("write finish targFileaPath={}", targFileaPath);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			return false;
		} finally {
			try {
				stamper.close();
			}catch (Exception e){
				log.info(e.getMessage(), e);
			}
		}
		return true;
	}

	public static String mergePDFTable(int pageNum, int[] posWidth, String filePath, String savePath, String title, String[] head2, String[][] rowsArray){
		List<List<String>> rows = arrDyadic2list(rowsArray);
		return mergePDFTable(pageNum, posWidth, filePath, savePath, title, head2, rows);
	}
	public static String mergePDFTable(int pageNum, int[] posWidth, String filePath, String savePath, String title, String[] head2, List<List<String>> rows){
		PdfStamper stamper =null;
		try {
			com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(new FileInputStream(filePath));
			stamper = new PdfStamper(reader, new FileOutputStream(savePath));

			BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
			Font font = new Font(baseFont);

			float[] widths = {100, 110, 190,150};
			PdfPTable table = new PdfPTable(widths);
			table.setTotalWidth(800);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell pdfCell_head = new PdfPCell(); //表格的单元格
			pdfCell_head.setColspan(4);
			pdfCell_head.setBackgroundColor(new BaseColor(200,200,200));
			pdfCell_head.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdfCell_head.setPhrase(new com.itextpdf.text.Paragraph(title, font));
			table.addCell(pdfCell_head);

			if(ArrayUtils.isNotEmpty(head2)) {
				for (int j = 0; j < head2.length; j++) {
					PdfPCell pdfCell = new PdfPCell(); //表格的单元格
					pdfCell.setBackgroundColor(new BaseColor(200, 200, 200));
					pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					com.itextpdf.text.Paragraph paragraph = new com.itextpdf.text.Paragraph(head2[j], font);
					pdfCell.setPhrase(paragraph);
					table.addCell(pdfCell);
				}
			}

			int i= 0, leng = rows.size(), firstPageRowNum = 44;
			for(;i<firstPageRowNum && i<leng;) {
                List<String> row = rows.get(i);
				for(String cellStr : row) {
					PdfPCell pdfCell = new PdfPCell(); //表格的单元格
					pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					com.itextpdf.text.Paragraph paragraph = new com.itextpdf.text.Paragraph(cellStr, font);
					pdfCell.setPhrase(paragraph);
					table.addCell(pdfCell);
				}
                ++i;
			}

			// 参考：  https://blog.csdn.net/qq_36537108/article/details/86635613
			PdfContentByte over = stamper.getOverContent(pageNum);
			ColumnText columnText = new ColumnText(over);
			// llx 和 urx  最小的值决定离左边的距离. lly 和 ury 最大的值决定离下边的距离 17, 793, 584, 300
			columnText.setSimpleColumn(posWidth[0], posWidth[1], posWidth[2], posWidth[3]);
			columnText.addElement(table);
            columnText.go();

			// 处理后续页
			if(leng>firstPageRowNum){
                int tableCurrPageNum = 2;
			    int tableRowNum = 46;
			    int levelRowNum = leng - firstPageRowNum;
                int tablePageNum = levelRowNum%tableRowNum > 0? (levelRowNum/tableRowNum+1) : levelRowNum/tableRowNum;
			    for(; tableCurrPageNum<= tablePageNum+1; ++tableCurrPageNum) {
                    PdfPTable tableFollow = new PdfPTable(widths);
                    tableFollow.setTotalWidth(800);
                    tableFollow.setHorizontalAlignment(Element.ALIGN_CENTER);

                    for(int j=0; j<tableRowNum && i<leng; ++j) {
                        List<String> row = rows.get(i);
                        for (String cellStr : row) {
                            PdfPCell pdfCell = new PdfPCell(); //表格的单元格
                            pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            com.itextpdf.text.Paragraph paragraph = new com.itextpdf.text.Paragraph(cellStr, font);
                            pdfCell.setPhrase(paragraph);
                            tableFollow.addCell(pdfCell);
                        }
                        ++i;
                    }

                    int newPageNum = pageNum+tableCurrPageNum-1;
                    stamper.insertPage(newPageNum, reader.getPageSizeWithRotation(pageNum));
                    PdfContentByte overFollow = stamper.getOverContent(newPageNum);
                    ColumnText columnTextFollow = new ColumnText(overFollow);
                    // llx 和 urx  最小的值决定离左边的距离. lly 和 ury 最大的值决定离下边的距离 17, 793, 584, 300
                    columnTextFollow.setSimpleColumn(posWidth[0], posWidth[1], posWidth[2], posWidth[3]);
                    columnTextFollow.addElement(tableFollow);
                    columnTextFollow.go();
                }

            }

		}catch (Exception e){
			log.info(e.getMessage(), e);
			return null;
		} finally {
			try {
				stamper.close();
			}catch (Exception e) {
				log.info(e.getMessage(), e);
			}
		}
		return savePath;
	}

	public static Font getPdfChineseFont() throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
				BaseFont.NOT_EMBEDDED);
		Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);
		return fontChinese;
	}

	public static byte[] str2byte(String str){
		if(str==null){
			return null;
		}
		try{
			return str.getBytes(CHARCODE);
		}catch (Exception e) {
			log.error("转换失败", e);
			return null;
		}
	}

	
	/**
	 * 将字符串拆分，过滤掉空白，截掉前后的空白。去重
	 * @param origin 需要处理的源字符串
	 * @param split 拆分的分隔字符串。如果为空，默认为英文逗号:","
	 * @return
	 */
	public static Set<String> str2set(String origin){
		List<String> list = str2list(origin, null);
		Set<String> set = new HashSet<>(list);
		return set;
	}
	
	/**
	 * 将字符串拆分，过滤掉空白，截掉前后的空白。去重
	 * @param origin 需要处理的源字符串
	 * @param split 拆分的分隔字符串。如果为空，默认为英文逗号:","
	 * @return
	 */
	public static Set<String> str2set(String origin, String split){
		List<String> list = str2list(origin, split);
		Set<String> set = new HashSet<>(list);
		return set;
	}


	public static Set<Long> str2Long4set(Set<String> strs){
		Set<Long> set = new HashSet<>();
		if(CollectionUtils.isEmpty(strs)){
			return null;
		}
		for(String str : strs){
			if(StringUtils.isBlank(str)){
				continue;
			}
			if(str.matches("\\d+")){
				set.add(Long.valueOf(str));
			}
		}
		return set;
	}

	/**
	 * 将字符串拆分，过滤掉空白，截掉前后的空白
	 * @param origin 需要处理的源字符串
	 * @param split 拆分的分隔字符串。如果为空，默认为英文逗号:","
	 * @return
	 */
	public static List<String> str2list(String origin, String split){
		return str2list(origin, split, 0, Integer.MAX_VALUE);
	}
	/**
	 * 将字符串拆分，过滤掉空白，截掉前后的空白
	 * for optimize
	 * @param origin 需要处理的源字符串
	 * @param split 拆分的分隔字符串。如果为空，默认为英文逗号:","
	 * @return 转换后的有效字符串列表
	 */
	public static List<String> str2list(String origin, String split, Integer startLine,  Integer endLine){
		List<String> list = new ArrayList<>();

		if(StringUtils.isBlank(origin)){
			return list;
		}
		origin= origin.trim();
		if(StringUtils.isBlank(split)){
			split = ComnConst.COMMA;
		}
		String[] strs = origin.split(split);

		startLine = startLine==null? 0 : startLine;
		endLine = endLine == null? Integer.MAX_VALUE : endLine;
		int i=-1;
		for(String str : strs){
			++i;
			if(i < startLine || StringUtils.isBlank(str)){
				continue;
			}
			if(i > endLine || (str.contains("(") && !str.contains("="))) {
				return list;
			}
			list.add(str.trim());
		}
		return list;
	}

	/**
	 * 将字符串转换成map。
	 * @param str 需要转换的字符串
	 * @param rowSplit 用于拆分每条记录的字符串
	 * @param colSplit 用于拆分key, value的字符串。只取前两个元素作为key, value。
	 * @return
	 */
	public static  Map<String, String> str2map(String str, String rowSplit, String colSplit){
		Map<String, String> retnMap = new HashMap<>();
		String[] strs = str.split(rowSplit);
		for(String strRow : strs){
			if(StringUtils.isBlank(strRow)){
				continue;
			}
			strRow = strRow.trim();
			String[] strs2 = strRow.split(colSplit);
			if(StringUtils.isNotBlank(strs2[0])){
				retnMap.put(strs2[0].trim(), strs2[1].trim());
			}
		}
		return retnMap;
	}

	/**
	 * 将字符串转换成map。
	 * @param str 需要转换的字符串
	 * @param rowSplit 用于拆分每条记录的字符串
	 * @param colSplit 用于拆分key, value的字符串。只取前三个元素作为key, key, value。
	 * @return
	 */
	public static Map<String, Map<String, String>> str2map2(String str, String rowSplit, String colSplit){
		Map<String, Map<String, String>> retnMap = new HashMap<>();
		String[] strs = str.split(rowSplit);
		for(String strRow : strs){
			if(StringUtils.isBlank(strRow)){
				continue;
			}
			strRow = strRow.trim();
			String[] strs2 = strRow.split(colSplit);
			Map<String, String> rowMap = retnMap.get(strs2[0].trim());
			if(rowMap == null){
				rowMap = new HashMap<>();
				retnMap.put(strs2[0].trim(), rowMap);
			}
			if(StringUtils.isNotBlank(strs2[2])){
				rowMap.put(strs2[1].trim(), strs2[2].trim());
			}
		}
		return retnMap;
	}


	/**
	 * 将字符串转换成map。跟str2map2的区别是：最终的值是个List，用于处理2级key重复
	 * @param str 需要转换的字符串
	 * @param rowSplit 用于拆分每条记录的字符串
	 * @param colSplit 用于拆分key, value的字符串。只取前三个元素作为key, key, value。
	 * @return
	 */
	public static Map<String, Map<String, List<String>>> str2mapList(String str, String rowSplit, String colSplit){
		Map<String, Map<String, List<String>>> retnMap = new HashMap<>();
		String[] strs = str.split(rowSplit);
		for(String strRow : strs){
			if(StringUtils.isBlank(strRow)){
				continue;
			}
			strRow = strRow.trim();
			String[] strs2 = strRow.split(colSplit);
			Map<String, List<String>> rowMap = retnMap.get(strs2[0].trim());
			if(rowMap == null){
				rowMap = new HashMap<>();
				retnMap.put(strs2[0].trim(), rowMap);
			}
			List<String> elemList = rowMap.get(strs2[1].trim());
			if(elemList == null){
				elemList = new ArrayList<>();
				rowMap.put(strs2[1].trim(), elemList);
			}
			if(StringUtils.isNotBlank(strs2[2])){
				elemList.add(strs2[2].trim());
			}
		}
		return retnMap;
	}


	/**
	 * 多个map合成一个map。前一个map的value必须与下一个map的key的值的类型要一致
	 * @param listMap
	 * @return
	 */
	public static Map<Object, Object> mapTransmit(List<Map<Object, Object>> listMap){
		if(CollectionUtils.isEmpty(listMap)) {
			return null;
		}
		if(listMap.size() == 1) {
			return listMap.get(0);
		}
		Map<Object, Object> mapPre = listMap.get(0);
		
		for(int i=1, leng= listMap.size(); i< leng; ++i) {
			Map<Object, Object> mapCurr = listMap.get(i);
			for(Map.Entry<Object, Object> enty : mapPre.entrySet()) {
				mapPre.put(enty.getKey(), mapCurr.get(enty.getValue()));
			}
		}
		return mapPre;
	}

	/**
	 * 将map转换成字符串
	 * for optimize
	 * @param map
	 * @param split
	 * @return
	 */
	public static String map2str(Map<Object, Object> map) {
		return map2str(map, "\n");
	}
	/**
	 * 将map转换成字符串
	 * for optimize
	 * @param map
	 * @param split
	 * @return
	 */
	public static String map2str(Map<Object, Object> map, String split) {
		StringBuilder sb = new StringBuilder();
		if(MapUtils.isNotEmpty(map)) {
			for(Map.Entry<Object, Object> enty : map.entrySet()){
				sb.append(enty.getKey()).append(ComnConst.EQUAL_SIGN).append(enty.getValue()).append(split);
			}
		}
		return sb.toString();
	}
	
	/**	
	 * 将字节数组加密成base64字符串
	 * @param src 明文的字节数组
	 * @return 经过base64计算后的base64格式的字符串
	 */
	public static String base64Encode(byte[] src) {
		byte[] encodeBase64 = Base64.encodeBase64(src);  
		return new String(encodeBase64);
	}
	
	/**	
	 * 将字符串转换成字节数组加密成base64字符串
	 * @param src 明文的字节数组
	 * @return 经过base64计算后的base64格式的字符串
	 */
	public static String base64Encode(String src) {
		try{
			byte[] encodeBase64 = Base64.encodeBase64(src.getBytes("UTF-8"));  
			return new String(encodeBase64);
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
		return null;
	}
	
	/**	
	 * 将base64字符串解密成字节数组
	 * @param base64 base64格式的字符串
	 * @return 解密后的字节数组
	 */
	public static byte[] base64Decode(String base64) {
		try{
			byte[] strByte = Base64.decodeBase64(base64.getBytes("UTF-8"));  
			return strByte;
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
		return null;
	}
	

	/**	
	 * 将base64字符串解密成字节数组，再转成字符串
	 * @param base64 base64格式的字符串
	 * @return 解密后的字节数组
	 */
	public static String base64Decode2str(String base64) {
		try{
			byte[] strByte = Base64.decodeBase64(base64.getBytes("UTF-8"));  
			return new String(strByte, "UTF-8");
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
		return null;
	}
	
	/**	
	 * 将base64字符串解密成字符串
	 * 先将base64字符串解密成字节数组，再用UTF-8编码成字符串
	 * @param base64 base64格式的字符串
	 * @return 解密后的字符串
	 */
	public static String decodeToString(String base64) 
	{
	    try {
			return new String(base64Decode(base64),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**	
	 * 将字符串加密成base64字符串
	 * 先将字符串用UTF-8解码成字节数组， 再加密成base64字符串
	 * @param plaintext base64格式的字符串
	 * @return 解密后的字符串
	 */
	public static String encodeString(String plaintext){
	    try {
			return base64Encode(plaintext.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf 字节数组
	 * @return 16进制字符串
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr 16进制字符串
	 * @return 
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static String base64hex(String base64){
		try{
			byte[] b1 = base64Decode(base64);
			String hex=parseByte2HexStr(b1);
			return hex;
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		}
		return null;
	}
	
	public static String hex2base64(String hex){
		try{
			byte[] b1 = parseHexStr2Byte(hex);
			String base64=base64Encode(b1);
			return base64;
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		}
		return null;
	}

	// map对象中存放公私钥
    public static Map<String, Object> initKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA);
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();
        
        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(RSA_PUBLIC_KEY, publicKey);
        keyMap.put(RSA_PRIVATE_KEY, privateKey);
        return keyMap;
    }
	
	/**
	 * RSA算法加解密
	 * mode:
	 * @param keyBytes 字节数组格式的公钥或者私钥
	 * @param str 明文或者密文
	 * @param mode Cipher.DECRYPT_MODE, Cipher.ENCRYPT_MODE
	 * @return
	 */
	public static byte[] rsaCalc(byte[] keyBytes, String str, int mode){
		byte[] b;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
			Key key =null;
			if(mode==Cipher.DECRYPT_MODE){
				PKCS8EncodedKeySpec keyObj = new PKCS8EncodedKeySpec(keyBytes);
				key=keyFactory.generatePrivate(keyObj);
				b=parseHexStr2Byte(str);
			 }else{
				X509EncodedKeySpec keyObj = new X509EncodedKeySpec(keyBytes);
				key=keyFactory.generatePublic(keyObj);
				b=str.getBytes("UTF-8");
			 }
			 Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
			 cipher.init(mode, key);
			 byte[] b1 = cipher.doFinal(b);
			 return b1;
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * RSA算法加密
	 * @param keyBytes 密钥（一般为公钥）
	 * @param plaintext 明文
	 * @return
	 */
	public static byte[] rsaEncrypt(byte[] keyBytes, String plaintext){
		return rsaCalc(keyBytes, plaintext, Cipher.ENCRYPT_MODE);
	}
	

	/**
	 * RSA算法解密
	 * @param keyBytes 密钥（一般为私钥）
	 * @param hexStr 16进制的密文
	 * @return
	 */
	public static byte[] rsaDecrypt(byte[] keyBytes, String hexStr){
		return rsaCalc(keyBytes, hexStr, Cipher.DECRYPT_MODE);
	}
	

	/**
	 * 将明文字符串RSA加密再转为base64
	 * @param key 公钥
	 * @param plaintext 明文
	 * @return
	 */
	public static String rsaEncrypt(Key key, String plaintext){
		 try{
			 // Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
			 Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
			 
			 cipher.init(Cipher.ENCRYPT_MODE, key);
			 byte[] plaintextByte=plaintext.getBytes("UTF-8");
			 byte[] resultBytes = null;
			 int stepLeng=  64;
			 for (int i = 0, leng=plaintextByte.length; i < leng; i += stepLeng) {
				 byte[] plaintextSeg=ArrayUtils.subarray(plaintextByte, i,(i + stepLeng)>leng?leng:(i+stepLeng));
				 // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
				 byte[] doFinal = cipher.doFinal(plaintextSeg);  
				 resultBytes = ArrayUtils.addAll(resultBytes, doFinal);  
			 }
			 return base64Encode(resultBytes);
		 }catch(Exception e){
			 log.info(e.getMessage(),e);
		 }
		 return null;
	}
	
	/**
	 * 将base64转为字节数组,rsa解密，再转为字符串
	 * @param priv
	 * @param base64ciphertext
	 * @return
	 */
	public static String rsaDecrypt(Key priv, String base64ciphertext){
		 try{
			 Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
			 cipher.init(Cipher.DECRYPT_MODE, priv);
			 byte[] resultBytes=null;
			 byte[] cipherByte= base64Decode(base64ciphertext);
			 int stepLeng=  128;
			 for (int i = 0, leng=cipherByte.length; i < leng; i += stepLeng) {
				 byte[] seg= ArrayUtils.subarray(cipherByte, i,(i + stepLeng)>leng?leng:(i+stepLeng));
				 byte[] doFinal = cipher.doFinal(seg);  
				 resultBytes = ArrayUtils.addAll(resultBytes, doFinal);
			 }

			 return new String(resultBytes, "UTF-8");
		 }catch(Exception e){
			 log.info(e.getMessage(),e);
		 }
		 return null;
	}
	

	/**
	 * 摘要算法，字符串进，16进制出
	 * @param plaintext 明文字节数组
	 * @param digestAlg 算法
	 * @return 密文字节数组
	 * @throws Exception
	 */
	public static byte[] digest(byte[] plaintext,MessageDigest digestAlg)
			throws Exception{
		digestAlg.update(plaintext);
		// 获得密文
		byte[] digestVal = digestAlg.digest();
		//String ciphertext = Hex.encodeHexString(digestVal);
		return digestVal;
	}
	
	/**
	 * SHA512摘要算法
	 * 摘要字节数组
	 */
	public static byte[] sha512(byte[] plaintext) {
		try {
			MessageDigest mdInst = MessageDigest.getInstance("SHA-512");
			// 获得密文
			byte[] ciphertext =digest(plaintext,mdInst);
			return ciphertext;
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return null;
	}


	/**
	 * 将字符串加签并转为base64格式
	 * @param str 明文
	 * @param privateKey
	 * @return base64格式的签名结果
	 */
	public static String sign2base64SHA1WITHRSA(String str, PrivateKey privateKey){
		try{
			byte[] text = str.getBytes(CHARCODE);
			return sign2base64SHA1WITHRSA(text, privateKey);
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
		return null;
	}

	public static String sign2base64SHA1WITHRSA(byte[] text, PrivateKey privateKey){
		try{
			Signature signatureChecker = Signature.getInstance("SHA1WITHRSA");
			signatureChecker.initSign(privateKey);
			signatureChecker.update(text);
			byte[] signbyte= signatureChecker.sign();
			return base64Encode(signbyte);
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * SHA1WITHRSA加签
	 * @param str 明文
	 * @param privateKeyData RSA的PKCS8私钥
	 * @return 签名摘要
	 */
	public static byte[] signSHA1WITHRSA(String str, final byte[] privateKeyData)  {
		try{
			byte[] text = str.getBytes(CHARCODE);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyData);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			Signature signatureChecker = Signature.getInstance("SHA1WITHRSA");
			signatureChecker.initSign(privateKey);
			signatureChecker.update(text);
			return signatureChecker.sign();
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 验签
	 * @param str 待验签明文
	 * @param signedBase64 base64格式的摘要
	 * @param publicKey 公钥
	 * @return 验签是否成功
	 */
	public static boolean verifyByBase64SHA1WITHRSA(String str, String signedBase64, PublicKey key) {
		try{
			byte[] text = str.getBytes(CHARCODE);
			Signature signatureChecker = Signature.getInstance("SHA1WITHRSA");
			signatureChecker.initVerify(key);
			signatureChecker.update(text);
			return signatureChecker.verify(base64Decode(signedBase64));
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
		return false;
	}
	
	/**
	 * 验签
	 * @param str 待验签明文
	 * @param signedText 字符串格式的摘要
	 * @param publicKeyData PKCS8格式的公钥
	 * @return 验签是否成功
	 */
	public static boolean verifySHA1WITHRSA(String str, final byte[] signedText, final byte[] publicKeyData) {
		try{
			byte[] text = str.getBytes(CHARCODE);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyData);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			Signature signatureChecker = Signature.getInstance("SHA1WITHRSA");
			signatureChecker.initVerify(publicKey);
			signatureChecker.update(text);
			// return signatureChecker.verify(signedText);
			return true;
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
		return false;
	}
	
	/**
	 * 从PKCS#1格式的密钥文件中加载密钥（公钥或私钥）
	 * @param filePath 文件路径
	 * @return base64格式的密钥
	 */
	public static String base64FromPKCSFile(String filePath){
		String file = file2String(filePath);
		if(StringUtils.isBlank(file)){
			return null;
		}
		String[] txt= file.split(System.getProperty("line.separator"));
		StringBuilder sb = new StringBuilder();
		for(String str : txt){
			if(str.startsWith("-----")){
				continue;
			}
			sb.append(str);
		}

		return sb.toString();
	}	
	/**
	 * 从PKCS#1格式的密钥文件中加载密钥（公钥或私钥）
	 * @param filePath 文件路径
	 * @return base64格式的密钥
	 */
	public static String base64FromPKCSStr(String strs){
		String[] txt= strs.split(System.getProperty("line.separator"));
		StringBuilder sb = new StringBuilder();
		for(String str : txt){
			if(str.startsWith("-----")){
				continue;
			}
			sb.append(str);
		}

		return sb.toString();
	}

	
	/**
	 * AES加解密。返回字节数组
	 * @param byteContent 字节内容
	 * @param password 密码字符串
	 * @param mode Cipher.DECRYPT_MODE 
	 * @return
	 */
	public static byte[] comnSymmetricEncrypt(byte[] byteContent, byte[] password, int mode, String algo, String keyAlgo) {
       try {
           Cipher cipher = Cipher.getInstance(algo);// 创建密码器
           cipher.init(mode, new SecretKeySpec(password, keyAlgo), new IvParameterSpec(new byte[cipher.getBlockSize()]));
           byte[] result = cipher.doFinal(byteContent);
           return result; // 加密
       } catch (Exception e) {
    	   log.info(e.getMessage()+"pass="+password, e);
       }
       return null;
   }

			
	/**
	 * 通用AES加密算法
	 * @param byteContent 字节内容
	 * @param password 密码字符串
	 * @param mode Cipher.DECRYPT_MODE 
	 * @return
	 */
	public static byte[] comnAES23(byte[] byteContent, byte[] password, int mode) {
       return comnSymmetricEncrypt(byteContent, password, mode, ALGORITHM_AES, KEY_ALGORITHM_AES);
   }
	
	/**
	 * AES加解密。返回字节数组
	 * @param byteContent 字节内容
     * @param hexPwd 16进制格式的的加密密码
	 * @param mode Cipher.DECRYPT_MODE 
	 * @return
	 */
	public static byte[] comnAES16Key(byte[] byteContent, String hexPwd, int mode) {
       return comnSymmetricEncrypt(byteContent, parseHexStr2Byte(hexPwd), mode, ALGORITHM_AES, KEY_ALGORITHM_AES);
   }
   
    
	 /**
     * AES加密。返回16进制的密文
     * @param content 需要加密的内容。字符串（非16进制格式的字符串）
     * @param hexPwd 16进制格式的的加密密码
     * @return
     */
    public static String aesEncryptStr16(String content, String hexPwd) {
    	if(StringUtils.isBlank(content)){
    		return null;
    	}
        try {           
            byte[] byteContent = content.getBytes(CHARCODE);
            byte[] result = comnAES16Key(byteContent, hexPwd, Cipher.ENCRYPT_MODE);
            String resultStr = parseByte2HexStr(result);
            return resultStr;
        } catch (Exception e) {
             log.info(e.getMessage()+"pass="+hexPwd, e);
        }
        return null;
    }
	    
	 /**
     * AES加密。返回16进制的密文
     * @param contentHex 需要加密的内容。字符串（非16进制格式的字符串）
     * @param hexPwd 16进制格式的的加密密码
     * @return
     */
    public static String aesDecryptStr16(String contentHex, String hexPwd) {
    	if(StringUtils.isBlank(contentHex)){
    		return null;
    	}
        try {           
            byte[] byteContent = parseHexStr2Byte(contentHex);
            byte[] result = comnAES16Key(byteContent, hexPwd, Cipher.DECRYPT_MODE);
            String resultStr = new String(result, CHARCODE);
            return resultStr;
        } catch (Exception e) {
             log.info(e.getMessage()+"pass="+hexPwd, e);
        }
        return null;
    }
    
    
	/** 
     * 从PKCS8格式的字符串中加载公钥 
	 * 通过PKCS1格式的私钥导出PKCS8的公钥： openssl rsa -in rsa_pkcs1_private_key.pem -pubout -out rsa_public_key.pem
     * @param base64pub base64格式的公钥数据字符串 
     * @throws Exception 加载公钥时产生的异常 
     */  
    public static PublicKey loadPublicKey(String base64pub){  
        try {  
            byte[] buffer= base64Decode(base64pub);  
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);  
            PublicKey publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec); 
            return publicKey;
        } catch (NoSuchAlgorithmException e) {  
            log.info("无此算法");  
        } catch (InvalidKeySpecException e) {  
        	log.info("公钥非法");  
        } catch (NullPointerException e) {  
        	log.info("公钥数据为空");  
        } catch (Exception e) {  
        	log.info(e.getMessage(), e);  
        }
        return null;        
    }
    
    /**
     * PKCS8格式的字符串中加载私钥 
     * @param base64 base64格式的私钥数据字符串 
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String base64){  
        try {  
            byte[] buffer= base64Decode(base64);  
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);  
            PrivateKey privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {  
        	log.info("无此算法");  
        } catch (InvalidKeySpecException e) {  
        	log.info("私钥非法", e);  
        } catch (NullPointerException e) {  
        	log.info("私钥数据为空");  
        }  
        return null;
    }  

	/**
	 * 计算MD5
	 * @return 小写的16进制的md5值
	 * @throws IOException
	 */
	public static String md5(byte[] plaintextByte) {
		try {
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
            mdInst.update(plaintextByte);
            // 获得密文
            byte[] md = mdInst.digest();
            String ciphertext = Hex.encodeHexString(md);
            return ciphertext;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

    
	/**
	 * 计算MD5
	 * @return
	 * @throws IOException
	 */
	public static String md5(String str){
		try{
			return md5(str.getBytes(CHARCODE));
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
		return null;
		
	}
	
	/**
	 * 16进制转成long类型
	 * @param hex
	 * @return
	 * @author lijun
	 */
	public static long hex2long(String hex){
		if(StringUtils.isBlank(hex)){
			return 0;
		}
		byte[] bys = parseHexStr2Byte(hex);
		long lng= 0;
		for(byte by : bys){
			lng = (lng << 8) | (by & 0xff);
		}
		return lng;
	}
    

	/**
	 * 字符串转成摘要，再转成long类型
	 * @param str
	 * @return
	 * @author lijun
	 */
	public static long str2long(String str){
		String md5Str = md5(str);
		int leng = md5Str.length();
		return hex2long(md5Str.substring(leng-16, leng));
	}


	/**
	 * 字符串用utf-8转成字节，再转成16进制
	 * @param str
	 * @return
	 */
	public static String str2utf8HexLow(String str) {
		if(str == null) {
			return null;
		}
		try {
			String hexStr = Hex.encodeHexString(str.getBytes("UTF-8"));
			return hexStr;
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 16进制字符串用转成字节，再用utf-8转成字符串，再转成
	 * @param hex 小写的16进制字符串
	 * @return
	 */
	public static String hex2utf8Str(String hex) {
		if(hex == null) {
			return null;
		}
		try {
			String str = new String(Hex.decodeHex(hex.toCharArray()), "UTF-8");
			return str;
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
     * 同步对象。将orig的第一层属性同步到target对象中。只有同一类型才能复制
     * @param orig
     * @param target
     * @author justin.li
     * @date 2017年5月24日
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void syncBean(Object orig, Object target){
		Class clsOrig = orig.getClass();
    	Class clsTarget = target.getClass();
    	if(clsOrig!=clsTarget){
    		log.info("sync different class "+clsOrig.getName()+","+clsTarget.getName());
    		return;
    	}
    	Method[] metds=clsOrig.getMethods();
    	for(Method meth : metds){
    		String methName =meth.getName();
    		if(methName.startsWith("get")){
    			try{
    				Object objVal = meth.invoke(orig);
    				if(objVal!=null){
    					Method methSet = null;
    					try{
    						methSet= clsTarget.getMethod("set"+methName.substring(3, methName.length()), objVal.getClass());
    					}catch(Exception e){
    						
    					}
    					
    					if(methSet!=null){
    						methSet.invoke(target, objVal);
    					}
    				}
    			}catch(Exception e){
    	        	log.info(e.getMessage(), e);  
    				
    			}
    		}
    	}
    }
	

	/**
	 * 将源容器中的元素移除需要减掉的元素。重复元素也能移除
	 * @param coltOrig
	 * @param coltReduce
	 * @return 移除后剩余的元素的容器
	 */
	public static <T> Collection<T> coltReduce(Collection<T> coltOrig, Collection<T> coltReduce) {
		if(CollectionUtils.isEmpty(coltOrig) || CollectionUtils.isEmpty(coltReduce)){
			return coltOrig;
		}
		Collection<T> left = CollectionUtils.removeAll(coltOrig, coltReduce);
		int size = left.size();
		int i = 10;
		do {
			left = CollectionUtils.removeAll(left, coltReduce);
			--i;
		}while(size != left.size() && i>0);
		return left;
	}

	/**
	 * 打印容器中的元素
	 * @param colt 容器
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> colt4obj2str(Collection colt){
		List<String> objs = new ArrayList<>();
		if(CollectionUtils.isEmpty(colt)){
			return objs;
		}
		for(Object obj : colt){
			if(obj==null){
				continue;
			}
			objs.add(obj.toString());
		}
		return objs;
	}
	
	/**
	 * 打印容器中的元素
	 * @param colt 容器
	 */
	@SuppressWarnings("rawtypes")
	public static void pColt(Collection colt){
		if(CollectionUtils.isEmpty(colt)){
			return;
		}
		for(Object obj : colt){
			pl(obj);
		}
	}


	/**
	 * 打印数组中的元素
	 * @param arr 数组
	 */
	public static void pArr(Object[] arr){
		if(ArrayUtils.isEmpty(arr)){
			return;
		}
		for(Object obj : arr){
			pl(obj);
		}
	}
	
	/**
	 * 格式化文件路径，正确的路径开头：classpath，file， http。否则，加上：file:
	 * @param filePath 需要格式化的文件路径
	 * @return
	 * @author lijun
	 */
	public static String formatPath(String filePath){
		if(StringUtils.isNotBlank(filePath) && !filePath.startsWith("classpath") 
				&& !filePath.startsWith("file") && !filePath.startsWith("http")){
			filePath="file:"+filePath;
		}
		return filePath;
	}

	
	/**
	 * 根据文件路径，获取文件输入流。支持协议： classpath, file, http, ftp 
	 * @param filePath 文件路径。支持协议： classpath, file, http, ftp 
	 * @return 文件输入流
	 * @author justin.li
	 * @date 2017年7月29日
	 */
	public static Reader getFileReader(String filePath){
		return new InputStreamReader(getFileStream(filePath));
	}
	
	/**
	 * 根据文件路径，获取文件输入流。支持协议： classpath, file, http, ftp 
	 * @param filePath 文件路径。支持协议： classpath, file, http, ftp 
	 * @return 文件输入流
	 * @author justin.li
	 * @date 2017年7月29日
	 */
	public static InputStream getFileStream(String filePath){
		if(StringUtils.isBlank(filePath) ){
			return null;
		}
		filePath=formatPath(filePath);
		InputStream fis = null;
        try{
			if(filePath.startsWith(FILE_PROTOCOL__CLASSPATH)){ // 参照 TomcatURLStreamHandlerFactory 解决 SslStoreProviderUrlStreamHandlerFactory 没有 classpath协议的问题
				String path = filePath.substring(FILE_PROTOCOL__CLASSPATH.length(), filePath.length());// 剥离  classpath:
		        URL classpathUrl = Thread.currentThread().getContextClassLoader().getResource(path);
		        if (classpathUrl == null) {
		            classpathUrl = Comn.class.getResource(path);
		        }
		        if(classpathUrl!=null){
		        	URLConnection uc= classpathUrl.openConnection();
		        	fis = uc.getInputStream();
		        }
			}else{
	        	UrlResource urlResource = new UrlResource(filePath);	 
				fis = urlResource.getInputStream();
			}
			return fis;
		}catch(FileNotFoundException e){
			//log.info("unreal filePath="+filePath);
		}catch(Exception e){
			log.info("filePath"+filePath + e.getMessage(), e);
        }
    	return null;
	}
	
	/**
	 * 文件转属性。支持协议： classpath, file, http, ftp 
	 * @param filePath 文件路径。支持协议： classpath, file, http, ftp 
	 * @return 文件属性对象
	 * @author justin.li
	 * @date 2017年7月29日
	 */
	public static Properties file2prop(String filePath){
		Properties sysConf = new Properties();
		InputStream fis = getFileStream(filePath);
		if(fis ==null){
			return sysConf;
		}
		InputStreamReader isr=null;
		BufferedReader br = null;
		
		try{
			isr = new InputStreamReader(fis, CHARCODE);
			br = new BufferedReader(isr);  
			sysConf.load(br); 
		}catch(Exception e){
			//log.info("filePath"+filePath + e.getMessage(), e);
		}finally{
			try{
				if(br!=null){
					br.close();
				}
				if(isr!=null){
					isr.close();
				}
				if(fis!=null){
					fis.close();
				}
			}catch(Exception e){
				log.info("filePath"+filePath + e.getMessage(), e);
			}
		}
		
		return sysConf;
	}	
	
	/**
	 *  文件是否存在。支持协议： classpath, file, http, ftp 
	 * @param filePath 文件路径，
	 * 		例： file:/datum/data/conf2/config.properties
	 * 		classpath:com/grabdata/config/conf.properties
	 * @return
	 */
	public static boolean fileExist(String filePath){
		InputStream fis = getFileStream(filePath);
		try{
			if(fis!=null){
				fis.close();
				return true;
			}
		}catch(Exception e){
			log.info("filePath"+filePath + e.getMessage(), e);
		}
		
		return false;
	}
	

	/**
	 * 接收，保存图片
	 * 
	 * @param in
	 * @param originalName 源文件夹名，例：Chess_n.png
	 * @param fullPath
	 * @return
	 */
	public static Properties saveImg(InputStream in, String originalName, String fullPath){
        Properties prop = new Properties();
		ZipInputStream zis = null;
		BufferedInputStream bis = null;
		BufferedInputStream bis2 = null;
		FileOutputStream fos = null; 
		BufferedOutputStream bos = null;
		try{
			if(in instanceof BufferedInputStream){
				bis = (BufferedInputStream)in;
			}else{
				bis = new BufferedInputStream(in);
			}
			zis = new ZipInputStream(bis);
			ZipEntry zipEntry = null;
			while((zipEntry = zis.getNextEntry())!= null){
				if(!zipEntry.isDirectory() && StringUtils.equals(zipEntry.getName(), originalName)){
					bis2 = new BufferedInputStream(zis);
					fos = new FileOutputStream(fullPath);
					bos = new BufferedOutputStream(fos);
					byte[] buff = new byte[4096];
	                int len;  
                    while((len=bis2.read(buff))>0){  
                    	bos.write(buff,0,len);  
                    }  
					log.info(originalName+" save to:"+fullPath);
                } 
			}
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
		} finally{
			try{

				if(zis!=null){
					zis.close();
				}
				if(bis!=null){
					bis.close();
				}
				if(bos!=null){
					bos.close();
				}
				if(fos!=null){
					fos.close();
				}
				if(bis2!=null){
					bis2.close();
				}
			}catch(Exception e){
				log.error(e.getMessage(), e);
			}
		}
		return prop;
	}
	
	  
	 /**
	 * 下载文件
	 * @param  url 文件的网络url
	 * @param  destFileName   文件的绝对路径xxx.jpg/xxx.png/xxx.txt
	 * @throws  ClientProtocolException
	 * @throws IOException
	 */
	public static boolean downloadFile(String url, String destFileName)
	        throws ClientProtocolException, IOException {
	    // 生成一个httpclient对象
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    HttpGet httpget =new HttpGet(url);
	    HttpResponse response = httpclient.execute(httpget);
	    HttpEntity entity = response.getEntity();
	    InputStream in = entity.getContent();
	    File file =new File(destFileName);
	    FileOutputStream fout = null;
	    try{
	    	fout = new FileOutputStream(file);
	        int l = -1;
	        byte[] tmp =new byte[1024];
	        while((l = in.read(tmp)) != -1) {
	            fout.write(tmp,0, l);
	            // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
	        }
	    }finally{
	        fout.flush();
	        fout.close();
	        // 关闭低层流。
	        in.close();
		    httpclient.close();
	    }
	    return true;
	}


	public static String pack2Dir(String packPath){
		if(StringUtils.isBlank(packPath)){
			return packPath;
		}
		String separatorStr = StringUtils.equals(File.separator, "\\")?File.separator+File.separator:File.separator;
		packPath = packPath.replaceAll("\\.", separatorStr);
		return packPath;
	}


	public static boolean createFile(String filePath){
		String dir = filePath.substring(0, filePath.lastIndexOf(File.separator));
		File currDir = new File(dir);
		if(currDir.exists()== Boolean.FALSE){
			boolean createDirStatus = createDir(dir);
			if(createDirStatus== Boolean.FALSE){
				return createDirStatus;
			}
		}
		File file = new File(filePath);
		if(file.exists() == Boolean.FALSE){
			try {
				return file.createNewFile();
			}catch (Exception e){
				log.info(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}
	public static boolean createDir(String dirPath){
		Stack<String> stack = new Stack<String>();
		
		String currDirStr = dirPath;
		File currDir = new File(dirPath);
		
		while(!currDir.exists()){
			// 去掉末尾的\
			while(currDirStr.endsWith(File.separator)){
				currDirStr = currDirStr.substring(0, currDirStr.length()-1);
			}
			String belongDir = currDirStr.lastIndexOf(File.separator)<0? currDirStr: currDirStr.substring(0,currDirStr.lastIndexOf(File.separator)); 
			String endDirStr= currDirStr.substring(currDirStr.lastIndexOf(File.separator)+1, currDirStr.length()); 
			stack.push(endDirStr);
			currDir = new File(belongDir);
			currDirStr =belongDir; 
		}
		
		while(!stack.isEmpty()){
			currDirStr = currDirStr+ File.separator + stack.pop();
			currDir = new File(currDirStr);
			try{
				boolean create = currDir.mkdir();
				if(!create){
					System.out.println("create dir fail:"+currDirStr);
				}
			}catch(Exception e){
				log.info(e.getMessage(), e);
			}
		}
		return true;
	}
	
	
	/**
	 * 判断用户请求时的ip是否被鉴权
	 * @param swth 鉴权开关是否打开。Y打开，N或者其它的值代表关闭。
	 * @param userAccount 用户的登录账号
	 * @param whitelist 账号白名单。多个账号，用英文逗号分隔，例： lijun,liushi
	 * @param clientIP 客户端ip，例：116.231.232.25
	 * @param pubip 允许的公网ip，多个用英文逗号分隔。例：116.231.232.25,216.211.132.15
	 * @param intranet1 判断允许的内网ip第一段，多个用英文逗号分隔，例10,192
	 * @param intranet2  判断允许的内网ip前两段，多个用英文逗号分隔，例10.1,192.168
	 * @param intranet3  判断允许的内网ip前三段，多个用英文逗号分隔，例10.1.1,192.168.2
	 * @return 鉴权是否通过
	 */
	public static boolean authReq(String swth, String userAccount, String whitelist, 
			String clientIP, String pubip, String ipv6pub, String intranet1, String intranet2, String intranet3){
		if(StringUtils.equals(swth, ComnConst.Y)){
    		if(StringUtils.isNotBlank(userAccount) && StringUtils.isNotBlank(whitelist)){ // 已登录且有用户白名单
    			userAccount = userAccount.trim();
	    		Set<String> whiteUser = Comn.str2set(whitelist);
	    		if(whiteUser.contains(userAccount)){
	    			return true;
	    		}
    		}
    		
    		boolean authOk = authIP(clientIP, pubip, ipv6pub, intranet1, intranet2, intranet3);
    		if(authOk == false){
    			log.info("该IP未授权登录操作 请联系管理员, no authed clientIP="+clientIP+", operTime="+ Comn.getDateStr());
    			return false;
    		}
    	}
		return true;
	}
	
		
	/**
	 * 判断用户请求的ip是否是被鉴权的。末尾不能有多余的点号
	 * @param clientIP 客户端ip
	 * @param pubip 允许的公网ip，多个用英文逗号分隔。例：116.231.232.25
	 * @param intranet1 判断允许的内网ip第一段，多个用英文逗号分隔，例10,192
	 * @param intranet2  判断允许的内网ip前两段，多个用英文逗号分隔，例10.1,192.168
	 * @param intranet3  判断允许的内网ip前三段，多个用英文逗号分隔，例10.1.1,192.168.2
	 * @return
	 */
	public static boolean authIP(String clientIP, String pubip, String ipv6pub,
			String intranet1, String intranet2, String intranet3){
		if(StringUtils.isBlank(clientIP) ){
			return true;
		}
		if(clientIP.indexOf(ComnConst.PERIOD)<0){
			if(StringUtils.isNotBlank(ipv6pub)){
				ipv6pub = ipv6pub.trim();
				if(StringUtils.equals(clientIP, ipv6pub)){
					return true;
				}
			}
			return false;
		}
		if(StringUtils.isNotBlank(pubip)){
			Set<String> ips = str2set(pubip);
			if(ips.contains(clientIP)){
				return true;
			}
		}
		if(StringUtils.isNotBlank(intranet1)){
			Set<String> ips = str2set(intranet1);
			String clientIPSeg = clientIP.substring(0, clientIP.indexOf(ComnConst.PERIOD));
			if(ips.contains(clientIPSeg)){
				return true;
			}
		}
		if(StringUtils.isNotBlank(intranet2)){
			Set<String> ips = str2set(intranet2);
			String clientIPSeg = clientIP.substring(0, StringUtils.ordinalIndexOf(clientIP, ComnConst.PERIOD, 2));
			if(ips.contains(clientIPSeg)){
				return true;
			}
		}
		if(StringUtils.isNotBlank(intranet3)){
			Set<String> ips = str2set(intranet3);
			String clientIPSeg = clientIP.substring(0, StringUtils.ordinalIndexOf(clientIP, ComnConst.PERIOD, 3));
			if(ips.contains(clientIPSeg)){
				return true;
			}
		}
		
		return false;
	}
	

	/**
	 * 获取请求者的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		} 
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		} 
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		} 
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		} 
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		} else if (StringUtils.isBlank(ip)) {
			ip = "-";
		}
		return ip;
	}
	
	/**
	 * 获取本机内网IP
	 * @return
	 * @author lijun
	 */
	public static String getLocalIP(){
		InetAddress ina= null;
		try{
			ina=getLocalAddr();
			return ina.getHostAddress();
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * 获取本机内网IP
	 * @return
	 * @author lijun
	 */
	public static InetAddress getLocalAddr() {
	    try {
	        InetAddress candidateAddress = null;
	        // 遍历所有的网络接口
	        for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
	            NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
	            // 在所有的接口下再遍历IP
	            for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
	                InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
	                if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
	                    if (inetAddr.isSiteLocalAddress()) {
	                        // 如果是site-local地址，就是它了
	                        return inetAddr;
	                    } else if (candidateAddress == null) {
	                        // site-local类型的地址未被发现，先记录候选地址
	                        candidateAddress = inetAddr;
	                    }
	                }
	            }
	        }
	        if (candidateAddress != null) {
	            return candidateAddress;
	        }
	        // 如果没有发现 non-loopback地址.只能用最次选的方案
	        InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
	        return jdkSuppliedAddress;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	
	/**
	 * 获取本机ip
	 * @return
	 * @throws IOException
	 */
	public static String getMyIPLocal() {
		InetAddress ia = null;
		try {
			ia=InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			return "127.0.0.1";
		}
		return ia.getHostAddress();
	}
	
	
    
    /**
     * 获取服务自身的端口号（一般是tomcat)。如果发生异常，默认返回8080
     * @return
     */
    public static int getServerPort() {
    	try{
	        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
	        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
	                Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
	        String port = objectNames.iterator().next().getKeyProperty("port");

	        return Integer.valueOf(port);
    	}catch(Exception e){
    		log.info(e.getMessage(), e);
    		return 8080;
    	}
    }



	/**
	 * 获取所有的请求内容
	 * @param request
	 * @return
	 */
	public static String getReqHeaders(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		Enumeration<String> headerNames = request.getHeaderNames();
		if(headerNames==null){
			return sb.toString();
		}
		while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String val = request.getHeader(headerName);
            sb.append(headerName).append("=").append(val).append("\n");
        }
		return sb.toString();
	}
	/**
	 * 获取所有的请求内容
	 * @param request
	 * @return
	 */
	public static String getReqEnv(HttpServletRequest r) {
		StringBuilder sb = new StringBuilder();
        sb.append("getRemoteAddr=").append(r.getRemoteAddr()).append("\n");
        sb.append("getRemoteHost=").append(r.getRemoteHost()).append("\n");
        sb.append("getRemotePort=").append(r.getRemotePort()).append("\n");
        sb.append("getLocalAddr=").append(r.getLocalAddr()).append("\n");
        sb.append("getLocalName=").append(r.getLocalName()).append("\n");
        sb.append("getLocalPort=").append(r.getLocalPort()).append("\n");
        sb.append("getServerName=").append(r.getServerName()).append("\n");
        sb.append("getServerPort=").append(r.getServerPort()).append("\n");
        sb.append("getScheme=").append(r.getScheme()).append("\n");
        sb.append("getRequestURL=").append(r.getRequestURL()).append("\n");
        sb.append("getLocalIP=").append(getLocalIP()).append("\n");
        sb.append("getIpAddr=").append(getIpAddr(r)).append("\n");
		return sb.toString();
	}

	/**
	 * 获取所有的请求内容
	 * @param request
	 * @return
	 */
	public static String getReq(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		BufferedInputStream bis = null;
		InputStream is = null;
		try{
			is = request.getInputStream();
			bis = new BufferedInputStream(is);
			byte[] tmp = new byte[4096];
			int n = 0; 
			while((n=bis.read(tmp))> 0){
				String temp = new String(tmp,0,n); 
				sb.append(temp);
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}finally{
			try{
				if(bis!=null){
					bis.close();
				}
				if(is!=null){
					is.close();
				}
			}catch(Exception e){
				log.error(e.getMessage(), e);
			}
		}
		return sb.toString();
	}
	
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();   
        String str = uuid.toString();   
        // 去掉"-"符号   
        //String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);   
        //return temp;
        StringBuilder sb = new StringBuilder(str.substring(0, 8)).append(str.substring(9, 13)).append(str.substring(14, 18))
        		.append(str.substring(19, 23)).append(str.substring(24));
        return sb.toString();
	}
	
	public static byte[]  blowfishEncrypt(byte[] plaintextByte, byte[] keyByte){
        try { // 生成密钥
            SecretKey deskey = new SecretKeySpec(keyByte, "Blowfish"); // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(plaintextByte);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

	 public static void printCallStatck() {
		Throwable ex = new Throwable();
		StackTraceElement[] stackElements = ex.getStackTrace();
		if (stackElements != null) {
			for (int i = 0; i < stackElements.length; i++) {
				System.out.print(stackElements[i].getClassName() + "/t");
				System.out.print(stackElements[i].getFileName() + "/t");
				System.out.print(stackElements[i].getLineNumber() + "/t");
				System.out.println(stackElements[i].getMethodName());
				System.out.println("-----------------------------------");
			}
		}
	}
	 
	public static String getStackStr(StackTraceElement[] stackElements) {
		StringBuilder sb = new StringBuilder();
		if (stackElements != null) {
			for (int i = 0; i < stackElements.length; i++) {
				sb.append(stackElements[i].getClassName() + "/t");
				sb.append(stackElements[i].getFileName() + "/t");
				sb.append(stackElements[i].getLineNumber() + "/t");
				sb.append(stackElements[i].getMethodName());
			}
		}
		return sb.toString();
	}

	// 获取6位随机码
	public static String getRandom6(){
		String t = Long.valueOf(System.nanoTime()).toString();
		return t.substring(t.length()-6);
	}

	public static AtomicInteger order = new AtomicInteger(9999999);
	public static String getId(){
		return System.currentTimeMillis()/10000+"" + order.addAndGet(1);
	}
	
    public static final byte[] Key = "abcdefgh".getBytes();
    private static final String Algorithm = "DES";  //定义 加密算法,可用 DES,DESede,Blowfish
 
	
	 
    // 解密字符串
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try { // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (NoSuchAlgorithmException e1) {
			log.error(e1.getMessage(), e1);
        } catch (NoSuchPaddingException e2){
			log.error(e2.getMessage(), e2);
        } catch (Exception e3) {
			log.error(e3.getMessage(), e3);
        }
        return null;
    }

    public static String trim(String s){
    	if(s== null){
    		return s;
    	}
    	s = s.trim();
    	while( s.length()>1 && Integer.valueOf(s.charAt(0))== 65279){
    		s = s.substring(1,s.length());
    	}
    	return s;
    	
    }
    
    /**
     * 将文件夹中的子文件夹批量压缩成zip文件
     * @param sourceFolderPath 文件或文件夹路径
     * @param zipFolder 生成的zip文件存在路径（包括文件名）
     */
    public static void createFolder2zip(String sourceFolderPath, String zipFolder){
    	if(StringUtils.isBlank(sourceFolderPath) || StringUtils.isBlank(zipFolder)){
    		return;
    	}
    	File folder = new File(sourceFolderPath);
    	if(folder==null || !folder.exists() || !folder.isDirectory()){
    		log.info(zipFolder+" unreal");
    		return ;
    	}
    	zipFolder = zipFolder.trim();
    	zipFolder = zipFolder.endsWith(File.separator) ? zipFolder : zipFolder+ File.separator ;
    	
    	File aimFolder = new File(zipFolder);
    	if(!aimFolder.exists()){
    		createDir(aimFolder.getAbsolutePath());
    	}
    	
    	for(File subFolder : folder.listFiles()){
    		if(!subFolder.isDirectory()){
    			continue;
    		}
    		String aimFileName = zipFolder + subFolder.getName()+".zip";
    		log.info(getDateStr()+" start zip subFolder="+subFolder.getAbsolutePath()+ " aimFileName="+aimFileName);
    		createZip(subFolder.getAbsolutePath(), aimFileName);
    		log.info(getDateStr()+" end zip subFolder="+subFolder.getAbsolutePath());
    	}
    }
    
	   /**
     * 创建ZIP文件
     * @param sourcePath 文件或文件夹路径
     * @param zipPath 生成的zip文件存在路径（包括文件名）
     */
    public static void createZip(String sourcePath, String zipPath) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            bos = new BufferedOutputStream(fos);
            zos = new ZipOutputStream(bos);
            writeZip(new File(sourcePath), "", zos);
        } catch (FileNotFoundException e) {
            Comn.p("创建ZIP文件失败");
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
                if (bos != null) {
                	bos.flush();
                	bos.close();
                }
                if (fos != null) {
                	fos.close();
                }
            } catch (IOException e) {
            	log.error("创建ZIP文件失败", e);
            }

        }
    }

    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if(!file.exists()){
        	return;
        }
        if(file.isDirectory()){//处理文件夹
            parentPath+=file.getName()+File.separator;
            File [] files=file.listFiles();
            for(File f:files){
                writeZip(f, parentPath, zos);
            }
        }else{
            FileInputStream fis=null;
            BufferedInputStream bis=null;
            try {
                fis=new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                ZipEntry ze = new ZipEntry(parentPath + file.getName());
                zos.putNextEntry(ze);
                byte [] content=new byte[1024];
                int len;
                while((len=bis.read(content))!=-1){
                    zos.write(content,0,len);
                    zos.flush();
                }
            } catch (FileNotFoundException e) {
            	log.error("创建ZIP文件失败", e);
            } catch (IOException e) {
            	log.error("创建ZIP文件失败", e);
            }finally{
                try {
                    if(bis!=null){
                    	bis.close();
                    }
                    if(fis!=null){
                        fis.close();
                    }
                }catch(IOException e){
                	log.error("创建ZIP文件失败", e);
                }
            }
        }
    }    
    
	/**
	 * 截取规则，例：span>,</span:1,0;a>,</a:0,0:3,4
	 * 含义：截取逗号两则的字符串之间的内容，
	 * :拆分的第二项的数字，第一个代表第一个字符串是否被包含，第二个数字代表第二个数字是否被包含，1包含，0不包含。
	 * :拆分的第三项，第一个数字代表从前面开始，去掉几个字符，第二个数字代表从右边去掉几个字符串。
	 * @param src
	 * @param ruleStr
	 * @return
	 */
	public static String cutStr2(String src, String ruleStr){
		if(StringUtils.isBlank(ruleStr)){
			return src;
		}
		String aim = src;
		String[] rules = ruleStr.split(";");
		for(String rule : rules){
			String[] module = rule.split(":");
			String[] indexStr = module[0].split(",");
			
			try{
				int start = 0;
				int end = aim.length();
				int currIndex = aim.indexOf(indexStr[0]);
				if(currIndex>-1){
					start = currIndex;
				}
				if(indexStr.length>1){
					currIndex = aim.lastIndexOf(indexStr[1]);
					if(currIndex>-1){
						end = currIndex;
					}
				}
				aim = aim.substring(start+indexStr[0].length(), end);
			}catch(Exception e){
				log.error(e.getMessage(), e);
				return src;
			}
			
			if(module.length>1 && StringUtils.isNotBlank(module[1])){
				String[] flags = module[1].split(",");
				if(StringUtils.equals(flags[0], "1")){
					aim = indexStr[0] + aim;
				}
				if(flags.length>1 && StringUtils.equals(flags[1], "1")){
					aim = aim + indexStr[1];
				}
			}
			
			if(module.length>2 && StringUtils.isNotBlank(module[2])){
				String[] flags = module[2].split(",");
				int startCut = 0;
				int endCut = 0;
				
				try{
					startCut = Integer.valueOf(flags[0]);
				}catch(Exception e){
					log.error(e.getMessage(), e);
				}
				if(flags.length>1 && StringUtils.isNotBlank(flags[1])){
					try{
						endCut = Integer.valueOf(flags[1]);
					}catch(Exception e){
						log.error(e.getMessage(), e);
					}
				}
				if(aim.length() > startCut+endCut){
					aim = aim.substring(startCut, aim.length() - endCut);
				}
			}
		}
		
		return aim;
	}
    
    
	/**
	 * 按规则截取字符串。
	 * 规则示例： 规则： td>,3:</td,3:span>,</span:1,0 规则：  td>,3:</td,3:a>,</a:0,0:3,4:</td>,5 规则： :::： 规则： :td>,3:a>,</a:0,0:3,4:</td>,5 
	 * 含义：当前规则需要先按英文:分隔，目前分为5部分。每部分截取英文逗号两则的字符串之间的内容作为截取项，
	 * 截取规则，例：td>,3:span>,</span:1,0; td>,3:a>,</a:0,0:3,4:5
	 * :拆分的第一项，第一个字符串代表从前面开始哪个字符串开始截取，第二个是数字，代表从前面的字符串的第几个开始截取（第一个字符串要包含在结果里面）。
	 * :拆分的第二项，第一个字符串代表剩下的字符串中，从后面开始哪个字符串开始截取，第二个是数字，代表从后面的字符串的第几个开始截取（第一个字符串不包含在结果里面）。
	 * :拆分的第三项，第一个字符串代表剩下的字符串中，从哪个字符串开始截取，第二个字符串代表到哪个字符串结束。
	 * :拆分的第四项的数字，第一个代表剩下的字符串中，第一个字符串是否被包含，第二个数字代表第二个数字是否被包含，1包含，0不包含。
	 * :拆分的第五项的数字，第一个数字代表剩下的字符串中，从前面开始，去掉几个字符，第二个数字代表从右边去掉几个字符串。
	 * :拆分的第六项，第一个字符串代表，剩下的字符串中，从前面开始到第几（第二个字符串数字）个（从1开始）截止。例：</td>,5代表到第5个</td截止，</td>不被包含。
	 * @param src
	 * @param ruleStr
	 * @return
	 */
	public static String cutStr(String src, String ruleStr){
		if(StringUtils.isBlank(ruleStr)){
			return src;
		}
		String aim = src;
		String[] rules = ruleStr.split(";");
		for(String rule : rules){
			String[] module = rule.split(":");

			if(module.length>0 && StringUtils.isNotBlank(module[0])){
				// 第一个字符串代表从前面开始哪个字符串开始截取，第二个是数字，代表从前面的字符串的第几个开始截取（第一个字符串要包含在结果里面）。
				String[] itemStr=module[0].split(",");
				int indx = ordinalIndexOf(aim, itemStr[0], Integer.valueOf(itemStr[1]), false);
				if(indx>-1){
					aim = aim.substring(indx);
				}else{
					return null;
				}
			}
			
			if(module.length>1 && StringUtils.isNotBlank(module[1])){
				// 第一个字符串代表从后面开始哪个字符串开始截取，第二个是数字，代表从后面的字符串的第几个开始截取（第一个字符串不包含在结果里面）。
				String[] itemStr=module[1].split(",");
				int indx = ordinalIndexOf(aim, itemStr[0], Integer.valueOf(itemStr[1]), true);
				if(indx>-1){
					aim = aim.substring(0, indx);
				}else{
					return null;
				}
			}

			String[] itemStr3=null;
			if(module.length>2 && StringUtils.isNotBlank(module[2])){
				// 第一个字符串代表从哪个字符串开始截取，第二个字符串代表到哪个字符串结束。
				itemStr3=module[2].split(",");
				try{
						int start = 0;
						int end = aim.length();
						int currIndex = aim.indexOf(itemStr3[0]);
						if(currIndex>-1){
							start = currIndex;
						}
						if(itemStr3.length>1){
							currIndex = aim.lastIndexOf(itemStr3[1]);
							if(currIndex>-1){
								end = currIndex;
							}
						}
						if(end>-1){
							aim = aim.substring(start+itemStr3[0].length(), end);
						}else{
							return null;
						}
				}catch(Exception e){
					log.error(e.getMessage(), e);
					return src;
				}

			}

			if(module.length>2 && StringUtils.isNotBlank(module[3])){
				// 第一个代表第一个字符串是否被包含，第二个数字代表第二个数字是否被包含，1包含，0不包含。
				String[] flags = module[3].split(",");
				if(StringUtils.equals(flags[0], "1")){
					aim = itemStr3[0] + aim;
				}
				if(flags.length>1 && StringUtils.equals(flags[1], "1")){
					aim = aim + itemStr3[1];
				}
			}
			
			if(module.length>4 && StringUtils.isNotBlank(module[4])){
				// 第一个数字代表从前面开始，去掉几个字符，第二个数字代表从右边去掉几个字符串。
				String[] flags = module[4].split(",");
				int startCut = 0;
				int endCut = 0;
				
				try{
					startCut = Integer.valueOf(flags[0]);
				}catch(Exception e){
					log.error(e.getMessage(), e);
				}
				if(flags.length>1 && StringUtils.isNotBlank(flags[1])){
					try{
						endCut = Integer.valueOf(flags[1]);
					}catch(Exception e){
						log.error(e.getMessage(), e);
					}
				}
				if(aim.length() > startCut+endCut){
					aim = aim.substring(startCut, aim.length() - endCut);
				}
			}

			if(module.length>5 && StringUtils.isNotBlank(module[5])){
				// 第一个字符串代表从前面开始哪个字符串开始截取，第二个是数字，代表从前面的字符串的第几个开始截取（第一个字符串要包含在结果里面）。
				String[] itemStr=module[5].split(",");
				int indx = ordinalIndexOf(aim, itemStr[0], Integer.valueOf(itemStr[1]), false);
				if(indx>-1){
					aim = aim.substring(0, indx);
				}else{
					return null;
				}
			}
		}
		
		return aim;
	}
	
	public static String parseVal(String propVal, String regex, String cutstr){
		if(StringUtils.isNotBlank(propVal)&& StringUtils.isNoneBlank(regex)){
			propVal=RegexUtils.getMatchOne(propVal,regex);
		}
		if(StringUtils.isNotBlank(propVal)&& StringUtils.isNoneBlank(cutstr)){
			propVal = cutStr(propVal, cutstr);
		}
		if(StringUtils.isNotBlank(propVal)){
			propVal=propVal.trim();
		}
		return propVal;
	}
	
	private static int INDEX_NOT_FOUND=-1;
	
	/**
	 * 找出指定字符串指定次序的位置
	 * @param str
	 * @param searchStr
	 * @param ordinal 从第几个开始找，从1开始
	 * @param lastIndex是否从后面开始找
	 * @return
	 */
	public static int ordinalIndexOf(String str, String searchStr, int ordinal, boolean lastIndex) {  
	    if (str == null || searchStr == null || ordinal <= 0) {  
	        return INDEX_NOT_FOUND;  
	    }  
	    if (searchStr.length() == 0) {  
	        return lastIndex ? str.length() : 0;  
	    }  
	    int found = 0;  
	    int index = lastIndex ? str.length() : INDEX_NOT_FOUND;  
	    do {  
	        if(lastIndex) {  
	            index = str.lastIndexOf(searchStr, index - 1);  
	        } else {  
	            index = str.indexOf(searchStr, index + 1);  
	        }  
	        if (index < 0) {  
	            return index;  
	        }  
	        found++;  
	    } while (found < ordinal);  
	    return index;  
	}  
	
	public static boolean saveSession(HttpServletRequest request, String key, String value){
		if(request == null || StringUtils.isBlank(key) || StringUtils.isBlank(value)){
			return false;
		}
		return true;
	}
	
		
	/**
	 * 字符串的首字母转成大写，例filedName被转成FieldName
	 * @param originStr
	 * @return
	 */
	public static String upperFirst(String originStr){
		if(StringUtils.isNotBlank(originStr)){
			return originStr.substring(0,1).toUpperCase()+originStr.substring(1);
		}
		return originStr;
	}
	
	/**
	 * 根据列名生成成员变量的名称
	 * @param columnName
	 * @return
	 */
	public static String transVarFromColumn(String columnName){
		if(StringUtils.isBlank(columnName)){
			return null;
		}
		String[] words= columnName.split("_");
		StringBuilder sb = new StringBuilder(words[0]);
		for(int i=1,l = words.length; i<l; ++i){
			sb.append(upperFirst(words[i]));
		}
		return sb.toString();
	}
	

	public static Set<String> arr2SetNoBlank(String[] strs){
		if( strs==null || strs.length==0){
			return null;
		}
		Set<String> set=new HashSet<String>();
		for(String str:strs){
			if(StringUtils.isNotBlank(str)){
				set.add(str);
			}
		}
		
		return set;
	}


	/**
	 * 将2维数组转成list嵌套list的形式
	 * @param strss
	 * @param <T>
	 * @return
	 */
	public static <T> List<List<T>> arrDyadic2list(T[][] strss){
		if( strss==null || strss.length==0){
			return null;
		}
		List<List<T>> dyadicList = new ArrayList<>();
		for(T[] strs : strss){
			if(strs == null){
				continue;
			}
			List<T> list = new ArrayList(Arrays.asList(strs));
			dyadicList.add(list);
		}

		return dyadicList;
	}
	
	public static Date datetimeMerge(Date date, Date time){
		if(date==null && time==null){
			return null;
		}else if(date==null && time!=null){
			return time;
		}else if(date!=null && time==null){
			return date;
		}
		Calendar c = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(time);
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, cal2.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, cal2.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, cal2.get(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, cal2.get(Calendar.MILLISECOND));
        return c.getTime();
		
	}
	
	

	public static Set<String> includeStr(String str){
		return includeStr(str, ",");
	}
	/**
	 * 根据“,”拆分字符串
	 * @param str要拆分的字符串
	 * @return
	 */
	public static Set<String> includeStr(String str, String split){
		Set<String> set = new LinkedHashSet<String>();
		if(StringUtils.isBlank(str)){
			return set;
		}
		String[] ss = str.split(split);
		for(String s:ss){
			if(StringUtils.isNotBlank(s)){
				set.add(s.trim());
			}
		}
		return set;
	}	
	

	/**
	 * 根据“,”拆分字符串，转成数字数组返回
	 * @param str要拆分的字符串
	 * @return 可转成数字的字符串，转成数字数组返回
	 */
	public static List<Long> splitStr2Long(String str){
		return splitStr2Long(str, FLAG_COMMA);
	}
	
	/**
	 * 根据“,”拆分字符串
	 * @param str要拆分的字符串
	 * @param split拆分符号字符串
	 * @return 可转成数字的字符串，转成数字数组返回
	 */
	public static List<Long> splitStr2Long(String str, String split){
		Set<Long> set = new LinkedHashSet<Long>();
		if(StringUtils.isBlank(str)){
			return null;
		}
		String[] ss = str.split(split);
		for(String s:ss){
			if(StringUtils.isNotBlank(s) && s.matches(NUM_REG)){
				set.add(Long.valueOf(s.trim()));
			}
		}
		return new ArrayList<Long>(set);
	}	
	

	/**
	 * 解析包含的字符串
	 * @param src
	 * @param range
	 * @return
	 */
	public static String parseIncludeStr(String src, Set<String> range){
		if(CollectionUtils.isEmpty(range)){
			return null;
		}
		for(String s:range){
			if(src.contains(s)){
				return s;
			}
		}
		return null;
	}

	/**
	 * 解析包含的字符串
	 * @param src
	 * @param range
	 * @return
	 */
	public static String includeOne(Set<String> set, Set<String> range){
		if(CollectionUtils.isEmpty(range) || CollectionUtils.isEmpty(set)){
			return null;
		}
		for(String s:range){
			if(set.contains(s)){
				return s;
			}
		}
		return null;
	}
	
	/**
	 * 从后面解析包含的字符串。如果源字符串中不包含 ends，则返回null
	 * @param src
	 * @param range
	 * @return
	 */
	public static String endExtract(String src, Set<String> starts, Set<String> ends, 
			boolean includeHead, boolean includeTail){
		if(StringUtils.isBlank(src) || CollectionUtils.isEmpty(ends)){
			return null;
		}
		String head="", detail="";
		int endIndex = -1;
		for(String str:ends){
			int tmp_endIndex=src.lastIndexOf(str);
			if(tmp_endIndex>endIndex){
				detail = str;
				endIndex = tmp_endIndex;
			}
		}
		if(endIndex<0){
			return null;  
		}
		int startIndex= -1;
		for(String str:starts){
			int tmp_startIndex=src.lastIndexOf(str, endIndex-1);
			if(tmp_startIndex>-1 && tmp_startIndex+str.length()-1>startIndex){
				head= str;
				startIndex=tmp_startIndex+str.length()-1;
			}
		}
		startIndex=startIndex<0?0:(startIndex+1);
		try{
			String aim=(includeHead?head:"")+ src.substring(startIndex, endIndex)+(includeTail?detail:"");
			return aim;
		}catch(Exception e){
			log.info(e.getMessage(), e);
		}
		return null;
	}	

	
	/**
	 * 获取map的第一个键值对
	 * @param map
	 * @return
	 */
	public static <K, V> Entry<K, V> getHead(Map<K, V> map) {
		if(map==null){
			return null;
		}
	    return map.entrySet().iterator().next();
	}

	/**
	 * 获取map的第一个元素
	 * @param map
	 * @return
	 */
	public static <K, V> V getHeadVal(Map<K, V> map) {
		if(map==null){
			return null;
		}
	    return map.entrySet().iterator().next().getValue();
	}

	/**
	 * 获取map的最后一个键值对
	 * @param map
	 * @return
	 */
	public static <K, V> Entry<K, V> getTail(Map<K, V> map) {
		if(map==null){
			return null;
		}
	    Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
	    Entry<K, V> tail = null;
	    while (iterator.hasNext()) {
	        tail = iterator.next();
	    }
	    return tail;
	}

	/**
	 * 获取map的最后一个值
	 * @param map
	 * @return
	 */
	public static <K, V> V getTailVal(Map<K, V> map) {
		if(map==null){
			return null;
		}
	    Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
	    Entry<K, V> tail = null;
	    while (iterator.hasNext()) {
	        tail = iterator.next();
	    }
	    return tail.getValue();
	}


	/**
	 * 将List转成值为List的Map
	 * @param objs 需要抽取属性值的对象列表
	 * @param prop 属性名称或者方法名称
	 * @return 属性值列表的映射
	 */
	public static <T> Map<Object, List<T>> list2mapList(List<T> objs, String prop){
		Map<Object, List<T>> vals = new LinkedHashMap<Object, List<T>>();
		if(CollectionUtils.isEmpty(objs)){
			return null;
		}
		// 属性方法
		Method method=null;
		for(T obj : objs){
			if(obj==null){
				continue;
			}
			if(method==null){
				method= getMethodByNameFromObj(obj, prop);
				if(method==null){
					return vals;
				}
			}
			try{
				Object retnObj = (Object)method.invoke(obj);
				if(retnObj!=null){
					List<T> list = vals.get(retnObj);
					if(list == null){
						list = new ArrayList<>();
						vals.put(retnObj, list);
					}
					list.add(obj);
				}
			}catch(Exception e){
				log.info(method.getDeclaringClass().getName()+" invoke fail methodName="+method.getName());
				return vals;
			}
		}
		return vals;
	}

	/**
	 * 将List转成Map
	 * @param objs 需要抽取属性值的对象列表 
	 * @param prop 属性名称或者方法名称
	 * @return 属性值集合
	 */
	public static <T> Map<Object, T> list2map(List<T> objs, String prop){
		Map<Object, T> vals = new LinkedHashMap<Object, T>();
		if(CollectionUtils.isEmpty(objs)){
			return null;
		}
		// 属性方法
		Method method=null;
		for(T obj : objs){
			if(obj==null){
				continue;
			}
			if(method==null){
				method= getMethodByNameFromObj(obj, prop);
				if(method==null){
					return vals;
				}
			}
			try{
				Object retnObj = (Object)method.invoke(obj);
				if(retnObj!=null){
					vals.put(retnObj, obj);
				}
			}catch(Exception e){
				log.info(method.getDeclaringClass().getName()+" invoke fail methodName="+method.getName());
				return vals;
			}
		}
		return vals;
	}


	/**
	 * 将List转成Map
	 * @param objs 需要抽取属性值的对象列表
	 * @param propKey 键的属性名称或者方法名称
	 * @param propVal 值的属性名称或者方法名称
	 * @return 属性值集合
	 */
	public static <T> Map<Object, Object> list2mapProp(List<T> objs, String propKey, String propVal){
		Map<Object, Object> vals = new LinkedHashMap<>();
		if(CollectionUtils.isEmpty(objs)){
			return null;
		}
		// 属性方法
		Method methodKey=null;
		Method methodVal=null;
		for(T obj : objs){
			if(obj==null){
				continue;
			}
			if(methodKey==null){
				methodKey= getMethodByNameFromObj(obj, propKey);
				if(methodKey==null){
					return vals;
				}
			}
			if(methodVal==null){
				methodVal= getMethodByNameFromObj(obj, propVal);
				if(methodVal==null){
					return vals;
				}
			}
			try{
				Object keyObj = (Object)methodKey.invoke(obj);
				Object valObj = (Object)methodVal.invoke(obj);
				if(keyObj!=null && valObj!=null){
					vals.put(keyObj, valObj);
				}
			}catch(Exception e){
				log.info(methodKey.getDeclaringClass().getName()+" invoke fail methodName="+methodKey.getName());
				return vals;
			}
		}
		return vals;
	}
	
	/**
	 * 将List转成Map
	 * @param objs 需要抽取属性值的对象列表 
	 * @param prop 属性名称或者方法名称
	 * @param cla 属性的数据类型
	 * @return 属性值集合
	 */
	@SuppressWarnings("unchecked")
	public static <K, T> Map<K, T> list2map(List<T> objs, String prop, Class<K> cla){
		Map<K, T> vals = new LinkedHashMap<K, T>();
		if(CollectionUtils.isEmpty(objs)){
			return null;
		}
		// 属性方法
		Method method=null;
		for(T obj : objs){
			if(obj==null){
				continue;
			}
			if(method==null){
				method= getMethodByNameFromObj(obj, prop);
				if(method==null){
					return vals;
				}
			}
			try{
				Object retnObj = (Object)method.invoke(obj);
				if(retnObj!=null){
					vals.put((K)retnObj, obj);
				}
			}catch(Exception e){
				log.info(method.getDeclaringClass().getName()+" invoke fail methodName="+method.getName());
				return vals;
			}
		}
		return vals;
	}
	
		
	/**
	 * 将数组拆分成Map。 自然序列的奇数为key, 偶数为value
	 * 例：{"cnyPrice", "10.2","devOrderId","15292090210000000","devOrderSubject","gussing","payType","PYC","totalamount","2"}
	 * 返回cnyPrice为key,10.2为value
	 * @param origin 需要处理的源数组
	 * @return
	 */
	public static <T> Map<T, T> array2map(T[] origin){
		if(origin==null || origin.length<2){
			return null;
		}
		Map<T, T> retnMap = new LinkedHashMap<>();
		for(int i=0, leng= origin.length-1; i<leng; i+=2){
			retnMap.put(origin[i], origin[i+1]);
		}
		return retnMap;
	}


	/**
	 * 抽取对象列表的属性值
	 * @param objs 需要抽取属性值的对象列表
	 * @param prop 属性名称或者方法名称
	 * @param cl 属性的数据类型
	 * @return 属性值集合
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> abstrict2list(List objs, String prop, Class<T> cl){
		return new ArrayList<>(abstrict(objs, prop, cl));
	}
	
	/**
	 * 抽取对象列表的属性值
	 * @param objs 需要抽取属性值的对象列表 
	 * @param prop 属性名称或者方法名称
	 * @param cl 属性的数据类型
	 * @return 属性值集合
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Set<T> abstrict(List objs, String prop, Class<T> cl){
		Set<T> vals = new LinkedHashSet<T>();
		if(CollectionUtils.isEmpty(objs) || StringUtils.isBlank(prop)){
			return vals;
		}

		// 属性方法
		Method method=null;
		for(Object obj: objs){
			if(obj==null){
				continue;
			}
			if(method==null){
				method= getMethodByNameFromObj(obj, prop);
				if(method==null){
					return vals;
				}
			}
			try{
				Object retnObj = (Object)method.invoke(obj);
				if(retnObj!=null){
					vals.add((T)retnObj);
				}
			}catch(Exception e){
				log.info(method.getDeclaringClass().getName()+" invoke fail methodName="+method.getName());
				return vals;
			}
		}
		
		return vals;
	}
	
	
	/**
	 * 批量抽取对象列表的属性
	 * @param objs 需要抽取属性值的对象列表 
	 * @param propMap 键为属性名称，值为抽取的结果需要存在的集合
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void abstrictBatch(List objs, Map<String, Set> propMap){
		if(CollectionUtils.isEmpty(objs) || MapUtils.isEmpty(propMap)){
			return ;
		}

		Map<String, Method> propMethod = new LinkedHashMap<String, Method>();
		
		Method method= null;
		for(Object obj: objs){
			if(obj==null){
				continue;
			}
			for(Entry<String, Set> enty : propMap.entrySet()){
				String key = enty.getKey();
				Set val = enty.getValue();
				if(key== null){
					continue;
				}
				method = propMethod.get(key);
				if(method==null){
					method= getMethodByNameFromObj(obj, key);
					if(method==null){
						return;
					}
				}
				try{
					Object retnObj = (Object)method.invoke(obj);
					if(retnObj!=null){
						val.add(retnObj);
					}
				}catch(Exception e){
					log.info(method.getDeclaringClass().getName()+" invoke fail methodName="+method.getName());
					return ;
				}
			}
		}
		
		return ;
	}
	
	/**
	 * 从一批对象中选取指定的属性的值在propVals中的对象
	 * @param objs
	 * @param prop
	 * @param propVals
	 * @return
	 */
	public static <K, T> List<T> listChoose(Collection<T> objs, String prop, Collection<K> propVals){
		List<T> vals = new ArrayList<T>();
		if(CollectionUtils.isEmpty(objs) || CollectionUtils.isEmpty(propVals) || StringUtils.isBlank(prop)){
			return vals;
		}
		Map<Object, T> objPropMap = list2map(new ArrayList<>(objs), prop);
		// 属性方法
		Method method=null;
		for(K propVal : propVals){
			if(propVal==null){
				continue;
			}
			T obj = objPropMap.get(propVal);
			
			if(method==null){
				method= getMethodByNameFromObj(obj, prop);
				if(method==null){
					return vals;
				}
			}
			try{
				Object retnObj = (Object)method.invoke(obj);
				if(retnObj!=null){
					if(propVal == retnObj) {
						vals.add((T)obj);
					}else if(propVal instanceof String && StringUtils.equals(propVal.toString(), retnObj.toString())){
						vals.add((T)obj);
					}else if(propVal instanceof Integer && (int)propVal == (int)retnObj){
						vals.add((T)obj);
					}else if(propVal instanceof Long && (long)propVal == (long)retnObj){
						vals.add((T)obj);
					}else if(propVal.equals(retnObj)){
						vals.add((T)obj);
					}
				}
			}catch(Exception e){
				log.info(method.getDeclaringClass().getName()+" invoke fail methodName="+method.getName());
				return vals;
			}
		}
		
		return vals;
	}
		
	/**
	 * 将list中对象的属性值设置到另一个list对象的属性中。批量属性
	 * 以用户所属的城为例，来描述注释，用户有城市id，现在需要在用户列表显示用户所属的城市名称
	 调用示例：			Map<String, String> distOrigPropMap = new HashMap<String, String>();
			distOrigPropMap.put("cityname", "name");
			distOrigPropMap.put("cityname2", "name2");
			Map<String, Class> distPropClaMap = new HashMap<String, Class>();
			distPropClaMap.put("cityname", String.class);
			distPropClaMap.put("cityname2", String.class);
			giveVal(cs, "id", ps, "cityid", distOrigPropMap, distPropClaMap);
	 * @param origObjs 源对象列表(例：城市列表citys)
	 * @param origMarkProp 源标对象的标识属性。即城市id在城市表中的列(例：城市id)
	 * @param distObjs 目标对象列表(例：用户列表persons)
	 * @param distMarkProp 目标对象的标识属性。即城市id在用户表中的列
	 * @param distOrigPropMap 目标对象的属性与源对象属性的映射。即用户类中的属性名称“城市名称”与城市表中的属性“名称”的映射
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void giveVal(List origObjs, String origMarkProp, List distObjs, 
			String distMarkProp, Map<String, String> distOrigPropMap, Map<String, Class> distPropClaMap){
		if(CollectionUtils.isEmpty(origObjs) || CollectionUtils.isEmpty(distObjs)
				|| MapUtils.isEmpty(distOrigPropMap) || StringUtils.isBlank(origMarkProp)
				|| StringUtils.isBlank(distMarkProp)){
			return ;
		}

		Map origValMap = new HashMap(); // 源数据对象的标识，对象映射。即城市id与城市对象的映射关系
		
		Method origMarkGetMethod= getMethodByName(origObjs.get(0).getClass(), origMarkProp);
		if(origMarkGetMethod==null){
			return;
		}
		for(Object obj: origObjs){ // 将源数据转换为map
			if(obj==null){
				continue;
			}
			try{
				Object retnObj = (Object)origMarkGetMethod.invoke(obj);
				if(retnObj!=null){
					origValMap.put(retnObj, obj);
				}
			}catch(Exception e){
				log.info(origMarkGetMethod.getDeclaringClass().getName()+" invoke fail methodName="+origMarkGetMethod.getName());
				return ;
			}
		}
		Map<String, Method> origPropGetMethodMap = new LinkedHashMap<String, Method>(); // 源属性，方法映射
		Map<String, Method> distPropSetMethodMap = new LinkedHashMap<String, Method>(); // 目标属性，方法映射
		Method distMarkGetMethod= getMethodByName(distObjs.get(0).getClass(), distMarkProp); // 目标对象的外键标识的get方法，用来获取标识值。即城市id
		if(distMarkGetMethod== null){
			if(distMarkGetMethod==null){
				return ;
			}
		}
		for(Object distObj: distObjs){
			if(distObj==null){
				continue;
			}
			Object distMark= null;
			try{
				distMark=distMarkGetMethod.invoke(distObj); // 目标标识，即用户表中的城市id
			}catch(Exception e){
				log.info("invoke val fail", e);
				return;
			}
			if(distMark==null){ // 用户表的城市id为空
				continue;
			}
			Object origObj = origValMap.get(distMark); // 源对象，即城市对象。每个用户都有一个城市id
			if(origObj==null){ // 城市表的城市id对应的记录为空
				continue;
			}
			for(Entry<String, String> enty : distOrigPropMap.entrySet()){ // 给属性设置值
				String origProp = enty.getValue();
				String distProp = enty.getKey();
				Method origPropGetMethod= origPropGetMethodMap.get(origProp);
				if(origPropGetMethod==null){
					origPropGetMethod = getMethodByNameFromObj(origObj, origProp);
					if(origPropGetMethod==null){
						continue;
					}
					origPropGetMethodMap.put(origProp, origPropGetMethod);
				}
				Method distPropGetMethod= distPropSetMethodMap.get(distProp);
				if(distPropGetMethod==null){
					distPropGetMethod = setMethodByNameFromObj(distObj, distProp, distPropClaMap.get(distProp));
					if(distPropGetMethod==null){
						continue;
					}
					distPropSetMethodMap.put(distProp, distPropGetMethod);
				}
				try{
					Object origPropVal= origPropGetMethod.invoke(origObj);				
					if(origPropVal!=null){
						distPropGetMethod.invoke(distObj, origPropVal);
					}
				}catch(Exception e){
					log.info("invoke val fail", e);
					return ;
				}
			}
		}
		return ;
	}
	
	/**
	 * 将list中对象的属性值设置到另一个list对象的属性中。单个属性
	 * 以用户所属的城为例，来描述注释，用户有城市id，现在需要在用户(Person)列表显示用户所属的城市(City)名称
	 * 调用示例：giveVal(cs, "id", ps, "cityid", "name", "cityname", String.class);
			giveVal(cs, "id", ps, "cityid", "name2", "cityname2", String.class);
	 * @param origObjs 源对象列表(例：城市列表citys)
	 * @param origMarkProp 源标对象的标识属性。即城市id在城市表中的列(例：城市id)
	 * @param distObjs 目标对象列表(例：用户列表persons)
	 * @param distMarkProp 目标对象的标识属性。即城市id在用户表中的列(例：Person类的成员变量属性名称cityid，存放的是city的id)
	 * @param origProp 源对象要被取值的属性名，比如，城市名称(例：城市类的成员变量属性名称name)
	 * @param distProp 目标对象要被设值的属性名，比如，用户的城市名称(例：Person类中存放城市名称的成员变量的名称“cityname”）
	 * @param distPropCla 目标对象要被设值的属，比如，用户的城市名称，应该是String类型(例：Person类中存放城市名称的变量的数据类型)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void giveVal(List origObjs, String origMarkProp, List distObjs, 
			String distMarkProp, String origProp, String distProp, Class distPropCla){
		if(CollectionUtils.isEmpty(origObjs) || StringUtils.isBlank(origMarkProp)
				|| CollectionUtils.isEmpty(distObjs) || StringUtils.isBlank(distMarkProp)
				|| StringUtils.isBlank(origProp) || StringUtils.isBlank(distProp) || distPropCla==null){
			return ;
		}

		Map origValMap = new LinkedHashMap(); // 源数据对象的标识，对象映射。即城市id与城市对象的映射关系
		Method origMarkGetMethod= getMethodByName(origObjs.get(0).getClass(), origMarkProp); // 源对象的标识方法，即getId()
		if(origMarkGetMethod==null){
			return;
		}
		for(Object obj: origObjs){ // 将源数据转换为map
			if(obj==null){
				continue;
			}
			try{
				Object retnObj = (Object)origMarkGetMethod.invoke(obj);
				if(retnObj!=null){
					origValMap.put(retnObj, obj);
				}
			}catch(Exception e){
				log.info(origMarkGetMethod.getDeclaringClass().getName()+" invoke fail methodName="+origMarkGetMethod.getName());
				return ;
			}
		}
		Method origPropGetMethod= getMethodByNameFromObj(origObjs.get(0), origProp);
		Method distMarkGetMethod= getMethodByName(distObjs.get(0).getClass(), distMarkProp); // 目标对象的外键标识的get方法，用来获取标识值。即城市id
		Method distPropSetMethod= setMethodByNameFromObj(distObjs.get(0), distProp, distPropCla);
		if(origPropGetMethod== null || distMarkGetMethod== null || distMarkGetMethod==null){
			return ;
		}
		for(Object distObj: distObjs){
			if(distObj==null){
				continue;
			}
			Object distMark= null;
			try{
				distMark=distMarkGetMethod.invoke(distObj); // 目标标识，即用户表中的城市id
			}catch(Exception e){
				log.info("invoke val fail", e);
				return;
			}
			if(distMark==null){ // 用户表的城市id为空
				continue;
			}
			Object origObj = origValMap.get(distMark); // 源对象，即城市对象。每个用户都有一个城市id
			if(origObj==null){ // 城市表的城市id对应的记录为空
				continue;
			}
			try{
				Object origPropVal= origPropGetMethod.invoke(origObj);
				if(origPropVal!=null){
					distPropSetMethod.invoke(distObj, origPropVal);
				}
			}catch(Exception e){
				log.info("invoke val fail", e);
				return ;
			}
		}
		return ;
	}
	
	/**
	 * 抽取map中的对象，然后放到一个数组列表里面。用于2层结构
	 * @param map
	 * @return
	 */
	public static <T> List<T> abstrictMapObj2(Map<String, Map<String, T>> map){
		if(MapUtils.isEmpty(map)){
			return null;
		}
		List<T> objs = new ArrayList<>();
		for(Entry<String, Map<String, T>> enty1 : map.entrySet()){
			objs.addAll(enty1.getValue().values());
		}
		return objs;
	}
	
	/**
	 * 对象属性值拷贝
	 * @param origObj 原对象，有值的对象
	 * @param distObj 目标对象，需要被设置值的对象
	 * @param propMapStrs 属性映射字符串，用英文逗号分隔
	 * @param conv 属性的键值对是否要转换
	 */
	public static void objCopy(Object origObj, Object distObj, String propMapStrs, Boolean conv){
		if(origObj == null || distObj == null || StringUtils.isBlank(propMapStrs)){
			return;
		}
		conv = conv== null ? false : conv;
		objCopy(origObj, distObj, propMapStrs.split(","), conv);
	}

	/**
	 * 对象属性值拷贝
	 * @param origObj 原对象，有值的对象
	 * @param distObj 目标对象，需要被设置值的对象
	 * @param propMapStrs 属性映射字符串数组
	 * @param conv 属性的键值对是否要转换
	 */
	public static void objCopy(Object origObj, Object distObj, String[] propMapStrs, Boolean conv){
		if(origObj == null || distObj == null || ArrayUtils.isEmpty(propMapStrs)){
			return;
		}
		conv = conv== null ? false : conv;
		Map<String, String> propMap = Comn.array2map(propMapStrs);
		if(conv){
			propMap = MapUtils.invertMap(propMap);
		}
		objCopy(origObj, distObj, propMap);
	}

	/**
	 * 对象属性值拷贝
	 * @param origObj 原对象，有值的对象
	 * @param distObj 目标对象，需要被设置值的对象
	 */
	public static void objCopy(Object origObj, Object distObj, Map<String, String> propMap){
		if(origObj == null || distObj == null || MapUtils.isEmpty(propMap)){
			return;
		}
		for(Entry<String, String> enty : propMap.entrySet()){
			Method origMethod = getMethodByNameFromObj(origObj, enty.getKey());
			if(origMethod==null){
				log.info("origMethod is inextance, method name="+enty.getKey());
				continue;
			}
			try{
				Object origPropVal= origMethod.invoke(origObj);
				if(origPropVal!=null){
					Method distPropSetMethod = setMethodByNameFromObj(distObj, enty.getValue(), origPropVal.getClass());
					distPropSetMethod.invoke(distObj, origPropVal);
				}
			}catch(Exception e){
				log.info("invoke val fail", e);
				return ;
			}
		}
	}
	
	

	/**
	 * 获取bean对象的get方法
	 * @param obj 对象
	 * @param prop 对象的属性
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Method getMethodByNameFromObj(Object obj, String prop){
		if(obj == null || StringUtils.isBlank(prop)){
			return null;
		}
		Class cla=obj.getClass();
		return getMethodByName(cla, prop);
	}


	/**
	 * 获取bean对象的get方法
	 * @param cla 对象的类
	 * @param prop 对象的属性
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getMethodByName(Class cla, String prop){
		String methodName= "get"+prop.substring(0,1).toUpperCase()+prop.substring(1);
		Method method=null;
		try{
			method=cla.getMethod(methodName);
		}catch(Exception e){
			try{
				method=cla.getMethod(prop);
			}catch(Exception e2){
				log.info(cla.getName()+" inexistence methodName "+prop+","+methodName);
			}
		}
		return method;
	}
	

	/**
	 * 获取bean对象的set方法
	 * @param obj 对象
	 * @param prop 对象的属性
	 * @param valCla 对象的属性的类
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Method setMethodByNameFromObj(Object obj, String prop, Class valCla){
		if(obj == null || StringUtils.isBlank(prop)){
			return null;
		}
		Class cla=obj.getClass();
		return setMethodByName(cla, prop, valCla);
	}
	
	// 收集bit位的值
	public static List<Long> splitBit(long src){
		List<Long> bi= new ArrayList<Long>();
		while(src>0){
			long v=-src&src;
			bi.add(v);
			src-=v;
		}
		return bi;
	}

	/**
	 * 获取bean对象的set方法
	 * @param cla 对象的类
	 * @param prop 对象的属性
	 * @param valCla 对象的属性的类
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method setMethodByName(Class cla, String prop, Class valCla){
		String methodName= "set"+prop.substring(0,1).toUpperCase()+prop.substring(1);
		Method method=null;
		try{
			method=cla.getMethod(methodName, valCla);
		}catch(Exception e){
			try{
				method=cla.getMethod(prop, valCla);
			}catch(Exception e2){
				log.info(cla.getName()+" inexistence methodName "+prop+","+methodName+",valCla="+valCla);
			}
		}
		return method;
	}
	
    
    // session
    // 获取session中的对象
    public static Object getSesnObj(HttpServletRequest request, String key){
    	if(request == null || StringUtils.isBlank(key)){
    		return null;
    	}
    	Object o = request.getSession().getAttribute(key);
    	return o;
    }
    // 获取session中的对象
    public static void setSesnObj(HttpServletRequest request, String key, Object obj){
    	if(request == null || StringUtils.isBlank(key) || obj== null){
    		return ;
    	}
    	request.getSession().setAttribute(key, obj);
    }
    
    // 获取session中的对象
    public static void removeSesnObj(HttpServletRequest request, String key){
    	if(request == null || StringUtils.isBlank(key)){
    		return ;
    	}
    	request.getSession().removeAttribute(key);
    }
	
	public static String doGet(String url){
    	return doGet(url, null, "UTF-8");
    }
    
	
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doGet(String url,Map<String,String> params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(30000).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        try {
            if(MapUtils.isNotEmpty(params)){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet http = new HttpGet(url);
			http.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
					+ "Chrome/46.0.2490.86 Safari/537.36");
            CloseableHttpResponse response = httpClient.execute(http);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
            	http.abort();
                log.error("HttpClient,error status code=" + statusCode);
                return null;
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
			log.error("request error, url="+url, e);
        }
        return null;
    }
    
	
	public static String hpost(String uri, String jsonStr){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(uri);
			StringEntity entity = new StringEntity(jsonStr, Comn.CHARCODE);
			entity.setContentType("application/json");
			
			httpPost.setEntity(entity);
			CloseableHttpResponse response1 = httpclient.execute(httpPost);
			// Comn.pl(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent(),"utf-8"));
			String line = null;
			StringBuilder lineSb = new StringBuilder();
			while((line=br.readLine())!=null){
				lineSb.append(line);
			}
			EntityUtils.consume(entity1);
			return lineSb.toString();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{
			httpclient.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

	/**
	 * 获取json里面的值 
	 * @param json json字符串
	 * @param propStr 用英文逗号分隔的属性名，例："results,status"
	 * @return
	 */
	public static String getJsonVal(String json, String propStr) {
		if (StringUtils.isBlank(propStr)) {
			return json;
		}
		String[] props = propStr.split(",");
		return getJsonVal(json, props);
	}

	public static String getJsonVal(String json, String[] props) {
		if (StringUtils.isBlank(json) || props == null || props.length == 0) {
			return json;
		}
		JSONObject jsonObj = JSONObject.parseObject(json);
		return getJsonVal(jsonObj, props);
	}


	public static String getJsonVal(JSONObject jsonObj, String propStr) {
		if (jsonObj == null || StringUtils.isBlank(propStr)) {
			return null;
		}
		String[] props = propStr.split(",");
		return getJsonVal(jsonObj, props);
	}
	
	public static String getJsonVal(JSONObject jsonObj, String[] props) {
		JSONObject jo = jsonObj;
		if (jo == null || props == null || props.length == 0) {
			return null;
		}
		String s = null;
		for (int i = 0, l = props.length; i < l; ++i) {
			String p = props[i];
			s = jo.getString(p);
			if (StringUtils.isBlank(s)) {
				return null;
			}
			if (i == l - 1) {
				return s;
			}
			try {
				jo = JSONObject.parseObject(s);
			} catch (Exception e) {
				log.debug("error json=" + s);
				return s;
			}
		}
		return s;
	}

	// https://blog.csdn.net/software7503/article/details/74936220
	public static  String HanDigiStr[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	public static  String HanDiviStr[] = new String[] { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟",
			"万", "拾", "佰", "仟", "亿", "拾", "佰","仟", "万", "拾", "佰", "仟" };

	/**
	 * 生成中文的钱的金额大写
	 * @param moneyStr 可为小数
	 * @return
	 */
	public static String money2HanStr(String moneyStr) {
		// NumStr 输入字符串必须正整数，只允许前导空格(必须右对齐)，不宜有前导零
		String NumStr = moneyStr.contains(".") ? moneyStr.substring(0, moneyStr.indexOf(".")) : moneyStr;
		String decimal = moneyStr.contains(".") && moneyStr.indexOf(".")+1<moneyStr.length()
				? moneyStr.substring(moneyStr.indexOf(".")+1, moneyStr.length()) : null;
		String RMBStr = "";
		boolean lastzero = false;
		boolean hasvalue = false; // 亿、万进位前有数值标记
		int len, n;
		len = NumStr.length();
		if (len > 15) {
			return "数值过大!";
		}
		for (int i = len - 1; i >= 0; i--) {
			if (NumStr.charAt(len - i - 1) == ' ') {
				continue;
			}
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9) {
				return "输入含非数字字符!";
			}
			if (n != 0) {
				if (lastzero) {
					RMBStr += HanDigiStr[0]; // 若干零后若跟非零值，只显示一个零
				}
				// 除了亿万前的零不带到后面
				// if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) ) //
				// 如十进位前有零也不发壹音用此行
				if (!(n == 1 && (i % 4) == 1 && i == len - 1)) { // 十进位处于第一位不发壹音
					RMBStr += HanDigiStr[n];
				}
				RMBStr += HanDiviStr[i]; // 非零值后加进位，个位为空
				hasvalue = true; // 置万进位前有值标记

			} else {
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) { // 亿万之间必须有非零值方显示万
					RMBStr += HanDiviStr[i]; // “亿”或“万”
				}
			}
			if (i % 8 == 0) {
				hasvalue = false; // 万进位前有值标记逢亿复位
			}
			lastzero = (n == 0) && (i % 4 != 0);
		}

		if (RMBStr.length() == 0) {
			return HanDigiStr[0]+"元"; // 输入空字符或"0"，返回"零"
		}else{
			RMBStr+="元";
		}
		if(StringUtils.isNotBlank(decimal)){
			RMBStr+= HanDigiStr[Integer.valueOf(String.valueOf(decimal.charAt(0)))]+"角";
			if(decimal.length()>1){
				RMBStr+= HanDigiStr[Integer.valueOf(String.valueOf(decimal.charAt(1)))]+"分";
			}
		}

		return RMBStr;
	}

}
