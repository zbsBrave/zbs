package rabbitmq.test;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;

/**
 * @author zbs
 * @since 2022/6/30 18:52
 */
public class ConnectTest {
    public static final String host = "10.10.1.32";
    public static final int port = 5672;
    public static final String username = "syhd_config", pwd = "syhd_config";
    
    public static void main(String[] args) {
        Connection connection = getFactory().createConnection();
        


    }
    public static PooledChannelConnectionFactory getFactory(){
        ConnectionFactory f = new ConnectionFactory();
        f.setHost(host);
        f.setPort(port);
        f.setUsername(username);
        f.setPassword(pwd);

        PooledChannelConnectionFactory pcf = new PooledChannelConnectionFactory(f);
        pcf.setPoolConfigurer( (pool, tx) -> {
            if(tx){
                System.out.println("tx -------------------");
            }else {
                System.out.println("tx else -------------------");
            }
        });
        return pcf;
    }
}
