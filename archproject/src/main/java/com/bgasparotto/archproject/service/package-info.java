/**
 * <p>
 * Main package of {@code service} layer. This package contains the service
 * abstractions, and the implementations can be found on its specific
 * sub-packages.
 * </p>
 * <p>
 * The sub-packages are organised and combined according to the business goal
 * they accomplish, but may be reorganised into finer granularity as soon as
 * they grow.
 * </p>
 * <p>
 * The service layer is where all the business rules are applied. Sometimes the
 * methods of classes and interfaces of {@code service} layer may look exactly
 * the same as the resources of {@code persistence} layer, but the service layer
 * acts like a facade to those resources, because if some business rules need to
 * be applied on a distinct operation, it's applied on service layer, this way,
 * the corresponding resource of persistence layer is left untouched and the
 * changes don't affect other services using that single resource.
 * </p>
 * <p>
 * The service layer should access resources from the {@code model} and
 * {@code persistence} layers, but shouldn't use resources from {@code web}
 * layer, even if it's possible to import or reference these resources.
 * </p>
 */
package com.bgasparotto.archproject.service;