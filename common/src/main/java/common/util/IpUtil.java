package common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zbs
 * @since 2022/6/28 18:00
 */
public class IpUtil {
    public static void main(String[] args) throws UnknownHostException {
        //10.10.0.125
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostAddress);

        //127.0.0.1
        String hostAddress1 = InetAddress.getLoopbackAddress().getHostAddress();
        System.out.println(hostAddress1);

    }
}
