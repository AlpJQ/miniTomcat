package com.nocoder.minitomcat.network.endpoint.aio;

import com.nocoder.minitomcat.network.connector.aio.AioAcceptor;
import com.nocoder.minitomcat.network.dispatcher.aio.AioDispatcher;
import com.nocoder.minitomcat.network.endpoint.Endpoint;
import com.nocoder.minitomcat.network.wrapper.aio.AioSocketWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.*;

/**
 * Java的AIO的编程风格与BIO、NIO很不相似，accept、read、write都是异步方法，
 * 后续代码执行必须放在CompletionHandler中进行回调。
 */
@Slf4j
public class AioEndpoint extends Endpoint {

    private final Logger logger = LoggerFactory.getLogger(AioEndpoint.class);

    private AsynchronousServerSocketChannel server;
    private AioDispatcher aioDispatcher;
    private AioAcceptor aioAcceptor;
    private ExecutorService pool;

    private void initDispatcherServlet() {
        aioDispatcher = new AioDispatcher();
    }

    private void initServerSocket(int port) throws IOException {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Endpoint Pool-" + count++);
            }
        };
        int processors = Runtime.getRuntime().availableProcessors();
        pool = new ThreadPoolExecutor(processors, processors, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        // 以指定线程池来创建一个AsynchronousChannelGroup  
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup
                .withThreadPool(pool);
        // 以指定线程池来创建一个AsynchronousServerSocketChannel  
        server = AsynchronousServerSocketChannel.open(channelGroup)
                // 指定监听本机的PORT端口  
                .bind(new InetSocketAddress(port));
        // 使用CompletionHandler接受来自客户端的连接请求  
        aioAcceptor = new AioAcceptor(this);
        // 开始接收客户端连接
        accept();
    }

    /**
     * 接收一个客户端连接
     */
    public void accept() {
        server.accept(null, aioAcceptor);
    }

    @Override
    public void start(int port) {
        try {
            initDispatcherServlet();
            initServerSocket(port);
            logger.info("服务器启动");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("初始化服务器失败");
            close();
        }
    }

    @Override
    public void close() {
        aioDispatcher.shutdown();
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行读已就绪的客户端连接的请求
     * @param socketWrapper
     */
    public void execute(AioSocketWrapper socketWrapper) {
        aioDispatcher.doDispatch(socketWrapper);
    }

}
