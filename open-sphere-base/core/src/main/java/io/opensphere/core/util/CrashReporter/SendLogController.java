package io.opensphere.core.util.CrashReporter;

import org.apache.log4j.Logger;
import io.opensphere.core.Toolbox;

/**
 * Sends crash log files to the server. -eventually integrate to send to JIRA
 */
public class SendLogController

{
    /**
     * Used to log messages.
     */
    private static final Logger LOGGER = Logger.getLogger(SendLogController.class);

    /**
     * Constructs a new controller.
     *
     * @param toolbox The system toolbox.
     */
    public SendLogController(Toolbox toolbox)
    {

    }

    /**
     * 
     */
    public void ConnectToServer(boolean check)
    {

        System.out.println(check);
    }

    /**
     * 
     */
    public void AuthenticateServer()
    {
    }

    /**
     * 
     */
    public void SendFile()
    {
    }

}
