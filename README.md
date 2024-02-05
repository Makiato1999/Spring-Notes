# Master Spring 6, Spring Boot 3, REST, JPA, Hibernate
_provided by Eazy Bytes, Madan Reddy on Udemy platform_ <br><br>

#### Table of Contents
1. [stereotype](#anchor_1)<br/>
2. [@Autowired Annotation](#anchor_2)<br/>
3. [Bean Scope](#anchor_3)<br/>

## stereotype<a name="anchor_1"></a>
Stereotype（原型）注解是一组用于定义Spring管理的bean的注解。这些注解提供了一种方便的方式来声明bean，并且通常还携带额外的元数据，让Spring容器能够识别和处理它们。
- @Component: 这是最通用的stereotype注解，它表明一个类被Spring框架视为组件。当使用类路径扫描时，Spring会自动找到这些注解，并注册它们为bean。
- @Service: 这个注解是@Component的特化，它用于标注服务层的组件。在语义层面上，它表示这个类提供了一种业务服务。
- @Repository: 这个注解是@Component的特化，用于标注数据访问对象（DAO），即那些提供了数据访问操作的类。它也可以帮助Spring提供数据访问时的特定功能，如异常翻译。

@Controller: 用于标注控制器组件，特别是在Spring MVC模式中，这个注解定义了一个类作为请求的处理器。
## @Autowired Annotation <a name="anchor_2"></a>
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

## Bean Scope<a name="anchor_3"></a>
- Singleton (default)
- Race condition
- 

