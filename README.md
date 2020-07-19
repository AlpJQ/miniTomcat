# 简化版Web服务器（包括HTTP服务器和Servlet容器）
## 具备的功能(均为简化版的实现)：
- HTTP Protocol
- Servlet
- ServletContext
- Request
- Response
- Dispatcher
- Static Resources & File Download
- Error Notification
- Get & Post & Put & Delete
- web.xml parse
- Forward
- Redirect
- Simple TemplateEngine
- session
- cookie
- filter
- listener 

## 使用技术
基于Java BIO/NIO/AIO、多线程、Socket网络编程、XML解析、log4j/slf4j日志
基于Spring的PathMatcher实现SpringMVC风格的路径匹配

## BIO
一个Acceptor阻塞式获取socket连接，然后线程池阻塞式等待socket读事件，处理业务逻辑，最后写回
每个HTTP连接结束后由客户端关闭TCP连接


## NIO Reactor
多个（1个或2个）Acceptor阻塞式获取socket连接，然后多个Poller（处理器个数个）非阻塞式轮询socket读事件，检测到读事件时将socket交给线程池处理业务逻辑
实现HTTP的keep-alive（复用socket连接）

![image](http://markdown-1252651195.cossh.myqcloud.com/%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6.jpg)


## AIO
实现一个AIO版本

## 压力测试

### BIO
使用JMeter进行压力测试：connection:close
以下测试总请求次数都为20000次

2个线程，每个线程循环访问10000次，吞吐量为556个请求/sec，平均响应时间为3ms
20个线程，每个线程循环访问1000次，吞吐量为650个请求/sec,平均响应时间为22ms
200个线程，每个线程循环访问100次，吞吐量为644个请求/sec,平均响应时间为209ms
1000个线程，每个线程循环访问20次，吞吐量为755个请求/sec,平均响应时间为774ms


### NIO

使用JMeter进行压力测试：connection:keep-alive
以下测试总请求次数都为20000次

2个线程，每个线程循环访问10000次，吞吐量为559个请求/sec，平均响应时间为2ms
20个线程，每个线程循环访问1000次，吞吐量为651个请求/sec,平均响应时间为21ms
200个线程，每个线程循环访问100次，吞吐量为659个请求/sec,平均响应时间为201ms
1000个线程，每个线程循环访问20次，吞吐量为503个请求/sec,平均响应时间为1396ms

### AIO

使用JMeter进行压力测试：connection:keep-alive
以下测试总请求次数都为20000次

2个线程，每个线程循环访问10000次，吞吐量为633个请求/sec，平均响应时间为2ms
20个线程，每个线程循环访问1000次，吞吐量为764个请求/sec,平均响应时间为16ms
200个线程，每个线程循环访问100次，吞吐量为738个请求/sec,平均响应时间为170ms
1000个线程，每个线程循环访问20次，吞吐量为704个请求/sec,平均响应时间为677ms,但有接近20%的错误率，错误信息是connection refused
