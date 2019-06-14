/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        PACKAGE_NAME
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/12/2 10:26
 *   *
 **/
public class Test1 {
    public static void main(String[] args) {
        String a = "www.taotao.com";
        String[] split = a.split(".");
        for (String s : split)
            System.out.println(s);
    }
}
