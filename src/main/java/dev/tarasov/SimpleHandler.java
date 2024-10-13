package dev.tarasov;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SimpleHandler implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String input, Context context) {
        var awsLogger = context.getLogger();
        awsLogger.log("Function '" + context.getFunctionName() + "' called");
        return input.toUpperCase();
    }
}
