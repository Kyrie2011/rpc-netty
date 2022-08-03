模块介绍：
rpc-provider, 服务提供者。负责发布RPC服务，接收和处理RPC请求。
rpc-consumer, 服务消费者。使用动态代理发起RPC远程调用，帮助使用者来屏蔽底层网络通信的细节。
rpc-registry, 注册中心模块。提供服务注册、服务发现、负载均衡的基本功能。
rpc-protocol, 网络通信模块。包含RPC协议的编解码器、序列化和反序列化工具等。
rpc-core, 基础类库。提供通用的工具类以及模型定义，例如RPC请求和响应类、RPC服务元数据类等。
rpc-facade, RPC服务接口。


1. 服务提供者发布服务
服务提供者rpc-provider需要完成哪些功能呢？主要分为四个核心流程：
(1) 服务提供者启动服务，并暴露服务端口；
(2) 启动时扫描需要对外发布的服务，并将服务元数据信息发布到注册中心；
(3) 接收RPC请求，解码后得到请求消息；
(4) 提交请求至自定义线程池进行处理，并将处理结果写回客户端。

RpcProvider重写了BeanPostProcessor接口的postProcessorAfterInitialization方法，对所有初始化完成后的Bean进行扫描。
如果Bean包含@RpcService注解，那么通过注解读取服务的元数据信息并构造出ServiceMeta对象，将服务的元数据信息发布至注册中心。


2. 服务消费者订阅服务
与服务提供者不同的是，服务消费者并不是一个常驻的服务，每次发起 RPC 调用时它才会去选择向哪个远端服务发送数据。
所以服务消费者的实现要复杂一些，对于声明@RpcReference注解的成员变量，需要构造出一个可以真正进行RPC调用的Bean，
然后将它注册到Spring的容器中。

RpcConsumerPostProcessor类中重写了BeanFactoryPostProcessor的postProcessBeanFactory方法，



