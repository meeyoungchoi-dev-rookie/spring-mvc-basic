<<<<<<< HEAD
# spring-mvc-basic-function
스프링 mvc1편 기본 기능

=======
# spring mvc 기본 기능
## HTTP 요청 파라미터 관련 내용 실습
### 배운점
+ [요청 매핑](https://unique-wandflower-4cc.notion.site/a049acd58d674185b3e1bb291f47f2b9)
+ [회원관리 요청매핑 API](https://unique-wandflower-4cc.notion.site/API-5f088d407b34405ba05850c402738774)
+ [HTTP 요청 - 헤더 조회](https://unique-wandflower-4cc.notion.site/HTTP-5ccda026b62f4e208d70617767d1cb47)
+ [@RequestParam](https://unique-wandflower-4cc.notion.site/HTTP-RequestParam-115b0e3598614d328f527e2270932712)
+ [@ModelAttribute](https://unique-wandflower-4cc.notion.site/HTTP-ModelAttribute-97bf6bb1b60942fe9ef76cda95487b1d)



## HTTP 요청 파라미터 관련 내용 실습
### 배운점
+ [요청 매핑](https://unique-wandflower-4cc.notion.site/a049acd58d674185b3e1bb291f47f2b9)
+ [회원관리 요청매핑 API](https://unique-wandflower-4cc.notion.site/API-5f088d407b34405ba05850c402738774)
+ [HTTP 요청 - 헤더 조회](https://unique-wandflower-4cc.notion.site/HTTP-5ccda026b62f4e208d70617767d1cb47)
+ [@RequestParam](https://unique-wandflower-4cc.notion.site/HTTP-RequestParam-115b0e3598614d328f527e2270932712)
+ [@ModelAttribute](https://unique-wandflower-4cc.notion.site/HTTP-ModelAttribute-97bf6bb1b60942fe9ef76cda95487b1d)

## HTTP 메시지 컨버터

- 직접 request , response 객체에 write 하는 것이 불편하다
- message-body에서 직접 데이터를 읽거나 쓰는 경우 사용한다
- @RequestBody가 붙으면 message-body에 담겨있는 데이터를 읽는다
- @ResponseBody가 붙으면 message-body에 응답 데이터를 쓴다

![spring_http_메시지 컨버터](https://user-images.githubusercontent.com/42866800/160276766-64074b47-5323-4992-a8a2-f1b569953742.png)


- 컨트롤러가 호출되기 전에 HttpMessageConverter가 적용된다
- HttpMessageConverter는 인터페이스로 되어있다
- HTTP 요청과 응답에 둘다 사용된다
- `canRead() , canWrite()` - 메시지 컨버터가 해당 클래스 , 미디어 타입을 지원하는지 체크한다
- `read(), write()` - 메시지 컨버터를 통해 메시지를 읽고 쓰는 기능

### HTTP 요청 데이터 읽기

- HTTP 요청
- @RequestBody , HttpEntity 파라미터를 사용하여 반환한다
- 메시지 컨버터가 타입 체크 진행 - canRead()
    - 대상 클래스 타입을 지원하는가
        - 예) @RequestBody - (byte[], String , HelloData)
    - HTTP 요청의 Content-Type 미디어 타입을 지원하는 가
        - 예) text/plain, application/json, */*
    - canRead 조건을 만족하면 read 메서드를 호출하여 객체를 생성하고 반환해 준다

### HTTP 응답 데이터 생성

- 컨트롤러에서 @ResponseBody, HttpEntity로 값이 반환된다
- 메시지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 canWriter 메서드를 호출한다
    - 대상 클래스 타입을 지원하는 가
        - 예) return 의 대상 클래스 (byte[], String , HelloData)
    - HTTP 요청의 미디어 타입을 지원하는 가
        - 예) text/plain, application/json , * / *
    - canWrite 조건을 만족하면 writer 메서드를 호출하여 응답 메시지 바디에 데이터를 생성한다


## 요청 매핑 핸들어 어댑터 구조 (RequestMappingHandlerAdapter)

![spring mvc 구조](https://user-images.githubusercontent.com/42866800/160276803-dfe38e32-fb7b-4874-b568-c36066bde9b3.png)

![RequestMappingHandlerMapping](https://user-images.githubusercontent.com/42866800/160276821-53a4f23d-1b9c-4085-954c-b2a713575d3a.png)


`ArgumentResolver`

- 컨트롤러로 들어온 파라미터를 가공하거나 수정 , 바인딩 기능을 사용하기 위한 객체
- 세션 , 쿠키 , 헤더 등에서 제공받는 데이터들을 파라미터로 받는 경우 ArgumentResolver를 사용하여 바인딩 할 수 있다
- @PathVariable을 사용하는 데이터들은 바로 컨트롤러에서 파라미터로 받을 수 있다
- 핸들러를 호출하여 파라미터 값을 확인하여 정보를 전달받는다
- ArgumentResolver를 호출하여 파라미터의 객체 생성을 요구한다
    - supportsParameter 메서드를 호출하여 해당 파라미터를 지원하는 지 체크한다
    - 지원하는 경우 resolveArgument를 호출하여 실제 객체를 생성한다
    - 다시 Handler를 호출한다
    - 파라미터로 객체를 전달한다
- `ReturnValueResolver`
    - 응답 데이터를 처리한다
    - @RestController를 사용하는 경우 반환 값을 그대로 데이터로 인식하여 반환해 준다
    - supportsReturnType을 호출하여 지원하는 타입인지 확인한다
    - 지원하는 경우 handlerReturnValue를 호출하여 해당 값을 반환해 준다


`MessageConverter`

- 요청 및 응답시 객체의 타입이 무엇이고 어떤 미디어타입을 제공하는지 확인하는 작업이 필요하다
- 파라미터와 반환 값을 체크하여 지원하는 값인지 체크하여 객체를 생성한 후 요청을 처리하고 응답을 처리한다