package jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        jedis
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/22 9:11
 *   *
 **/
public class JedisTest {
    @Test
    public void testJedisSingle() {
        Jedis jedis = new Jedis("192.168.73.128", 6379);
        jedis.auth("wyx2020");
        jedis.set("key1", "shenmolie");
        System.out.println(jedis.get("key1"));
        jedis.close();
    }

    @Test
    public void testJedisPool() {
        JedisPool pool = new JedisPool("192.168.73.128", 6379);
        Jedis jedis = pool.getResource();
        jedis.auth("wyx2020");
        System.out.println(jedis.get("key1"));
        jedis.close();
        pool.close();
    }

    @Test
    public void testJedisCluster() {
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.73.128", 7001));
        nodes.add(new HostAndPort("192.168.73.128", 7002));
        nodes.add(new HostAndPort("192.168.73.128", 7003));
        nodes.add(new HostAndPort("192.168.73.128", 7004));
        nodes.add(new HostAndPort("192.168.73.128", 7005));
        nodes.add(new HostAndPort("192.168.73.128", 7006));
        JedisCluster cluster = new JedisCluster(nodes);
        System.out.println(cluster.get("key1"));
        cluster.close();
    }

    @Test
    public void testSpringJedisSingle() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        JedisPool jedisPool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get("key1");
        System.out.println(result);
        jedis.close();
        jedisPool.close();
    }

    @Test
    public void testSpringJedisCluster() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisCluster");
        String result = jedisCluster.get("key1");
        System.out.println(result);
        jedisCluster.close();
    }
}
