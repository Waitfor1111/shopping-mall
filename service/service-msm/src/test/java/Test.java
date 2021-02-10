/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/29 16:53
 */
public class Test {
    public static void main(String[] args) {


        String randomcode = "";
        // 用字符数组的方式随机
        String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] m = model.toCharArray();
        for (int j = 0; j < 6; j++) {
            char c = m[(int) (Math.random() * 36)];
            // 保证六位随机数之间没有重复的
            if (randomcode.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            randomcode = randomcode + c;
        }
        System.out.println(randomcode);
    }

}
