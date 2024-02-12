# Master Spring 6, Spring Boot 3, REST, JPA, Hibernate
_provided by Eazy Bytes, Madan Reddy on Udemy platform_ <br><br>

### Table of Contents
##### Spring Core: Bean, depedency Injection, Inversion of Control, Aspect-Oriented Programming
1. [Inversion of Control & Dependency Injection](#anchor_11)<br/>
2. [stereotype](#anchor_12)<br/>
3. [@Autowired Annotation](#anchor_13)<br/>
4. [Bean Scope](#anchor_14)<br/>
5. [Aspect-Oriented Programming](#anchor_15)<br/>
##### Spring Web
1. [stereotype](#anchor_21)<br/>

## Inversion of Control & Dependency Injection<a name="anchor_11"></a>
IoC is a software design principle, Dependency Injection is the pattern through which Inversion of Control achieved.
- Spring IoC container

## stereotype<a name="anchor_12"></a>
Stereotype（原型）注解是一组用于定义Spring管理的bean的注解。这些注解提供了一种方便的方式来声明bean，并且通常还携带额外的元数据，让Spring容器能够识别和处理它们。
- @Component: 这是最通用的stereotype注解，它表明一个类被Spring框架视为组件。当使用类路径扫描时，Spring会自动找到这些注解，并注册它们为bean。
- @Service: 这个注解是@Component的特化，它用于标注服务层的组件。在语义层面上，它表示这个类提供了一种业务服务。
- @Repository: 这个注解是@Component的特化，用于标注数据访问对象（DAO），即那些提供了数据访问操作的类。它也可以帮助Spring提供数据访问时的特定功能，如异常翻译。
- @Controller: 用于标注控制器组件，特别是在Spring MVC模式中，这个注解定义了一个类作为请求的处理器。

## @Autowired Annotation <a name="anchor_13"></a>
在设置器方法上使用@Autowired注解来告知Spring这个方法应该被用来依赖注入。
- Field Injection
  ```
  @Autowired
  private Engine engine;
  ```
  没什么好说的
- Setter Injection
  ```
  @Component
  public class VehicleService {
    private Engine engine;
    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
  }
  ```
  setEngine方法用于注入Engine类型的依赖。当Spring创建VehicleService的实例时，它将寻找一个匹配的Engine类型的bean，并通过调用setEngine方法将其注入。
- Constructor Injection
  ```
  @Component
  public class VehicleService {
    private final Engine engine;
    private final Transmission transmission;

    @Autowired // 这个注解在Spring 4.3之后是可选的
    public VehicleService(Engine engine, Transmission transmission) {
        this.engine = engine;
        this.transmission = transmission;
    }
  }
  ```
  VehicleService类有两个依赖：Engine和Transmission。这两个依赖都是通过构造器注入到VehicleService类中的。

## Bean Scope<a name="anchor_14"></a>
在Spring框架中，默认情况下，bean是以单例（Singleton）作用域创建的。
- Singleton(default) and Race condition
  - 在单例bean的上下文中，如果bean包含了可变的共享数据，且没有适当的同步机制，当多个线程同时访问这个bean时，就可能会出现竞争条件。例如，如果你有一个计数器bean，它被多个线程用于增加计数，如果没有同步这些操作，最终的计数可能会因为竞争条件而错误。
    - 同步：确保所有修改共享状态的方法都是同步的。你可以使用Java的同步关键字（synchronized），或者使用其他并发工具，如ReentrantLock。
    - Spring作用域：如果可能，考虑将bean的作用域从单例更改为其他作用域，如请求或会话作用域。对于Web应用程序，每个HTTP请求可以有自己的bean实例（请求作用域），或者每个用户会话可以有自己的bean实例（会话作用域）。
    - 使用并发集合：Java提供了一些线程安全的集合类，如ConcurrentHashMap，你可以使用这些集合来存储bean的状态，以减少需要自己进行同步的地方。
    - 使用原子变量：对于简单的计数器或标志，可以使用Java的原子变量类，如AtomicInteger或AtomicBoolean。
- Eager & Lazy Initialization
  - Eager(默认)情况下，Spring IoC容器会在启动时立即创建并初始化所有的单例scope bean, lazy初始化会推迟bean的创建和初始化，直到第一次需要它时
- Prototype Scope
  ```
  @Scope(BeanDefinition.SCOPE_PROTOTYPE)
  ```
  当一个bean被定义为Prototype作用域时，Spring容器每次被请求时都会创建一个新的bean实例，按需创建的，不会在应用启动时就被创建。
  
## Aspect-Oriented Programming<a name="anchor_15"></a>
AOP's ability to separate concerns that are not central to business logic from the main codebase can lead to more maintainable and less cluttered code.
- Aspects: These are the modular units of cross-cutting concerns, such as logging, transaction management, or security.
- Join Points: 程序执行中的特定点，例如方法调用或字段访问操作，可以在这些点上应用切面。
- Weaving
- 增强（Advice）：切面在特定连接点采取的行动。有几种类型的增强：
  - 前置增强（Before）：在连接点方法执行前运行。
  - 后置增强（After）：无论其结果如何，在连接点方法执行后运行。
  - 返回后增强（After-returning）：在连接点方法成功返回后运行。
  - 异常后增强（After-throwing）：在连接点方法通过抛出异常退出后运行。
  - 环绕增强（Around）：代替连接点方法运行，并能决定是否继续执行原方法。
  
  Example1: Logger Aspect，目的是打log记录状态
  ```
  @Aspect
  @Component
  @Order(2)
  public class LoggerAspect {
    @Around("execution(* com.example.services.*.*(..))")
    public void log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info(joinPoint.getSignature().toString() + " method execution start");
        Instant start = Instant.now();
        joinPoint.proceed();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Time took to execute the method : "+timeElapsed);
        logger.info(joinPoint.getSignature().toString() + " method execution end");
    }
  }
  ```
  - @Around这个增强将围绕目标方法执行，匹配com.example.services包（及其子包）中任何方法执行
  - 切入点（Pointcut），它匹配com.example.services包下的所有类和所有方法。
  - ProceedingJoinPoint是JoinPoint的子接口，它额外提供了proceed()方法，专用于around类型的增强，可以在方法执行前后执行代码。
  - joinPoint.proceed()就是执行方法

  Example2: VehicleStartCheck Aspect，目的是检查汽车有无启动
  ```
  @Aspect
  @Component
  @Order(1)
  public class VehicleStartCheckAspect {
    @Before("execution(* com.example.services.*.*(..)) && args(vehicleStarted,..)")
    public void checkVehicleStarted(JoinPoint joinPoint, boolean vehicleStarted) throws Throwable {
        if(!vehicleStarted){
            throw new RuntimeException("Vehicle not started");
        }
    }
  }
  ```
  - @Order(1)：数字越小，aspect优先级越高。
  - @Before：在连接点方法执行之前运行。
  - Join Points 切入点表达式：
    - execution(* com.example.services.*.*(..))：匹配com.example.services包及其子包中类的任何方法执行。*通配符匹配任何返回类型，..通配符匹配任意数量的参数。
    - args(vehicleStarted,..)：进一步将匹配范围缩小到第一个参数是名为vehicleStarted的布尔参数的方法执行，此外还允许有任意数量的其他参数。
  - JoinPoint：（这里并没有用，只是做了个汽车启动判定）
    - 方法签名：JoinPoint.getSignature() 方法返回当前连接点执行的方法的签名信息，比如方法名称和返回类型。
    - 方法参数：通过 JoinPoint.getArgs() 方法可以获取到方法调用时传递的参数。
    - 目标对象：JoinPoint.getTarget() 方法允许你访问目标对象，即包含正在执行的方法的对象。
    - 代理对象：如果你需要获取代理对象本身，可以使用 JoinPoint.getThis() 方法。
- sss
