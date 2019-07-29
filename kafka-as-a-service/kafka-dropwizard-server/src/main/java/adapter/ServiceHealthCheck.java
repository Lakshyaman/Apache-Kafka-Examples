package adapter;

import static com.google.common.base.Preconditions.checkNotNull;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.util.concurrent.Service;

/**
 * Adapts a {@link Service} to a {@link HealthCheck} so it can be registered by DropWizard.
 *
 * <p>Created by wilmol on 2019-07-29.
 */
public class ServiceHealthCheck extends HealthCheck {

  private final Service service;

  public ServiceHealthCheck(Service service) {
    this.service = checkNotNull(service);
  }

  @Override
  protected HealthCheck.Result check() {
    String state = service.state().toString();
    return service.isRunning()
        ? HealthCheck.Result.healthy(state)
        : HealthCheck.Result.unhealthy(state);
  }
}
