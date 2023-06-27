package cat.customize.ulite.system;

import java.util.Random;

/**
 * Created by HSL
 * on 2023/5/24.
 */

public class RandomUlite {

    /**
     * 生成随机整数（包含起始与终止范围）
     *
     * @param num1 起始范围参数
     * @param num2 终止范围参数
     * @return 随机整数
     * @author pan_junbiao
     */
    public static int randomNum(int num1, int num2) {
        int result = (int) (num1 + Math.random() * (num2 - num1 + 1));
        return result;
    }

    /**
     * 随机生成字符串
     *
     * @param length 随机字符串长度
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random randomUlite = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = randomUlite.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机产生一个整数
     *
     * @return
     */
    public int getInt() {
        Random randomUlite = new Random();
        return randomUlite.nextInt();
    }

    /**
     * 随机产生一个大于等于0且小于max的整数
     * @param max
     */
    public int getInt(int max){
        return new Random().nextInt(max);
    }

    /**
     * 随机产生一个布尔类型的值
     * @return
     */
    public boolean getBoolean(){
        return new Random().nextBoolean();
    }
    /**
     * 随机产生一个双精度值
     * @return
     */
    public double getDouble(){
        return new Random().nextDouble();
    }

    /**
     * 随机产生一个浮点型的值
     * @return
     */
    public float getFloat(){
        return new Random().nextFloat();
    }

    /**
     * 随机产生一个概率密度为高斯分布的双精度值
     * @return
     */
    public double getGaussian(){
        return new Random().nextGaussian();
    }
}
