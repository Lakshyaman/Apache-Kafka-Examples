package service;

import static com.google.common.base.Preconditions.checkNotNull;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

/**
 * A service which may run within a DropWizard Application.
 *
 * <p>Created by wilmol on 2019-07-29.
 *
 * @see io.dropwizard.Application
 * @see Managed
 * @see HealthCheck
 */
public interface DropWizardService {

  /**
   * Register a {@link DropWizardService} within a DropWizard {@link Environment}.
   *
   * @param managedService service to register
   * @param environment DropWizard app environment
   */
  static void register(DropWizardService managedService, Environment environment) {
    checkNotNull(managedService);
    checkNotNull(environment);
    environment.lifecycle().manage(managedService.managed());
    environment.healthChecks().register(managedService.name(), managedService.healthCheck());
  }

  /**
   * Adapt this {@code DropWizardService} to a {@link Managed} so it can be run as a background
   * service by DropWizard.
   *
   * @return this instance as a {@link Managed}
   * @see adapter.ManagedService
   */
  Managed managed();

  /**
   * Adapt this {@code DropWizardService} to a {@link HealthCheck} so it can be registered by
   * DropWizard.
   *
   * @return this instance as a {@link HealthCheck}
   * @see adapter.ServiceHealthCheck
   */
  HealthCheck healthCheck();

  /**
   * The name of this {@code DropWizardService}.
   *
   * <p>Recommend using different names for different instances of the same service (e.g. if running
   * multiple clients in parallel).
   *
   * @return the name of this instance
   */
  String name();
}
