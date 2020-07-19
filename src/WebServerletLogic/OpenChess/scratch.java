package WebServerletLogic.OpenChess;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.ScriptStatus;

import java.io.File;
import java.util.concurrent.ExecutionException;

class Scratch {
    public static void main(String[] args) throws NodeException, ExecutionException, InterruptedException {


// The NodeEnvironment controls the environment for many scripts
        NodeEnvironment env = new NodeEnvironment();
        String[] arr=new String[]{"e4","c5","Nf3"};
// Pass in the script file name, a File pointing to the actual script, and an Object[] containg "argv"
        NodeScript script = env.createScript("myfirst.js",
                new File("src\\WebServletLogic\\myfirst.js"),arr );
 s
// Wait for the script to complete
        ScriptStatus status = script.execute().get();

// Check the exit code
        System.exit(status.getExitCode());
    }
}