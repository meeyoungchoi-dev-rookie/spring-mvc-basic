package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@Controller
//@ResponseBody
@RestController
public class ResponseBodyController {


    @GetMapping("/response-body-string-v1")
    public void responseBodyv1(HttpServletResponse response) throws IOException {
        response.getWriter().write("OK");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyv2()  {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    //@ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyv3()  {
        return "OK";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonv1() {
        HelloData helloData = new HelloData();
        helloData.setUserName("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    //@ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonv2() {
        HelloData helloData = new HelloData();
        helloData.setUserName("userA");
        helloData.setAge(20);
        return helloData;
    }
}
