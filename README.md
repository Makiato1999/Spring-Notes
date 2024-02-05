# Master Spring 6, Spring Boot 3, REST, JPA, Hibernate
_provided by Eazy Bytes, Madan Reddy on Udemy platform_ <br><br>

#### Table of Contents
1. [@Component Annotation](#anchor_1)<br/>
2. [@Autowired Annotation](#anchor_2)<br/>

## @Component Annotation<a name="anchor_1"></a>

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
## @Component Annotation<a name="anchor_3"></a>
  
