package debug;

import com.google.inject.Inject;
import main.AGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * There is a controllers duplicate invoking  problem.
 * Method debug detected problem.
 * If the problem is solved clazz will be destroyed.
 */

public final class DebugThread {
    private final Logger logger = LoggerFactory.getLogger(DebugThread.class);

    private int sync = 0;

    @Inject
    private AGame aGame;

    public final void debug(){
            if (aGame == null){
                logger.info("null");
            } else {
                logger.info("NotNull");
            }
        logger.info(sync++ + "");
    }
}
