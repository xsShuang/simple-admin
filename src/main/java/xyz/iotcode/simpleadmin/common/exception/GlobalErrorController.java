package xyz.iotcode.simpleadmin.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xyz.iotcode.simpleadmin.common.pojo.vo.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xieshuang
 */
@RestController
@ApiIgnore
@RequestMapping("${server.error.path:/error}")
public class GlobalErrorController implements ErrorController {

    /**
     * 错误信息的构建工具.
     */
    @Autowired
    private ErrorInfoBuilder errorInfoBuilder;

    /**
     * 错误信息页的路径
     */
    private final static String DEFAULT_ERROR_VIEW = "error";

    /**
     * 获取错误控制器的映射路径.
     */
    @Override
    public String getErrorPath() {
        return errorInfoBuilder.getErrorProperties().getPath();
    }

    /**
     * 情况1：若预期返回类型为text/html,s则返回错误信息页(View).
     */
    /*@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ModelAndView errorHtml(HttpServletRequest request) {
        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request));
    }*/

    /**
     * 情况2：其它预期类型 则返回详细的错误信息(JSON).
     */
    @RequestMapping
    public Result<ErrorInfo> error(HttpServletRequest request) {
        ErrorInfo errorInfo = errorInfoBuilder.getErrorInfo(request);
        return Result.fail(errorInfo.getStatusCode(), errorInfo.getMessage(), errorInfo);
    }

}