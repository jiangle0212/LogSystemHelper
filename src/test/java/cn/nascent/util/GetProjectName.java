package cn.nascent.util;

import java.io.File;

/**
 * @author wei
 * @date 2018/12/10
 * <p>
 * <p>
 * 用来获取引用这个jar包的项目名
 */
public class GetProjectName {

    public static void main(String[] args) {

        File testFile = new File(".");
        String[] strings = testFile.getAbsolutePath().split("/");
        System.out.println(strings[strings.length - 2]);
        for (String tar : strings) {
            System.out.println(tar);
        }

    }
}
