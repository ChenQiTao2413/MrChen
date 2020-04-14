package experiment.test1;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class App {
    private static final String PATH = "d:/test1.txt";

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Please provide an input!");
            System.exit(0);
        }
        if(args[0].equals("1")){
            insert(args[1],args[2]);
        }else{
            String pwd = queryPwd(args[1]);
            if(pwd.equals("")){
                System.out.println("没有该用户");
            }else{
                if(pwd.equals(args[2])){
                    System.out.println("登录成功,信息：账号：" + args[1] + ",密码：" + getSHA256(pwd));
                }else{
                    System.out.println("登录失败,正确信息：账号：" + args[1] + ",密码：" + getSHA256(pwd));
                }
            }
        }
    }

    public static void insert(String name,String pwd) throws IOException {
        File file = new File(PATH);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file,true);
        String content = name + " " + pwd + "\r\n";
        writer.write(content);
        writer.close();
        System.out.println("增加用户成功");
    }

    public static String queryPwd(String name) throws IOException {
        FileReader fr = new FileReader(PATH);
        BufferedReader br = new BufferedReader(fr);
        String str = "";
        while((str = br.readLine()) != null){
            int index = str.indexOf(" ");
            String tempName = str.substring(0,index);
            String tempPwd = str.substring(index + 1,str.length());
            if(name.equals(tempName)){
                return tempPwd;
            }
        }
        return "";
    }

    public static String getSHA256(String str){
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }
    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}

