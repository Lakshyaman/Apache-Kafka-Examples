package adapter;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.util.concurrent.Service;
import io.dropwizard.lifecycle.Managed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Adapts a {@link Service} to a {@link Managed} so it can be run as a background service by
 * DropWizard.
 *
 * <p>Created by wilmol on 2019-07-29.
 */
public class ManagedService implements Managed {

  private final Logger log = LogManager.getLogger();

  private final Service service;

  private final String name;

  public ManagedService(Service service, String name) {
    this.service = checkNotNull(service);
    this.name = name;
  }

  @Override
  public void start() {
    log.info("Starting: [{}]", name);
    service.startAsync().awaitRunning();
    log.info("Started: [{}]", name);
  }

  @Override
  public void stop() {
    log.info("Stopping: [{}]", name);
    service.stopAsync().awaitTerminated();
    log.info("Stopped: [{}]", name);
  }
}
